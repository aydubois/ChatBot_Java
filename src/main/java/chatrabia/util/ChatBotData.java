package chatrabia.util;

import chatrabia.bot.AssocPatternResponse;

import java.util.ArrayList;

public class ChatBotData {
    private static volatile ChatBotData instance = null;
    private ArrayList<AssocPatternResponse> patternResponse = new ArrayList<>();


    private ChatBotData() {
        ParseXml parseXml = new ParseXml.ParseBuilder("fichiers/bot/config.xml").build();
        patternResponse.addAll(parseXml.getPatternResponse());
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

}
