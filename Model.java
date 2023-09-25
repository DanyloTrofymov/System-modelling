import java.io.FileWriter;
import java.io.IOException;

public class Model {
    private double tnext;
    private double tcurr;
    private double t0, t1;
    private double delayCreate, delayProcess;
    private double totalProcessingTime = 0.0;
    private int numCreate, numProcess, failure;
    private int state, maxQueue, queue;
    private int nextEvent;

    public Model(double delayCr, double delayPr) {
        delayCreate = delayCr;
        delayProcess = delayPr;
        tnext=0.0;
        nextEvent = 0;
        tcurr = tnext;
        t0=tcurr; t1=Double.MAX_VALUE;
        maxQueue = 0;
    }
    /**
      *  @param delayCr середній час надходження заявки
      *  @param delayPr середній час обслуговування заявки
      *  @param maxQ максимальна довжина черги
     */
    public Model(double delayCr, double delayPr, int maxQ) {
        delayCreate = delayCr;
        delayProcess = delayPr;
        tnext=0.0;              // момент найближчої події
        nextEvent = 0;          // тип найближчої події (0 - надходження, 1 - обслуговування)
        tcurr = tnext;          // поточний момент часу
        t0=tcurr;               // момент часу надходження поточної заявки
        t1=Double.MAX_VALUE;    // момент часу обслуговування поточної заявки
        maxQueue = maxQ;
    }

    /**
     * Метод моделювання
     * @param timeModeling - час моделювання в умовних одиницях часу
     */
    public void simulate(double timeModeling){
        while(tcurr<timeModeling){
            tnext = t0;         // знаходження найближчої події
            nextEvent = 0;      // тип найближчої події (0 - надходження, 1 - обслуговування)

            if(t1<=tnext){      //вирішення конфлікту подій
                tnext = t1;
                nextEvent = 1;
            }

            tcurr = tnext;      // перехід в момент tnext

            switch(nextEvent){  // зміна стану системи в залежності від типу події
                case 0: eventCreate();
                    break;
                case 1: eventProcess1();
            }
            printInfo();
        }
        printStatistic();
    }

    /**
     * Метод обробки події "надходження заявки"
     */
    public void eventCreate(){
        t0 = tcurr+getDelayCreate();
        numCreate++;
        if(state==0){   // стан присторою
            state = 1;
            t1 = tcurr+getDelayProcess(); // момент виходу пристрою зі стану 1
        } else {
            if(queue<maxQueue) queue++;
            else failure++;
        }
    }

    /**
     * Метод обробки події "закінчення обслуговування 1"
     */
    public void eventProcess1() {
        t1 = Double.MAX_VALUE;  // момент виходу пристрою зі стану 1(не очікується вихід з пристрою)
        state = 0;              // Пристрій звільнився
        if (queue > 0) {
            queue--;
            state = 1;
            double processingTime = getDelayProcess();
            t1 = tcurr + processingTime;
            totalProcessingTime += processingTime;
        }
        numProcess++;
    }

    public void printStatistic(){
        double avgProcessingTime = totalProcessingTime / numProcess;
        System.out.println("numCreate = " + numCreate+
                " numProcess = "+numProcess+" failure = "+failure+ " avgProcessingTime = " + avgProcessingTime);
    }
    public void printInfo(){
        System.out.println(" t = " + tcurr+" state = "+state+" queue = "+queue);
    }
    private double getDelayCreate() {
        return FunRand.exp(delayCreate);
    }
    private double getDelayProcess() {
        return FunRand.exp(delayProcess);
    }

}