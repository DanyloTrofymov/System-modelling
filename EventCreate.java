import java.util.Objects;

public class EventCreate extends Event{
    public EventCreate(double delay, String name) {
        super(delay, name);
        tstate = 0.0;
    }
    public int serve(double tcurr, int state, Event prev, Event nextEvent) {
        super.serve(tcurr, state, prev, nextEvent);
        tstate = tcurr + getDelay();
        if (state == 0) {
            state = 1;
            double time = nextEvent.getDelay();
            nextEvent.tstate = tcurr + time;
            totalProcessingTime += time;
            if(nextEvent.queue < nextEvent.maxQueue) {
                nextEvent.queue++;
            } else {
                nextEvent.failure++;
            }
        }

        return state;
    }

    public int getCreated() {
        return served;
    }
}
