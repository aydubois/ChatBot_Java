package chatrabia.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class KnockKnockService {

    private HttpService httpService;

    public KnockKnockService(@Qualifier("httpService") HttpService httpService) {

//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("https://knock-knock-jokes.p.rapidapi.com/knock-knock/random"))
//                .header("x-rapidapi-key", "e6111aaa23msh7ad3c23507c357cp1f97f9jsn187233b5cf50")
//                .header("x-rapidapi-host", "knock-knock-jokes.p.rapidapi.com")
//                .method("GET", HttpRequest.BodyPublishers.noBody())
//                .build();
//        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

    }
}
