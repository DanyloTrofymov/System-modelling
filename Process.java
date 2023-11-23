public class Process extends Element {

    protected int servedA = 0;
    protected int servedB = 0;
    protected int servedC = 0;

    public Process(String name) {
        super(0, name);
        this.state = 0;
        this.tstate = Double.MAX_VALUE;
    }

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
               this.next.inAct(tcurr, currentTaskClass);
            }
            else{
                if(currentTaskClass != TaskClass.C) {
                    Model.timeOut.add(tcurr);
                } else if (this.name.equals("Process 1")){
                    Model.timeOut.add(tcurr);
                }
            }
        addServed();
    }

    public void outAct(double tcurr, double delay) {
        super.outAct(tcurr);
        this.state = 1;
        this.tstate = tcurr + delay;
        totalWorkTime += delay;
        if (this.next != null) {
            this.next.inAct(tcurr, currentTaskClass);
        }
        else{
            if(currentTaskClass != TaskClass.C) {
                Model.timeOut.add(tcurr);
            } else if (this.name.equals("Process 1")){
                Model.timeOut.add(tcurr);
            }
        }
        addServed();
    }

    private void addServed() {
        switch (currentTaskClass) {
            case A:
                servedA++;
                break;
            case B:
                servedB++;
                break;
            case C:
                servedC++;
                break;
        }
    }

    @Override
    public void calcMeanQueueLength(double delta) {
        this.meanQueue = this.meanQueue + this.queue.size() * delta;
    }
    @Override
    public void printResult() {
        super.printResult();
        System.out.println("\tServed A = " + this.servedA);
        System.out.println("\tServed B = " + this.servedB);
        System.out.println("\tServed C = " + this.servedC);
    }

    public void setNextElement(Element next) {
        this.next = next;
    }
}