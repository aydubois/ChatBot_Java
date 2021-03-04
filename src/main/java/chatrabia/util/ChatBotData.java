package chatrabia.util;

import chatrabia.bot.AssocPatternResponse;
import chatrabia.bot.AssocWordCitation;

import java.util.ArrayList;

public class ChatBotData {
    private static volatile ChatBotData instance = null;
    private ArrayList<AssocPatternResponse> patternResponse = new ArrayList<>();
    private ArrayList<String> services = new ArrayList<>();
    private ArrayList<AssocWordCitation> assocCitations = new ArrayList<>();


    private ChatBotData() {
        ParseXml parseXml = new ParseXml.ParseBuilder("fichiers/bot/config.xml").addXmlService("fichiers/bot/service.xml").
                addXmlCitation("fichiers/bot/fortuneAPI.xml").build();
        patternResponse.addAll(parseXml.getPatternResponse());
        services.addAll(parseXml.getServices());
        assocCitations.addAll(parseXml.getAssocCitations());

    }


    public static ChatBotData getInstance() {
        if (instance == null) {
            synchronized (ChatBotData.class) {
                instance = new ChatBotData();
            }
        }
        return instance;
    }

    // ici proteger le get va etre un peu couteux
    public ArrayList<AssocPatternResponse> getPatternResponse() {
        return patternResponse;
    }
    // pour proteger le get, il suffit de return un new arrayList ici (car String est immuable)
    public ArrayList<String> getServices() {
        return services;
    }
    public ArrayList<AssocWordCitation> getAssocCitations() {
        return assocCitations;
    }

}
