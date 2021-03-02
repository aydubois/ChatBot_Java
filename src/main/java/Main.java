import chatrabia.util.ChatBotData;

public class Main {

    public static void main(String[] args) {
        ChatBotData.getInstance().getPatternResponse().forEach(pr -> {
            pr.afficher();
            System.out.println("//////////////////");
        });
    }
}
