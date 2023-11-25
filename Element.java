import java.util.List;

public class Element {
    protected double tstate, delay, delayfrom, delayto, totalWorkTime;
    protected int state, failure, served, k;

    protected TaskClass currentTaskClass;
    protected Element next;
    protected List<TaskClass> queue;
    protected DistributionType distributionType = DistributionType.EXPONENTIAL;

    protected String name;

    public Element(String name) {
        this.delay = 0;
        this.name = name;
        tstate = 0;
        next = null;

    }
    public Element(double delay, String name) {
        this.delay = delay;
        this.name = name;
        tstate = 0;
        next = null;

    }
    public void inAct(double tcurr) {
    }
    public void inAct(double tcurr, TaskClass taskClass) {
    }

    public void outAct(double tcurr){
        served++;
    }

    public void outAct(double tcurr, TaskClass taskClass){
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

    public void calcMeanQueueLength(double delta){
    }
    public void setNextElement(Element next) {
        this.next = next;
    }

    protected double getDelay() {
        switch (distributionType) {
            case EXPONENTIAL:
                return FunRand.exp(delay);
            case UNIFORM:
                return FunRand.unif(delayfrom, delayto);
            case NORMAL:
                return FunRand.norm(delay, 0.4);
            case ERLANG:
                return FunRand.erlang(delay, k);
            default:
                return FunRand.exp(delay);
        }
    }

    protected void setTstate(double tstate) {
        this.tstate = tstate;
    }

    protected void setState(int state) {
        this.state = state;
    }


}