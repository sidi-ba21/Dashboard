package Spauth.security.services;

import Spauth.security.model.Widgets;
import Spauth.security.repository.WidgetsRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// spotify demande une gestion des tokens, à voir si ca peut passer? 
@Service
public class DeezerService {

    @Autowired
    private WidgetsRepository widgetsRepository;

    public String Song(String artist, Long userId) {
        String link = "https://api.deezer.com/search?q=" + artist;

        RestTemplate restTemplate = new RestTemplate();
        String songs = restTemplate.getForObject(link, String.class);

        Widgets widgets = new Widgets(link, userId, artist);
        List<Widgets> listWidget = widgetsRepository.findAllByValue(artist);
        if (listWidget.isEmpty()) {
            widgetsRepository.save(widgets);
        }
        else {
            for (Widgets widget : listWidget) {
                if (widget.getUserId() == userId && widget.getValue().equals(artist)) {
                    widgetsRepository.delete(widget);
                }
            }
            widgetsRepository.save(widgets);
        }

        return songs;
    }
}