import java.util.List;

public class Create extends Element{
    ClientType clientType;
    public Create(double delay, String name) {
        super(delay, name);
    }
    public Create(double delay, String name, ClientType clientType) {
        super(delay, name);
        this.clientType = clientType;
    }
    @Override
    public void outAct(double tcurr) {
        super.outAct(tcurr);
        double delay = getDelay();
        totalWorkTime += delay;
        tstate = tcurr + delay;
        Element nextEl = next.getNextElement(currentClientType);
        nextEl.delay = getServiceDelayOnType();
        nextEl.inAct(tcurr, clientType);
    }
    @Override
    public void doStatistics(double delta) {
        if (queue != null) {
            meanQueue = meanQueue + queue.size() * delta;
        }
    }

    private double getServiceDelayOnType() {
        switch (clientType) {
            case FIRST:
                return 15;
            case SECOND:
                return 40;
            case THIRD:
                return 30;
            default:
                return 0.0;
        }
    }
}