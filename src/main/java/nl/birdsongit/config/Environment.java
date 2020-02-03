package nl.birdsongit.config;

import static java.util.Optional.ofNullable;

public interface Environment {

    int DEFAULT_PORT = 7000;
    String DEFAULT_URL = "http://localhost";
    String HEROKU_URL = "https://javalin-todobackend.herokuapp.com";

    static String hostUrl() {
        return ofNullable(System.getenv("DYNO"))
                .map(url -> HEROKU_URL)
                .orElse(DEFAULT_URL);
    }

    static Integer port() {
        return ofNullable(System.getenv("PORT"))
                .map(Integer::parseInt)
                .orElse(DEFAULT_PORT);
    }
}
