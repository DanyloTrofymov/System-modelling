public class Model {
    private double tnext;
    private double tcurr;
    private double t0, t1, t2, t3;
    private double delayCreate, delayProcess;
    private double totalProcessingTime1, totalProcessingTime2, totalProcessingTime3= 0.0;
    private int numCreate, failure;
    private int numProcess1, numProcess2, numProcess3;
    private int state, maxQueue, queueProcess1, queueProcess2, queueProcess3;
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
            nextEvent = 0;      // тип найближчої події (0 - надходження, 1-3 - обслуговування)

            // Порівнюємо моменти обслуговування для всіх трьох процесів і вибираємо найменший
            if (t1 <= tnext && t1 <= t2 && t1 <= t3) {
                tnext = t1;
                nextEvent = 1;
            } else if (t2 <= tnext && t2 <= t1 && t2 <= t3) {
                tnext = t2;
                nextEvent = 2;
            } else if (t3 <= tnext && t3 <= t1 && t3 <= t2) {
                tnext = t3;
                nextEvent = 3;
            }

            tcurr = tnext;      // перехід в момент tnext

            switch(nextEvent){  // зміна стану системи в залежності від типу події
                case 0: eventCreate();
                    break;
                case 1: eventProcess1();
                    break;
                case 2: eventProcess2();
                    break;
                case 3: eventProcess3();
                    break;
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
            t1 = tcurr+ getDelayProcess1(); // момент виходу пристрою зі стану 1
        } else {
            if(queueProcess1 <maxQueue) queueProcess1++;
            else failure++;
        }
    }

    /**
     * Метод обробки події "закінчення обслуговування 1"
     */
    public void eventProcess1() {
        t1 = Double.MAX_VALUE;  // момент виходу пристрою зі стану 1(не очікується вихід з пристрою)
        state = 0;              // Пристрій звільнився
        if (queueProcess1 > 0) {
            queueProcess1--;
            state = 1;
            double processingTime = getDelayProcess1();
            t1 = tcurr + processingTime;
            totalProcessingTime1 += processingTime;
        }
        numProcess1++;
    }

    public void eventProcess2() {
        t2 = Double.MAX_VALUE;  // момент виходу пристрою зі стану 2 (не очікується вихід з пристрою)
        state = 0;              // Процес 2 завершився
        // Перевіряємо, чи є черга для Process2 та запускаємо її, якщо так
        if (queueProcess2 > 0) {
            queueProcess2--;
            state = 2;  // Змінюємо стан на 2 для Process2
            double processingTime = getDelayProcess2();
            t2 = tcurr + processingTime;
            totalProcessingTime2 += processingTime;
        }
        numProcess2++;
    }

    /**
     * Метод обробки події "закінчення обслуговування 3"
     */
    public void eventProcess3() {
        t3 = Double.MAX_VALUE;  // момент виходу пристрою зі стану 3 (не очікується вихід з пристрою)
        state = 0;              // Процес 3 завершився
        // Перевіряємо, чи є черга для Process3 та запускаємо її, якщо так
        if (queueProcess3 > 0) {
            queueProcess3--;
            state = 3;  // Змінюємо стан на 3 для Process3
            double processingTime = getDelayProcess3();
            t3 = tcurr + processingTime;
            totalProcessingTime3 += processingTime;
        }
        numProcess3++;
    }

    public void printStatistic(){
        double avgProcessingTime1 = totalProcessingTime1 / numProcess1;
        double avgProcessingTime2 = totalProcessingTime2 / numProcess2;
        double avgProcessingTime3 = totalProcessingTime3 / numProcess3;
        System.out.println("numCreate = " + numCreate + " failure = "+failure);
        System.out.println("numProcess1 = "+ numProcess1 +" avgProcessingTime1 = " + avgProcessingTime1);
        System.out.println("numProcess2 = "+ numProcess2 +" avgProcessingTime2 = " + avgProcessingTime2);
        System.out.println("numProcess3 = "+ numProcess3 +" avgProcessingTime3 = " + avgProcessingTime3);
    }
    public void printInfo(){
        System.out.println(" t = " + tcurr+" state = "+state+" queue = "+ queueProcess1);
    }
    private double getDelayCreate() {
        return FunRand.exp(delayCreate);
    }
    private double getDelayProcess1() {
        return FunRand.exp(delayProcess);
    }
    private double getDelayProcess2() {
        return FunRand.exp(delayProcess);
    }
    private double getDelayProcess3() {
        return FunRand.exp(delayProcess);
    }

}