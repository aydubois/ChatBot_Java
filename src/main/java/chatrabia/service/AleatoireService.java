package chatrabia.service;

import chatrabia.domain.Kaamelott;
import chatrabia.exception.ExternalAPIException;
import chatrabia.util.Util;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AleatoireService {
    private String[] prenoms = {"Ali", "Malek", "Audrey","Eslam", "Jean-Lou", "Peter","Benoist", "Antho","Jean-Philippe", "Léonie", "Matthieu", "Nadim", "Nicolas D.","Océane", "Pierre-Yves", "Rémi", "Tommy","Clément"};
    private HttpService httpService;
    private static final String aleatoireAPI = "http://slogaanizer.free.fr/sloganize.php";
    private static final String aleatoireJoieCodeAPI = "https://lesjoiesducode.fr/random";
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
    public String[] getAleatoireJoieCode(){
        try {
            ArrayList<String> headers = new ArrayList<>();
            headers.add("Accept:application/json");

            String sentence = httpService.sendGetRequest(aleatoireJoieCodeAPI, String.class, headers);

            String[] datas = traitementJoie(sentence);

            return datas;


        } catch (ExternalAPIException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String traitement(String sentence){
        String[] strings = sentence.split("<span id=\"slogan\" style=\"font-size: 34px;\">");

        String[] sentencePresqueOK = strings[1].split("</span>");
        String sentenceOK = sentencePresqueOK[0];
        int randomInt = Util.getRandom(0, prenoms.length-1);
        return sentenceOK.replace("Slogaanizer", prenoms[randomInt]);
    }

    private String[] traitementJoie(String sentence){
        String[] strings = sentence.split("<h1 class=\"blog-post-title single-blog-post-title\">");
        String[] sentencePresqueOK = strings[1].split("</h1>");
        String titre = sentencePresqueOK[0];
        String[] urlImagePresqueOK = strings[1].split(" type=image/gif>");
        urlImagePresqueOK = urlImagePresqueOK[0].split("data=");
        String urlImage =  urlImagePresqueOK[1];
        String[] datas = {titre, urlImage};
        return datas;

    }
}
