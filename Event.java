public class Event {
    protected double tstate, delay, totalProcessingTime;
    protected int queue, maxQueue, failure, served;
    protected Event next;
    protected String name;

    public Event(double delay, String name) {
        this.delay = delay;
        maxQueue = 0;
        this.name = name;
        tstate = Double.MAX_VALUE;
        next = null;
    }
    public Event(double delay, String name, int maxQueue) {
        this.delay = delay;
        this.maxQueue = maxQueue;
        this.name = name;
        tstate = Double.MAX_VALUE;
        next = null;
    }

    protected int serve (double tcurr, int state, Event prev, Event next) {
        served++;
        return state;
    }

    protected void printInfo() {
        System.out.println("Event = " + name + " tstate = " + tstate + " queue: " + queue);
    }
    protected void printStatistic(double timeModeling){
        System.out.println("Event = " + name + " computed= " + served + " failure = "+failure + " avgProcessingTime = " + totalProcessingTime / timeModeling);
    }

    protected double getDelay() {
        return FunRand.exp(delay);
    }
}
