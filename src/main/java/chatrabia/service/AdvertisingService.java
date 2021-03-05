package chatrabia.service;

import chatrabia.bot.AssocNameLike;
import chatrabia.util.ChatBotData;
import chatrabia.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class AdvertisingService {

    private static final Logger log = LogManager.getRootLogger();

    private static final Path filePath = Paths.get("fichiers/bot/advertising.xml");

    public static final String likePattern = "[Jj][e']\\s*veux\\s+(?:de|d'|des|du|un|une|les|le|la|l')?\\s*(.*)"+"|"
                                                +"[Jj]'ai\\senvi.*?\\s(?:de|d')\\s+(.*)"+"|"
                                                +"[Jj]'aime\\s+(?:de|d'|des|du|un|une|les|le|la|l')?\\s*(.*)";
    private final ChatBotData chatBotData;
    private ArrayList<AssocNameLike> assocNameLikeList = new ArrayList<>();
    private final String[] advertisingSlogans = { "Offre speciale ! %s à seulement 99.99€ !!",
                                                "Vous en avez revé, Chat'Rabia l'a fait ! %s à seulement 0.99€" };


    public AdvertisingService() {
        chatBotData = ChatBotData.getInstance();
        assocNameLikeList = chatBotData.getAssocNameLikeList();
    }

    public String getMessage(String userName) {

        if(userName == null || userName.isEmpty()) return null;

        String slogan = getRandomSlogan();
        if(slogan == null) return null;

        List<String> likeList = getUserWishList(userName);

        if(likeList == null && likeList.size() == 0) return null;

        int randomLikeIndex = Util.getRandom(0, likeList.size() -1);
        String like = likeList.get(randomLikeIndex);

        return String.format(slogan, like);
    }

    private String getRandomSlogan() {
        if(advertisingSlogans == null || advertisingSlogans.length == 0) return null;

        int randomSloganIndex = Util.getRandom(0, advertisingSlogans.length -1);
        return advertisingSlogans[randomSloganIndex];
    }

    public List<String> getUserWishList(String userName) {
        if(userName == null || userName.isEmpty()) return null;

        return assocNameLikeList.stream()
                .distinct()
                .filter((assocNameLike) -> userName.equals(assocNameLike.getName()))
                .map((assocNameLike) -> assocNameLike.getLike())
                .collect(Collectors.toList());
    }

    private String getWish(String message) {
        Pattern pattern = Pattern.compile(likePattern);
        Matcher matcher = pattern.matcher(message);

        String likeMatch = null;

        if(matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                if(matcher.group(i) != null ) likeMatch = matcher.group(i);
            }
        }
        return likeMatch;
    }

    private boolean isAWish(String message) {
        return message.matches(likePattern);
    }

    public void writeAWish(String userName, String message) {
        if(!isAWish(message)) return;

        String wish = getWish(message);
        if(wish == null || "".equals(wish)) return;

        List<String> lines = null;
        try {
            lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);

        } catch (IOException e) {
            log.error(e.toString());
        }

        int position = lines.size() - 1;

        String[] newLines = {
                "<like>",
                "<name>"+userName+"</name>",
                "<value>"+wish+"</value>",
                "</like>"
        };

        for (int i = newLines.length - 1; i >= 0 ; i--) {
            lines.add(position, newLines[i]);
        }

        try {
            Files.write(filePath, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error(e.toString());
        }

        assocNameLikeList = chatBotData.getAssocNameLikeList();
    }
}
