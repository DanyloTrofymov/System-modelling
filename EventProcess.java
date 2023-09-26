import java.util.*;

public class EventProcess extends Event {
    private List<Integer> workerStates = new ArrayList<>();
    private List<Double> workersTnext = new ArrayList<>();
    public EventProcess(double delay, String name, int maxQueue, int countOfWorkers) {
        super(delay, name, maxQueue);
        tstate = Double.MAX_VALUE;
        for (int i = 0; i < countOfWorkers; i++) {
            workerStates.add(0);
            workersTnext.add(Double.MAX_VALUE);
        }
    }

    @Override
    public void inAct(double tcurr) {
        int workerIndex = workerStates.indexOf(0);
        if (workerIndex != -1) {
            workerStates.set(workerIndex, 1);
            workersTnext.set(workerIndex, tcurr + getDelay());
            tstate = Collections.min(workersTnext);
        } else {
            if (queue < maxQueue) {
                queue += 1;
            } else {
                failure++;
            }
        }
    }

    public void setNextEvent(List <Event> events) {
        int processEventsCount = events.size() - 1;
        double step = 1.0 / processEventsCount;
        double randomNumber = Math.random();
        for (int i = 0; i < processEventsCount; i++) {
            if (randomNumber >= i * step && randomNumber < (i + 1) * step) {
                Event nextEvent = events.get(i + 1);
                if(!Objects.equals(nextEvent.name, this.name)) {
                    next = nextEvent;
                }
                break;
            }
        }
    }

    @Override
    public void outAct(double tcurr, List <Event> events) {
        super.outAct(tcurr, events);
        int workerIndex = workersTnext.indexOf(tcurr);
        workerStates.set(workerIndex, 0);
        workersTnext.set(workerIndex, Double.MAX_VALUE);
        tstate = Collections.min(workersTnext);
        if (queue > 0) {
            queue -= 1;
            workerStates.set(workerIndex, 1);
            workersTnext.set(workerIndex, tcurr + getDelay());
            tstate = Collections.min(workersTnext);
        }
        setNextEvent(events);
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
