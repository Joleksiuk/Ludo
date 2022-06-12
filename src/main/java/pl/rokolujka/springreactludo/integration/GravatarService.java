package pl.rokolujka.springreactludo.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GravatarService {

    private final HttpClient httpClient;

    public Optional<String> findGravatarUrl(String email) {
        if (email == null) return Optional.empty();

        // s -size d-not found
        String url = "http://www.gravatar.com/avatar/" + MD5.toHash(email) + "?s=300&d=404";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == HttpStatus.OK.value()
                    ? Optional.of(url) : Optional.empty();
        } catch (InterruptedException | IOException e) {
            return Optional.empty();
        }
    }
}
