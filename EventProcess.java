public class EventProcess extends Event {
    public EventProcess(double delay, String name, int maxQueue) {
        super(delay, name, maxQueue);
    }

    @Override
    public void inAct(double tcurr) {
        if (state == 0) {
            state = 1;
            tstate = tcurr + getDelay();
        } else {
            if (queue < maxQueue) {
                queue += 1;
            } else {
                failure++;
            }
        }
    }

    @Override
    public void outAct(double tcurr) {
        super.outAct(tcurr);
        tstate = Double.MAX_VALUE;
        state = 0;
        if (queue > 0) {
            queue -= 1;
            state = 1;
            tstate = tcurr + getDelay();
        }
        if (next != null) {
            next.inAct(tcurr);
        }
    }

    @Override
    public void doStatistics(double delta) {
        meanQueue = meanQueue + queue * delta;
    }
    @Override
    public void printResult() {
        super.printResult();
        System.out.println("failure = " + this.failure);
    }

}
