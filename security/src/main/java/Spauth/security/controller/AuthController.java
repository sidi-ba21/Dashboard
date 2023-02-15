package Spauth.security.controller;

import Spauth.security.exception.BadRequestException;
import Spauth.security.model.AuthProvider;
import Spauth.security.model.User;
import Spauth.security.model.Widgets;
import Spauth.security.payload.ApiResponse;
import Spauth.security.payload.AuthResponse;
import Spauth.security.payload.LoginRequest;
import Spauth.security.payload.SignUpRequest;
import Spauth.security.repository.UserRepository;
import Spauth.security.security.TokenProvider;
import Spauth.security.services.DeezerService;
import Spauth.security.services.ExchangeService;
import Spauth.security.services.NewsService;
import Spauth.security.services.SteamService;
import Spauth.security.services.TimezoneService;
import Spauth.security.services.VideoService;
import Spauth.security.services.WeatherService;
import Spauth.security.services.WidgetsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    private Long userId = 0L;
    private boolean isLogged = true;

    @Autowired
    WeatherService weather;
    @Autowired
    DeezerService deezer;
    @Autowired
    NewsService news;
    @Autowired
    VideoService yt;
    @Autowired
    WidgetsService widgetsService;
    @Autowired
    SteamService steam;
    @Autowired
    ExchangeService exchange;
    @Autowired
    TimezoneService timezone;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        // Creating user's account
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(AuthProvider.local);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully@"));
    }

    @GetMapping("/widgets")
    @ResponseBody
    public Iterable<Widgets> getUserWidgets() {
        this.userId = UserController.getUserId();
        Iterable<Widgets> widgets = widgetsService.getWidgets(userId);
        System.out.println(widgets);
        return widgets;
    }

    @RequestMapping(value = "/services/steam/players")
    public String players(@RequestParam("value") String val) {
        this.userId = UserController.getUserId();
        if (isLogged) {
            return steam.getGameInfo(val, userId);
        }
        return null;
    }
    @RequestMapping(value = "/services/steam/games")
    public String games() {
        if (isLogged) {
            return steam.getGameList();
        }
        return null;
    }

    @RequestMapping(value = "/services/music/artist")
    public String artiste(@RequestParam("value") String val) {
        this.userId = UserController.getUserId();
        if (isLogged) {
            return deezer.Song(val, userId);
        }
        return null;
    }

    @RequestMapping(value = "/services/weather/temp", method = RequestMethod.GET)
    public String temp(@RequestParam("value") String val) {
        this.userId = UserController.getUserId();
        String sys = weather.Temperature(val, userId);
        return sys;

    }

    @RequestMapping(value = "/services/news/topnews")
    public String topnews(@RequestParam("value") String val) {
        this.userId = UserController.getUserId();
        if (isLogged) {
            return news.TopNews(val, userId);
        }
        return null;
    }

    @RequestMapping(value = "/services/news/keyword")
    public String keyword(@RequestParam("value") String val) {
        this.userId = UserController.getUserId();
        if (isLogged) {
            return news.NewsPaper(val, userId);
        }
        return null;
    }

    @RequestMapping(value = "/services/yt/subscribers")
    public String subscribers(@RequestParam("value") String val) {
        this.userId = UserController.getUserId();
        if (isLogged) {
            return yt.FetchSubscribersOf(val, userId);
        }
        return null;
    }

    @RequestMapping(value = "/services/yt/search")
    public String search(@RequestParam("value") String val) {
        this.userId = UserController.getUserId();
        if (isLogged) {
            return yt.videoByQuery(val, userId);
        }
        return null;
    }

    @RequestMapping(value = "/services/exchange")
    public String exchange(@RequestParam("value") String val) {
        this.userId = UserController.getUserId();
        if (isLogged) {
            return exchange.getCurrencyChange(val, userId);
        }
        return null;
    }

    @RequestMapping(value = "/services/timeZone")
    public String timeZone(@RequestParam("value") String val) {
        this.userId = UserController.getUserId();
        if (isLogged) {
            return timezone.Timezone(val, userId);
        }
        return null;
    }

    @PostMapping("/delWidgets")
    public void deleteWidget(@RequestBody String value) {
        this.userId = UserController.getUserId();
        String newValue = value.replaceAll("\"", "");
        Iterable<Widgets> listWidgets = widgetsService.getWidgets(userId);
        for (Widgets elmt : listWidgets) {
            if (newValue.toLowerCase().contains(elmt.getValue().toLowerCase())) {
                widgetsService.delete(elmt);
            }
        }
    }

}
