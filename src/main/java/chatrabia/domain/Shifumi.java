package chatrabia.domain;

public class Shifumi {
    private boolean activated = false;
    private int scoreUser = 0;
    private int scoreBot = 0;

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public int getScoreUser() {
        return scoreUser;
    }

    public void setScoreUser(int scoreUser) {
        this.scoreUser = scoreUser;
    }

    public int getScoreBot() {
        return scoreBot;
    }

    public void setScoreBot(int scoreBot) {
        this.scoreBot = scoreBot;
    }
}
