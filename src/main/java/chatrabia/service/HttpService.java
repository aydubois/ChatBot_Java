package chatrabia.service;

import chatrabia.exception.ExternalAPIException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;

@Service("httpService")
public class HttpService {

    private static Logger log = LogManager.getRootLogger();

    private WebClient webClient;

    public HttpService(WebClient webClient) {
        this.webClient = webClient;
    }

    public <T> T sendGetRequest(String url, Class<T> c, ArrayList<String> headers) throws ExternalAPIException {

        ClientResponse clientResponse;
        Mono<ClientResponse> fluxResponse;

        WebClient.RequestHeadersSpec<?> requestSpec = webClient
                .get()
                .uri(URI.create(url));

        if(headers != null) {
            headers.forEach( element -> {String[] strings = element.split(":");
                String key = strings[0];
                String value = strings[1];
                requestSpec.header(key, value);
            });
        }

        try {
            fluxResponse = requestSpec.exchange();
        } catch (Exception ex) {
            log.error("erreur lors de la requete url {}", url, ex);
            if (ex.getCause() instanceof UnknownHostException) {
                throw new ExternalAPIException(String.format("Le nom d'hôte %s est inconnu.", url));
            } else if (ex.getMessage().contains("Connection timed out")) {
                throw new ExternalAPIException(String.format("Impossible de se connecter à l'hôte %s", url));
            } else {
                throw new ExternalAPIException(String.format("Erreur lors de l'exécution de la requête %s", url));
            }
        }

        try {
            clientResponse = fluxResponse.block();
            HttpStatus statusCode = clientResponse.statusCode();
            if (statusCode.value() == 200 || statusCode.value() == 204 || statusCode.value() == 206) {
                return clientResponse.bodyToMono(c).block();
            } else {
                throw new ExternalAPIException(String.format("Erreur lors de l'exécution de la requête GET %s code HTTP %s", url, statusCode.value()));
            }
        } catch (Exception ex) {
            throw new ExternalAPIException(String.format("Erreur lors de l'exécution de la requête %s", url));
        }
    }


    public <T> T sendPostRequest(String url, Object bodyContent, Class<T> c, ArrayList<String> headers) throws ExternalAPIException {
        ClientResponse clientResponse;
        Mono<ClientResponse> fluxResponse;

        WebClient.RequestHeadersSpec<?> requestSpec = webClient
                .post()
                .uri(URI.create(url))
                .body(BodyInserters.fromValue(bodyContent));

        headers.forEach( element -> {String[] strings = element.split(":");
            String key = strings[0];
            String value = strings[1];
            requestSpec.header(key, value);
        });

        try {
            fluxResponse = requestSpec.exchange();
        } catch (Exception ex) {
            log.warn("erreur lors de la requete url {}", url, ex);
            if (ex.getCause() instanceof UnknownHostException) {
                throw new ExternalAPIException(String.format("Le nom d'hôte %s est inconnu.", url));
            } else if (ex.getMessage().contains("Connection timed out")) {
                throw new ExternalAPIException(String.format("Impossible de se connecter à l'hôte %s", url));
            } else {
                throw new ExternalAPIException(String.format("Erreur lors de l'exécution de la requête %s", url));
            }
        }

        clientResponse = fluxResponse.block();

        if(clientResponse != null) {
            HttpStatus statusCode = clientResponse.statusCode();
            if (statusCode.value() == 200 || statusCode.value() == 201) {
                return clientResponse.bodyToMono(c).block();
            } else {
                throw new ExternalAPIException(String.format("Erreur lors de l'exécution de la requête POST %s code HTTP %s", url, statusCode.value()));
            }
        } else {
            throw new ExternalAPIException(String.format("Erreur lors de l'exécution de la requête %s", url));
        }
    }

}
