package Spauth.security.services;

import Spauth.security.model.Widgets;
import Spauth.security.repository.WidgetsRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TimezoneService {

    @Autowired
    private WidgetsRepository widgetsRepository;

    public String Timezone(String city, Long userId) {
        String url = "https://timeapi.io/api/Time/current/zone?timeZone=" + city;

        RestTemplate restTemplate = new RestTemplate();
        String timezone;

        try {
            timezone = restTemplate.getForObject(url, String.class);
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
        return timezone;
    }
}