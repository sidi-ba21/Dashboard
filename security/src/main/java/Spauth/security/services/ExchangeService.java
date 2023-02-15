package Spauth.security.services;

import Spauth.security.model.Widgets;
import Spauth.security.repository.WidgetsRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeService {
    @Autowired
    private WidgetsRepository widgetsRepository;

    public String getCurrencyChange(String currencies, Long userId) {
        String url = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/" + currencies + ".json";

        RestTemplate restTemplate = new RestTemplate();
        String currency;

        try {
            currency = restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            return "{\"message\":\"not found\"}";
        }
        Widgets widgets = new Widgets(url, userId, currencies);
        List<Widgets> listWidget = widgetsRepository.findAllByValue(currencies);
        if (listWidget.isEmpty()) {
            widgetsRepository.save(widgets);
        }
        else {
            for (Widgets widget : listWidget) {
                if (widget.getUserId() == userId && widget.getValue().equals(currencies)) {
                    widgetsRepository.delete(widget);
                }
            }
            widgetsRepository.save(widgets);
        }
        return currency;
    }
}
