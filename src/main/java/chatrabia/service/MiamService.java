package chatrabia.service;

import chatrabia.exception.ExternalAPIException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MiamService extends GetHttp {
    private String pattern = ".*j'ai\s*faim.*|.*on va au rest.*?|.*[Mm]iam.*";

    public MiamService(@Qualifier("httpService")HttpService httpService){
            super(httpService);
    }
    @Override
    public String get() {
        try {
            ArrayList<String> headers = new ArrayList<>();
            headers.add("Accept:application/json");
            String sentence = httpService.sendGetRequest("https://foodish-api.herokuapp.com/api", String.class, headers);
            System.out.println(sentence);
            sentence = sentence.replace("{\"image\":\"", "");
            sentence = sentence.replace("\"}", "");
            return sentence;
        } catch (ExternalAPIException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getPattern() {
        return pattern;
    }
}
