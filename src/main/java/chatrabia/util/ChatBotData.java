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

    // todo: si tu fais un getInstance() comme pour un singleton, pourquoi faire un new dedans et pas dans  private static volatile ChatBotData instance = null;
    //    pourquoi le synchronized ici (je n'ai pas encore regarder autour :p)
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

}
