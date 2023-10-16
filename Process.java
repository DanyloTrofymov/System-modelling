public class Process extends Element {
    public boolean hasOwnQueue = true;
    public Process(double delay, String name, int maxQueue) {
        super(delay, name, maxQueue);
        this.tstate = Double.MAX_VALUE;
    }

    @Override
    public void inAct(double tcurr) {
        if (this.state == 0) {
            this.state = 1;
            this.tstate = tcurr + getDelay();
        } else {
            if (hasOwnQueue && this.queue < this.maxQueue) {
                this.queue += 1;
            } else {
                this.failure++;
            }
        }
    }

    @Override
    public void outAct(double tcurr) {
        super.outAct(tcurr);
        this.tstate = Double.MAX_VALUE;
        this.state = 0;
        if (hasOwnQueue && this.queue > 0) {
            this.queue -= 1;
            this.state = 1;
            this.tstate = tcurr + getDelay();
            if (this.next != null) {
                this.next.inAct(tcurr);
            }
        }
        else if (!hasOwnQueue){
            this.state = 1;
            this.tstate = tcurr + getDelay();
            if (this.next != null) {
                this.next.inAct(tcurr);
            }
        }
    }

    @Override
    public void doStatistics(double delta) {
        this.meanQueue = this.meanQueue + this.queue * delta;
    }
    @Override
    public void printResult() {
        super.printResult();
        System.out.println("failure = " + this.failure);
    }

}