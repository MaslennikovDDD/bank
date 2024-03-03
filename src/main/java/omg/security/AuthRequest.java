package omg.security;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AuthRequest {
    @NotNull
    private String login;
    @NotNull
    private String password;

    // getters and setters are not shown...
}