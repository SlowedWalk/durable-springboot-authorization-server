package tech.hidetora.authserver.init;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Map;

/**
 * @author hidetora
 * @version 1.0.0
 * @since 2022/04/18
 */
@Configuration
@RequiredArgsConstructor
public class UsersRunner {
    private final JdbcUserDetailsManager userDetailsManager;


    @Bean
    ApplicationRunner runUsers() {
        return args -> {
            var builder = User.builder().roles("USER");
            var users = Map.of(
                    "hidetora", "{bcrypt}$2a$10$I4b0gdSh0H2WbgDFPwdMp.PhoM06XrYpUdsYBJVsl4oUyd0cpLFoa",
                    "jean", "{bcrypt}$2a$10$BkNpTV236.4Xw7U9fi/tLeYUOcMJQzvvsvLjdJBfgXvUUkS0sGjBO");
            users.forEach((username, password) -> {
                if (!userDetailsManager.userExists(username)) {
                    var user = builder
                            .username(username)
                            .password(password)
                            .build();
                    userDetailsManager.createUser(user);
                }
            });
        };
    }
}
