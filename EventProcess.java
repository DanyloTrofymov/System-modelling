import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class EventProcess extends Event {
    private int countOfWorkers;
    private List<Integer> workerStates = new ArrayList<>();
    private List<Double> workersTnext = new ArrayList<>();
    public EventProcess(double delay, String name, int maxQueue, int countOfWorkers) {
        super(delay, name, maxQueue);
        this.countOfWorkers = countOfWorkers;
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

    @Override
    public void outAct(double tcurr) {
        super.outAct(tcurr);
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
