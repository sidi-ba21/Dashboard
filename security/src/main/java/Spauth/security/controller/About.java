package Spauth.security.controller;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JacksonException;

import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.Instant;

@RestController
public class About {
    @GetMapping("/about.json")
    public Map<String, Object> getAbout(HttpServletRequest request) throws JacksonException{
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, String> client = new HashMap<String, String>();
        Map<String, Object> server = new HashMap<String, Object>();
        Map<String, Object> services = new HashMap<String, Object>();
        Map<String, String> fwidget = new HashMap<String, String>();
        Map<String, String> swidget = new HashMap<String, String>();
        Map<String, String> twidget = new HashMap<String, String>();
        client.put("host", request.getRemoteAddr());
        
        services.put("name", "TimeZone");
        fwidget.put("name", "International Time");
        fwidget.put("description", "Displays time from different countries");
        fwidget.put("params", "none");
        swidget.put("name", "International Weather");
        swidget.put("description", "Displays weather variations from different countries");
        swidget.put("params", "none");
        twidget.put("name", "Steam");
        twidget.put("description", "Displays steam games nb players");
        twidget.put("params", "none");
        twidget.put("name", "Deezer");
        twidget.put("description", "Displays deezer music nb listeners");
        twidget.put("params", "none");
        twidget.put("name", "Youtube");
        twidget.put("description", "Displays youtube videos nb views");
        twidget.put("params", "none");
        twidget.put("name", "News");
        twidget.put("description", "Displays news articles+link");
        twidget.put("params", "none");
        twidget.put("name", "Exchange");
        twidget.put("description", "Displays exchange rates");
        twidget.put("params", "none");
        twidget.put("name", "Ringstone");
        twidget.put("description", "play song + dl");
        twidget.put("params", "none");
        

        List<Map<String, String>> widgets = new ArrayList<Map<String, String>>();
        widgets.add(fwidget);
        widgets.add(swidget);
        widgets.add(twidget);
        services.put("widgets", widgets);
        
        server.put("services", services);
        server.put("current_time", Instant.now().toEpochMilli());
        
        map.put("client", client);
        map.put("server", server);

        return map;
    }
}