package Spauth.security.services;

import Spauth.security.model.Widgets;
import Spauth.security.repository.WidgetsRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class VideoService {
    private final String key = "AIzaSyCzDFtRZPI7iuWUguoomI5cMNN0GNMUoJQ";

    @Autowired
    private WidgetsRepository widgetsRepository;

    public String videoByQuery(String query, Long userId) {
        String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&q="+query+"&maxResults=15&key=" + key;

        RestTemplate restTemplate = new RestTemplate();
        String queryVideo = restTemplate.getForObject(url, String.class);

        Widgets widgets = new Widgets(url, userId, query);
        List<Widgets> listWidget = widgetsRepository.findAllByValue(query);
        if (listWidget.isEmpty()) {
            widgetsRepository.save(widgets);
        }
        else {
            for (Widgets widget : listWidget) {
                if (widget.getUserId() == userId && widget.getValue().equals(query)) {
                    widgetsRepository.delete(widget);
                }
            }
            widgetsRepository.save(widgets);
        }

        return queryVideo;
    }
    public String TopTen(Long userId){
        String url = "https://youtube.googleapis.com/youtube/v3/videos?part=snippet&chart=mostPopular&maxResults=10&regionCode=fr&key="+key;

        RestTemplate restTemplate = new RestTemplate();
        String TopTen = restTemplate.getForObject(url, String.class);

        Widgets widgets = new Widgets(url, userId, null);
        Iterable<Widgets> listWidget = widgetsRepository.findAll();
        for (Widgets widget : listWidget) {
            if (widget.getUserId() == userId && widget.getUrl().equals(url)) {
                widgetsRepository.delete(widget);
            }
        }
        widgetsRepository.save(widgets);

        return TopTen;
    }
    public String FetchSubscribersOf(String name, Long userId){
            String url = "https://www.googleapis.com/youtube/v3/channels?part=statistics&forUsername=" + name + "&fields=items/statistics/subscriberCount&key=" + key;
    
            RestTemplate restTemplate = new RestTemplate();
            String subscribers = restTemplate.getForObject(url, String.class);

            Widgets widgets = new Widgets(url, userId, name);
            List<Widgets> listWidget = widgetsRepository.findAllByValue(name);
        if (listWidget.isEmpty()) {
            widgetsRepository.save(widgets);
        }
        else {
            for (Widgets widget : listWidget) {
                if (widget.getUserId() == userId && widget.getValue().equals(name)) {
                    widgetsRepository.delete(widget);
                }
            }
            widgetsRepository.save(widgets);
        }
    
            return subscribers;
    }
}