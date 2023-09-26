import java.util.List;
import java.util.ArrayList;

public class Model {
    private double tnext;
    private double tcurr;

    private int state;
    private List <Event> events = new ArrayList<>();

    public Model(Event create, List<Event> process) {
        tnext=0.0;
        tcurr = tnext;
        events.add(create);
        state = 0;
        for (Event event : process) {
            events.add(event);
        }
    }

    /**
     * Метод моделювання
     * @param timeModeling - час моделювання в умовних одиницях часу
     */
    public void simulate(double timeModeling){
        while(tcurr<timeModeling) {
            tnext = Double.MAX_VALUE;       // Час наступної події
            Event nextEvent = null;         // Подія, яка станеться найближчою

            for (Event event : events) {
                if (event.tstate < tnext) {
                    tnext = event.tstate;
                    nextEvent = event;
                }
            }
            System.out.println("\nIt's time for event in " +
                    nextEvent.name +
                    ", time = " + tnext);
            for (Event e : events) {
                e.doStatistics(tnext - tcurr);
            }
            tcurr = tnext;      // перехід в момент tnext

            for (Event event : events) {
                if(event.tstate == tcurr) {
                    event.outAct(tcurr);
                }
            }
            printInfo();
        }
        printResult();
    }
    public void printInfo() {
        for (Event e : events) {
            if(e.state == 1)
                e.printInfo();
        }
    }
    public void printResult() {
        System.out.println("\n-------------RESULTS-------------");
        for (Event e : events) {
            e.printResult();
            if (e instanceof EventProcess) {
                EventProcess p = (EventProcess) e;
                System.out.println("mean length of queue = " +
                        p.meanQueue / tcurr
                        + "\nfailure probability = " +
                        p.failure / ((double) p.served + p.failure));
            }
            System.out.println();
        }
    }

}