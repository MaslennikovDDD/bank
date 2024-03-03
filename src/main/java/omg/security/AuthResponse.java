package omg.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String login;
    private String accessToken;

    public AuthResponse() { }

    public AuthResponse(String login, String accessToken) {
        this.login = login;
        this.accessToken = accessToken;
    }

    // getters and setters are not shown...
}