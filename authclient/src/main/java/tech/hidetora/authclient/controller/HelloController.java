package tech.hidetora.authclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

/**
 * @author hidetora
 * @version 1.0.0
 * @since 2022/04/18
 */

@RestController
public class HelloController {

    @GetMapping("/")
    public Map<String, String> hello(Principal principal) {
        return Map.of("message", "Hello, " + principal.getName() + "!");
    }
}
