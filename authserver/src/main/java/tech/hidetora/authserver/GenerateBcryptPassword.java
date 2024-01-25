package tech.hidetora.authserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Scanner;

/**
 * @author hidetora
 * @version 1.0.0
 * @since 2022/04/18
 */

@Slf4j
public class GenerateBcryptPassword {
    public static void main(String[] args) {
        // read password from user's input
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter username: ");

        String password = scanner.nextLine();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(14);
        String hashedPassword = passwordEncoder.encode(password);
        log.info(hashedPassword);
    }
}
