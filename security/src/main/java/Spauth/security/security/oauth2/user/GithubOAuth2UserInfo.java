package Spauth.security.security.oauth2.user;

import java.util.Map;

public class GithubOAuth2UserInfo extends OAuth2UserInfo {

    static int i = 0;
    public GithubOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return ((Integer) attributes.get("id")).toString();
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        i++;
        return "null" + i + "@gmail.com";
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("avatar_url");
    }
}
