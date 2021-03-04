package chatrabia.service;

import chatrabia.domain.Kaamelott;
import chatrabia.exception.ExternalAPIException;
import chatrabia.util.Util;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AleatoireService {
    private String[] prenoms = {"Ali", "Malek", "Audre Bo.", "Audrey Br.", "Audrey D.", "Eslam", "Jean-Lou", "Peter","Benoist", "Antho","Jean-Philippe", "Léonie", "Matthieu", "Nadim", "Nicolas D.","Nicolas R.","Océane", "Pierre-Yves", "Rémi", "Tommy","Clément"};
    private HttpService httpService;
    private static final String aleatoireAPI = "http://slogaanizer.free.fr/sloganize.php";
    public AleatoireService(@Qualifier("httpService")HttpService httpService){this.httpService = httpService;}

    public String getAleatoire() {
        try {
            ArrayList<String> headers = new ArrayList<>();
            headers.add("Accept:application/json");

            String sentence = httpService.sendPostRequest(aleatoireAPI,"ponyo", String.class, headers);

            sentence = traitement(sentence);
            return sentence;


        } catch (ExternalAPIException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String traitement(String sentence){
        String[] strings = sentence.split("<span id=\"slogan\" style=\"font-size: 34px;\">");

        String[] sentencePresqueOK = strings[1].split("</span>");
        String sentenceOK = sentencePresqueOK[0];
        int randomInt = Util.getRandom(0, prenoms.length-1);
        return sentenceOK.replace("Slogaanizer", prenoms[randomInt]);
    }
}
