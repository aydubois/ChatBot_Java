package chatrabia.util;

import chatrabia.bot.AssocPatternResponse;

import java.util.ArrayList;

public class ChatBotData {
    private static volatile ChatBotData instance = null;
    private ArrayList<AssocPatternResponse> patternResponse = new ArrayList<>();
    private ArrayList<String> services = new ArrayList<>();


    private ChatBotData() {
        ParseXml parseXml = new ParseXml.ParseBuilder("fichiers/bot/config.xml").addXmlService("fichiers/bot/service.xml").build();
        patternResponse.addAll(parseXml.getPatternResponse());
        services.addAll(parseXml.getServices());

    }

    public static ChatBotData getInstance() {
        if (instance == null) {
            synchronized (ChatBotData.class) {
                instance = new ChatBotData();
            }
        }
        return instance;
    }


    public ArrayList<AssocPatternResponse> getPatternResponse() {
        return patternResponse;
    }
    public ArrayList<String> getServices() {
        return services;
    }

}
