/**
 * Created by Mind on 3/11/2016.
 */

/*used to measure the time it takes to make a move*/
public class TimeCheck {
    private long start, spent = 0;
    public TimeCheck() { play(); }
    public double check() { return (System.nanoTime()-start+spent)/1e9; }
    public void pause() { spent += System.nanoTime()-start; }
    public void play() { start = System.nanoTime(); }
}