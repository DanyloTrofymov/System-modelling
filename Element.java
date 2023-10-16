import java.util.List;

public class Element {
    protected double meanQueue, tstate, delay, totalWorkTime;
    protected int state, queue, maxQueue, failure, served;
    protected NextElements next;

    protected String name;

    public Element(String name) {
        this.delay = 0;
        maxQueue = 0;
        this.name = name;
        tstate = 0;
        next = null;

    }
    public Element(double delay, String name) {
        this.delay = delay;
        maxQueue = 0;
        this.name = name;
        tstate = 0;
        next = null;

    }
    public Element(double delay, String name, int maxQueue) {
        this.delay = delay;
        this.maxQueue = maxQueue;
        this.name = name;
        tstate = 0;
        next = null;
    }

    public void inAct(double tcurr) {
    }
    public void outAct(double tcurr){
        served++;
    }
    protected void printInfo() {
        System.out.println("Event = " + name + " tnext = " + tstate + " queue: " + queue + " state = " + state);
    }
    protected void printStatistic(double timeModeling){
        System.out.println("Event = " + name + " served = " + served + " failure = "+failure);
    }
    public void printResult(){
        System.out.println(name+ " served = "+ served);
    }

    public void doStatistics(double delta){
    }
    public void setNextElement(NextElements next) {
        this.next = next;
    }


    protected double getDelay() {
        return FunRand.exp(delay);
    }

    protected void setTstate(double tstate) {
        this.tstate = tstate;
    }

    protected void setState(int state) {
        this.state = state;
    }
}