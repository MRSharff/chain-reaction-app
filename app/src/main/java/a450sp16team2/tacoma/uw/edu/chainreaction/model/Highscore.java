package a450sp16team2.tacoma.uw.edu.chainreaction.model;

/**
 * Created by mat on 6/1/16.
 */
public class Highscore {
    public final String myUsername;
    public final int myScore;

    public Highscore(String theUsername, int theScore) {
        myUsername = theUsername;
        myScore = theScore;
    }

    public String getUsername() {
        return myUsername;
    }

    public int getScore() {
        return myScore;
    }
}
