public class EventProcess extends Event {
    public EventProcess(double delay, String name, int maxQueue) {
        super(delay, name, maxQueue);
    }

    public int serve(double tcurr, int state, Event prev, Event nextEvent) {
        super.serve(tcurr, state, prev, nextEvent);
        state = 0;
        tstate = Double.MAX_VALUE;
        if (queue > 0) {
            queue--;
            state = 1;
            double time = getDelay();
            tstate = tcurr + time;
            totalProcessingTime += time;
            if (nextEvent != null) {
                time = nextEvent.getDelay();
                nextEvent.tstate = tcurr + time;
                totalProcessingTime += time;
                if(nextEvent.queue < nextEvent.maxQueue) {
                    nextEvent.queue++;
                } else {
                    nextEvent.failure++;
                }
            }
        }
        return state;
    }
}
