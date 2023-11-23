import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Model {
    private double tnext;
    private double tcurr;
    private List <Element> elements = new ArrayList<>();
    public static List<Double> timeIn = new ArrayList<>();
    public static List<Double> timeOut = new ArrayList<>();

    public static List<Double> queueIn = new ArrayList<>();
    public static List<Double> queueOut = new ArrayList<>();
    private double timeModeling;

    public Model(List<Create> create, List<MSS> process) {
        tnext=0.0;
        tcurr = tnext;
        elements.addAll(create);
        elements.addAll(process);
    }

    /**
     * Метод моделювання
     * @param timeModeling - час моделювання в умовних одиницях часу
     */
    public void simulate(double timeModeling){
        this.timeModeling = timeModeling;
        while(tcurr<timeModeling) {
            tnext = Double.MAX_VALUE;       // Час наступної події
            Element nextElement = null;         // Подія, яка станеться найближчою

            for (Element element : elements) {
                if (element.tstate < tnext) {
                    tnext = element.tstate;
                    nextElement = element;
                }
            }
            /*System.out.println("\nIt's time for element in " +
                    nextElement.name +
                    ", time = " + tnext);*/
            for (Element e : elements) {
                e.calcMeanQueueLength(tnext - tcurr);
            }
            tcurr = tnext;

            for (Element element : elements) {
                if(element.tstate == tcurr) {
                    element.outAct(tcurr, element.currentTaskClass);
                }
            }
            //printInfo();
        }
        //printResult();
    }
    public void printInfo() {
        for (Element e : elements) {
            if(e.state == 1)
                e.printInfo();
        }
    }
    public HashMap<StatisticDataTypes, Double> getStatistics(){
        HashMap<StatisticDataTypes, Double> statistics = new HashMap<>();
        double meanQueueLength = 0;
        double servedA = 0;
        double servedB = 0;
        double servedC = 0;
        int processCount = 0;
        for (Element e : elements) {
            if (e instanceof MSS) {
                MSS p = (MSS) e;
                meanQueueLength = p.meanQueue / tcurr;
                for (Process process : p.processes) {
                    servedA += process.servedA;
                    servedB += process.servedB;
                    servedC += process.servedC;
                    processCount = p.processes.size();
                }
            }
        }
        statistics.put(StatisticDataTypes.meanQueueLength, meanQueueLength);
        statistics.put(StatisticDataTypes.meanTimeInQueue, getMeanTimeInQueue());
        statistics.put(StatisticDataTypes.meanTimeInSystem, getMeanTimeInSystem());
        statistics.put(StatisticDataTypes.servedA, servedA);
        statistics.put(StatisticDataTypes.servedB, servedB);
        statistics.put(StatisticDataTypes.servedC, servedC / processCount);
        return statistics;
    }
    public void printResult() {
        System.out.println("\n-------------RESULTS-------------");
        for (Element e : elements) {
            e.printResult();
            if (e instanceof MSS) {
                MSS p = (MSS) e;
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

        System.out.println("mean time in system = " + getMeanTimeInSystem());

        System.out.println("mean time in queue = " + getMeanTimeInQueue());
    }

    private double getMeanTimeInSystem(){
        double sumTimeIn = 0;
        double sumTimeOut = 0;
        int elementCount = timeOut.size();
        for (int i = 0; i < elementCount; i++) {
            sumTimeIn += timeIn.get(i);
            sumTimeOut += timeOut.get(i);
        }
        return (sumTimeOut - sumTimeIn) / elementCount;
    }

    private double getMeanTimeInQueue(){
        double sumQueueIn = 0;
        double sumQueueOut = 0;
        int elementCount = queueOut.size();
        for(int i = 0; i < elementCount; i++){
            sumQueueIn += queueIn.get(i);
            sumQueueOut += queueOut.get(i);
        }

        return (sumQueueOut - sumQueueIn) / elementCount;
    }
}