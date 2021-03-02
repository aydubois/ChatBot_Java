package chatrabia.service;

import chatrabia.domain.ChuckNorris;
import chatrabia.exception.ExternalAPIException;
import chatrabia.service.HttpService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ChuckNorrisService {

    private HttpService httpService;
    private static final String chuckApiRandomUrl = "http://api.icndb.com/jokes/random";

    public ChuckNorrisService(@Qualifier("httpService") HttpService httpService) {
        this.httpService = httpService;
    }

    public String getChuckNorrisRandomJoke() {
        try {
            ChuckNorris chuckNorris = httpService.sendGetRequest(chuckApiRandomUrl, ChuckNorris.class, null);

            return chuckNorris.getJoke();

        } catch (ExternalAPIException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getChuckNorrisRandomJokeByName(String name) {
        try {
            ChuckNorris chuckNorris = httpService.sendGetRequest(chuckApiRandomUrl+"?firstName="+name, ChuckNorris.class, null);

            return chuckNorris.getJoke();

        } catch (ExternalAPIException e) {
            e.printStackTrace();
        }
        return "";
    }
}
