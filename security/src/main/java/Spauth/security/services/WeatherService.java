package Spauth.security.services;

import Spauth.security.model.Widgets;
import Spauth.security.repository.WidgetsRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    private final String key = "8e0e12c87a2dce2da789a60f5029b5e9";

    @Autowired
    private WidgetsRepository widgetsRepository;

    public String Temperature(String city, Long userId) {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + key;

        System.out.println(url);
        RestTemplate restTemplate = new RestTemplate();
        // check if the city is valid
        String weather;
        try {
            weather = restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            return "{\"message\":\"not found\"}";

        }

        Widgets widgets = new Widgets(url, userId, city);
        List<Widgets> listWidget = widgetsRepository.findAllByValue(city);
        if (listWidget.isEmpty()) {
            widgetsRepository.save(widgets);
        }
        else {
            for (Widgets widget : listWidget) {
                if (widget.getUserId() == userId && widget.getValue().equals(city)) {
                    widgetsRepository.delete(widget);
                }
            }
            widgetsRepository.save(widgets);
        }
        return weather;
    }
}