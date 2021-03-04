package chatrabia.service;

import chatrabia.domain.ChuckNorris;
import chatrabia.domain.Kaamelott;
import chatrabia.exception.ExternalAPIException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class KaamelottService {

    public class Citation {
        private String citation = "";
        private String personnage = "";

        public Citation(String citation, String personnage) {
            this.citation = citation != null ? citation : "plop";
            this.personnage = personnage  != null ? personnage : "";
        }

        public String getCitation() {
            return citation;
        }

        public String getPersonnage() {
            return personnage;
        }
    }

    private HttpService httpService;
    private static final String kaamelottAPI = "https://kaamelott.chaudie.re/api/random";
    public KaamelottService(@Qualifier("httpService")HttpService httpService){this.httpService = httpService;}

    public Citation getKaamelott() {

        try {
            Kaamelott kaamelott = httpService.sendGetRequest(kaamelottAPI, Kaamelott.class, null);
            return new Citation(kaamelott.getCitation().getCitation(), kaamelott.getCitation().getInfos().getPersonnage());

        } catch (ExternalAPIException e) {
            e.printStackTrace();
        }

        return new Citation("", "");
    }

}
