package chatrabia.domain;

import org.springframework.stereotype.Component;

import java.io.Serializable;
@Component
public class Message implements Serializable {

    private static int currentId = 0;

    private final int id;
    private String userName;
    private String userMessage;
    private String botMessage;
    private String option;

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

    public String getBotMessage() {
        return botMessage;
    }

    public void setBotMessage(String botMessage) {
        this.botMessage = botMessage;
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
}
