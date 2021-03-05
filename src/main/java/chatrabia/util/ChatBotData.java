package chatrabia.util;

import chatrabia.bot.AssocNameLike;
import chatrabia.bot.AssocPatternResponse;
import chatrabia.bot.AssocWordCitation;
import chatrabia.service.*;

import java.util.ArrayList;

public class ChatBotData {
    private static volatile ChatBotData instance = null;
    private ArrayList<AssocPatternResponse> patternResponse = new ArrayList<>();
    private ArrayList<String> services = new ArrayList<>();
    private ArrayList<AssocWordCitation> assocCitations = new ArrayList<>();
    private ArrayList<AssocNameLike> assocNameLikeList = new ArrayList<>();

    private ChatBotData() {
        ParseXml parseXml = new ParseXml.ParseBuilder("fichiers/bot/config.xml").addXmlService("fichiers/bot/service.xml").
                addXmlCitation("fichiers/bot/fortuneAPI.xml").addXmlAdvertising("fichiers/bot/advertising.xml").build();

        patternResponse.addAll(parseXml.getPatternResponse());
        services.addAll(parseXml.getServices());
        assocCitations.addAll(parseXml.getAssocCitations());
        assocNameLikeList.addAll(parseXml.getAssocNameLikeList());
    }


    public static ChatBotData getInstance() {
        synchronized (ChatBotData.class) {
            if (instance == null) {
                instance = new ChatBotData();
            }
        }
        return instance;
    }

    // todo: ici proteger le get va etre un peu couteux
    public ArrayList<AssocPatternResponse> getPatternResponse() {
        return patternResponse;
    }
    // todo: pour proteger le get, il suffit de return un new arrayList ici (car String est immuable)
    public ArrayList<String> getServices() {
        return services;
    }
    public ArrayList<AssocWordCitation> getAssocCitations() {
        return assocCitations;
    }
    public ArrayList<AssocNameLike> getAssocNameLikeList() { return assocNameLikeList; }
}
