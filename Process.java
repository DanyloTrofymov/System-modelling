import org.w3c.dom.events.Event;

public class Process extends Element {

    Event firstProcess;
    public Process(double delay, String name) {
        super(delay, name);
        this.tstate = Double.MAX_VALUE;
    }

    @Override
    public void outAct(double tcurr) {
        super.outAct(tcurr);
            this.state = 1;
            double delay = getDelay();
            this.tstate = tcurr + delay;
            totalWorkTime += delay;
            if (this.next != null) {
                Element thisNext = this.next.getNextElement(currentClientType);
                thisNext.inAct(tcurr, currentClientType);
            }
            else if (currentClientType != ClientType.FIRST){

            }
    }

    @Override
    public void doStatistics(double delta) {
        this.meanQueue = this.meanQueue + this.queue.size() * delta;
    }
    @Override
    public void printResult() {
        super.printResult();
        System.out.println("failure = " + this.failure);
    }

    public void setNextElement(NextElementsOnClientType next) {
        this.next = next;
    }
}