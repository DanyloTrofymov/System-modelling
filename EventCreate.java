import java.util.List;

public class EventCreate extends Event{
    public EventCreate(double delay, String name) {
        super(delay, name);
    }
    @Override
    public void outAct(double tcurr, List<Event> events) {
        super.outAct(tcurr, events);
        tstate = tcurr + getDelay();
        next.inAct(tcurr);
    }
    @Override
    public void doStatistics(double delta) {
        meanQueue = meanQueue + queue * delta;
    }
}
