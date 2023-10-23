import java.util.List;
import java.util.ArrayList;

public class Model {
    private double tnext;
    private double tcurr;
    private List <Element> elements = new ArrayList<>();

    public Model(Create create, List<MultiTaskProcessor> process) {
        tnext=0.0;
        tcurr = tnext;
        elements.add(create);
        for (Element element : process) {
            elements.add(element);
        }
    }

    /**
     * Метод моделювання
     * @param timeModeling - час моделювання в умовних одиницях часу
     */
    public void simulate(double timeModeling){
        while(tcurr<timeModeling) {
            tnext = Double.MAX_VALUE;       // Час наступної події
            Element nextElement = null;         // Подія, яка станеться найближчою

            for (Element element : elements) {
                if (element.tstate < tnext) {
                    tnext = element.tstate;
                    nextElement = element;
                }
            }
            System.out.println("\nIt's time for element in " +
                    nextElement.name +
                    ", time = " + tnext);
            for (Element e : elements) {
                e.doStatistics(tnext - tcurr);
            }
            tcurr = tnext;

            for (Element element : elements) {
                if(element.tstate == tcurr) {
                    element.outAct(tcurr);
                }
            }
            tryToSwitchQueue();
            printInfo();
        }
        printResult(timeModeling);
    }
    public void printInfo() {
        for (Element e : elements) {
            if(e.state == 1)
                e.printInfo();
        }
    }
    public void printResult( double timeModeling) {
        System.out.println("\n-------------RESULTS-------------");
        for (Element e : elements) {
            e.printResult();
            if (e instanceof MultiTaskProcessor) {
                MultiTaskProcessor p = (MultiTaskProcessor) e;
                System.out.println("mean length of queue = " +
                        p.meanQueue / tcurr
                        + "\nfailure probability = " +
                        p.failure / ((double) p.served + p.failure) +
                        "\nawg load time = " + p.getTotalWorkTime() / p.getProucessCount() / timeModeling);
                for (Process process : p.getProcesses()) {
                    System.out.println("load time in " + process.name + " = " + process.totalWorkTime / timeModeling);
                }
            }
            System.out.println();
        }
    }

    // method only for bank task
    public void tryToSwitchQueue() {
        int minQueue = Integer.MAX_VALUE;
        int maxQueue = 0;
        Element minQueueElement = null;
        Element maxQueueElement = null;
        for (Element element : elements) {
            if (element.queue < minQueue) {
                minQueue = element.queue;
                minQueueElement = element;
            }
            if (element.queue > maxQueue) {
                maxQueue = element.queue;
                maxQueueElement = element;
            }
        }
        double randValue = Math.random();
        if (minQueueElement != null && maxQueueElement != null) {
            if(maxQueueElement.queue - minQueueElement.queue >= 2 && randValue < 0.5) {
                minQueueElement.queue += 1;
                maxQueueElement.queue -= 1;
            }
        }
    }
}