package chatrabia.service;

import chatrabia.domain.Kaamelott;
import chatrabia.exception.ExternalAPIException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class KaamelottService extends GetHttp {
    private final String urlAPI = "https://kaamelott.chaudie.re/api/random";
    public KaamelottService(@Qualifier("httpService")HttpService httpService){super(httpService);}

    public String get() {
        try {
            Kaamelott kaamelott = httpService.sendGetRequest(urlAPI, Kaamelott.class, null);

            return kaamelott.getCitation().getCitation();

        } catch (ExternalAPIException e) {
            e.printStackTrace();
        }
        return "";
    }

}
