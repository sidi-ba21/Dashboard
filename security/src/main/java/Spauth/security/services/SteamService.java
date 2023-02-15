package Spauth.security.services;

import Spauth.security.model.Widgets;
import Spauth.security.repository.WidgetsRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SteamService {
    @Autowired
    private WidgetsRepository widgetsRepository;

    public String getGameInfo(String game, Long userId) {
        String url = "https://api.steampowered.com/ISteamUserStats/GetNumberOfCurrentPlayers/v1/?format=json&appid="
                + game;

        System.out.println(url);
        RestTemplate restTemplate = new RestTemplate();
        String games = restTemplate.getForObject(url, String.class);
        Widgets widgets = new Widgets(url, userId, game);
        List<Widgets> listWidget = widgetsRepository.findAllByValue(game);
        if (listWidget.isEmpty()) {
            widgetsRepository.save(widgets);
        } else {
            for (Widgets widget : listWidget) {
                if (widget.getUserId() == userId && widget.getValue().equals(game)) {
                    widgetsRepository.delete(widget);
                }
            }
            widgetsRepository.save(widgets);
        }
        return games;
    }

    public String getGameList() {
        String url = "https://api.steampowered.com/ISteamApps/GetAppList/v0002/";

        System.out.println(url);

        RestTemplate restTemplate = new RestTemplate();
        String games = restTemplate.getForObject(url, String.class);
        return games;
    }
}
