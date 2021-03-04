package chatrabia.service;

import chatrabia.domain.ChuckNorris;
import chatrabia.domain.Kaamelott;
import chatrabia.exception.ExternalAPIException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class KaamelottService {
    private HttpService httpService;
    private static final String kaamelottAPI = "https://kaamelott.chaudie.re/api/random";
    public KaamelottService(@Qualifier("httpService")HttpService httpService){this.httpService = httpService;}

    public String getKaamelott() {
        try {
            Kaamelott kaamelott = httpService.sendGetRequest(kaamelottAPI, Kaamelott.class, null);

            return kaamelott.getCitation().getCitation();

        } catch (ExternalAPIException e) {
            e.printStackTrace();
        }
        return "";
    }

}
