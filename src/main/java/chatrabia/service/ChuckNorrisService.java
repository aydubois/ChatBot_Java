package chatrabia.service;

import chatrabia.domain.ChuckNorris;
import chatrabia.exception.ExternalAPIException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ChuckNorrisService extends GetHttp {
    private static final String pattern = ".*[Cc]huck.*|.*CHUCK.*|.*[Nn]orris.*|.*[Bb]lagu.*|.*[Jj]oke.*";
    private static final String urlAPI = "http://api.icndb.com/jokes/random?escape=javascript";

    public ChuckNorrisService(@Qualifier("httpService") HttpService httpService) {
        super(httpService);
    }

    public String get() {
        try {
            ChuckNorris chuckNorris = httpService.sendGetRequest(urlAPI, ChuckNorris.class, null);
            return chuckNorris.getJoke();

        } catch (ExternalAPIException e) {
            e.printStackTrace();
        }
        return "";
    }

    //Pas utilisé pour le moment
    public String getChuckNorrisRandomJokeByName(String name) {
        try {
            ChuckNorris chuckNorris = httpService.sendGetRequest(urlAPI+"?firstName="+name, ChuckNorris.class, null);

            return chuckNorris.getJoke();

        } catch (ExternalAPIException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getPattern() {
        return pattern;
    }
}
