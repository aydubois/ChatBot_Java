package chatrabia.service;

import chatrabia.domain.InsulteRusse;
import chatrabia.exception.ExternalAPIException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class InsulteRusseService extends GetHttp {
    private final String pattern = "(putain)|(connard)|(encul.*)|( bite )|(batard)|(prout)";
    private String urlAPI = "https://evilinsult.com/generate_insult.php?lang=ru&type=json";
    public InsulteRusseService(@Qualifier("httpService")HttpService httpService){super(httpService);}

    @Override
    public String get() {
        try {
            InsulteRusse insulteRusse = httpService.sendGetRequest(urlAPI, InsulteRusse.class, null);
            return insulteRusse.getInsult();

        } catch (ExternalAPIException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getPattern(){
        return pattern;
    }

}
