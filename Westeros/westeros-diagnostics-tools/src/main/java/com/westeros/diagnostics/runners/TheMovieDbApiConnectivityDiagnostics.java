package com.westeros.diagnostics.runners;

import com.westeros.diagnostics.services.contract.Diagnostics;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TheMovieDbApiConnectivityDiagnostics implements IDiagnose {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${themoviedb.api.url}")
    private String apiUrl;

    @Value("${themoviedb.api.key}")
    private String apiKey;

    @Override
    public String getName() {
        return " The Movie DB API Connectivity Diagnostics";
    }

    @Override
    public String getDescription() {
        return " Checks connectivity to The Movie DB API.";
    }

    @Override
    public Diagnostics run() {
        Diagnostics diagnostics = new Diagnostics();
        diagnostics.setName(getName());
        diagnostics.setDescription(getDescription());

        try {
            String url = apiUrl + "?api_key=" + apiKey;

            ResponseEntity<String> response =
                    restTemplate.getForEntity(url, String.class);

            boolean success = response.getStatusCode().is2xxSuccessful();
            diagnostics.setSuccess(success);

            if (!success) {
                diagnostics.setErrorMessage(
                        "TheMovieDb API returned status: " + response.getStatusCode()
                );
            }

        } catch (Exception e) {
            diagnostics.setSuccess(false);
            diagnostics.setErrorMessage(
                    "TheMovieDb API connection failed: " + e.getMessage()
            );
        }

        return diagnostics;
    }
}
