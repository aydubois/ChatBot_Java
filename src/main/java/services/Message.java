package services;

public class Message {
    private final int id;
    private String userMessage;
    private String botMessage;
    private String option;

    public Message(int id) {
        this.id = id;
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
}
