/*import java.util.*;

public class Process_old extends Element {
    private List<Integer> workerStates = new ArrayList<>();
    private List<Double> workersTnext = new ArrayList<>();
    public Process_old(double delay, String name, int maxQueue, int countOfWorkers) {
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
            double delay = getDelay();
            workersTnext.set(workerIndex, tcurr + delay);
            tstate = Collections.min(workersTnext);
        } else {
            if (queue < maxQueue) {
                queue += 1;
            } else {
                failure++;
            }
        }
    }

    public void setNextElement(List <Element> events) {
        int processElementsCount = events.size() - 1;
        double step = 1.0 / processElementsCount;
        double randomNumber = Math.random();
        for (int i = 0; i < processElementsCount; i++) {
            if (randomNumber >= i * step && randomNumber < (i + 1) * step) {
                Element nextElement = events.get(i + 1);
                if(!Objects.equals(nextElement.name, this.name)) {
                    next = nextElement;
                }
                break;
            }
        }
    }

    @Override
    public void outAct(double tcurr, List <Element> events) {
        super.outAct(tcurr, events);
        int workerIndex = workersTnext.indexOf(tcurr);
        workerStates.set(workerIndex, 0);
        workersTnext.set(workerIndex, Double.MAX_VALUE);
        tstate = Collections.min(workersTnext);
        if (queue > 0) {
            queue -= 1;
            workerStates.set(workerIndex, 1);
            double delay = getDelay();
            totalWorkTime += delay;
            workersTnext.set(workerIndex, tcurr + delay);
            tstate = Collections.min(workersTnext);
        }
        setNextElement(events);
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

}*/