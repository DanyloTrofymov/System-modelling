import java.util.List;

public class Event {
    protected double meanQueue, tstate, delay;
    protected int state, queue, maxQueue, failure, served;
    protected Event next;
    protected String name;

    public Event(double delay, String name) {
        this.delay = delay;
        maxQueue = 0;
        this.name = name;
        tstate = 0;
        next = null;

    }
    public Event(double delay, String name, int maxQueue) {
        this.delay = delay;
        this.maxQueue = maxQueue;
        this.name = name;
        tstate = 0;
        next = null;
    }

    public void inAct(double tcurr) {
    }
    public void outAct(double tcurr, List<Event> events){
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
    public void setNextElement(Event nextElement) {
        this.next = nextElement;
    }
    protected double getDelay() {
        return FunRand.exp(delay);
    }
}
