/**
 * Created by Mind on 3/11/2016.
 */

//Action class used to determine the best action in decideNextMove function.
public class Action {
    private int action;
    private Integer score;

    public Action(int action, int score) {
        this.score = score;
        this.action = action;
    }

    public Integer getScore() {
        return score;
    }

    public int getAction() {
        return action;
    }
}
