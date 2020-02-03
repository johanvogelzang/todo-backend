package nl.birdsongit.config;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

public interface Environment {

    int DEFAULT_PORT = 7000;
    String DEFAULT_URL = "http://localhost";
    String HEROKU_URL = "https://javalin-todobackend.herokuapp.com";

    static String hostUrl() {
        return ofNullable(System.getenv("DYNO"))
                .map(url -> HEROKU_URL)
                .orElse(format("%s:%d", DEFAULT_URL, DEFAULT_PORT));
    }

    static Integer port() {
        return ofNullable(System.getenv("PORT"))
                .map(Integer::parseInt)
                .orElse(DEFAULT_PORT);
    }
}
