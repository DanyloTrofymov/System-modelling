import java.util.List;
import java.util.ArrayList;

public class Model {
    private double tnext;
    private double tcurr;
    private List <Event> events = new ArrayList<>();

    public Model(Event create, List<Event> process) {
        tnext=0.0;
        tcurr = tnext;
        events.add(create);
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
            tcurr = tnext;

            for (Event event : events) {
                if(event.tstate == tcurr) {
                    event.outAct(tcurr, events);
                }
            }
            printInfo();
        }
        printResult(timeModeling);
    }
    public void printInfo() {
        for (Event e : events) {
            if(e.state == 1)
                e.printInfo();
        }
    }
    public void printResult( double timeModeling) {
        System.out.println("\n-------------RESULTS-------------");
        for (Event e : events) {
            e.printResult();
            if (e instanceof EventProcess) {
                EventProcess p = (EventProcess) e;
                System.out.println("mean length of queue = " +
                        p.meanQueue / tcurr
                        + "\nfailure probability = " +
                        p.failure / ((double) p.served + p.failure) +
                        "\nload time = " + p.totalWorkTime / timeModeling);
            }
            System.out.println();
        }
    }
}