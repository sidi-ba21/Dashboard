package Spauth.security.payload;

public class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    public AuthResponse(String accessToken){this.accessToken = accessToken;}

    public String getAccessToken(){return accessToken;}

    public void setAccessToken(String accessToken){this.accessToken = accessToken;}

    public void setTokenType(String tokenType){this.tokenType = tokenType;}

    public String getTokenType(){return tokenType;}
}