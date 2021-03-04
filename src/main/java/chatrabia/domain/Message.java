package chatrabia.domain;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;

@Component
public class Message implements Serializable {

    private static int currentId = 0;

    private final int id;
    private String userName = null;
    private String userMessage = null;
    private String botName = "Nono";
    private ArrayList<String> botMessage = new ArrayList<>();
    private String option = null;

    public Message() {

        this.id = currentId;
        currentId++;
    }

    public int getId() {
        return id;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public ArrayList<String> getBotMessage() {
        return botMessage;
    }

    public void addBotMessage(String botMessage) {
        this.botMessage.add(botMessage);
    }
    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }
}
