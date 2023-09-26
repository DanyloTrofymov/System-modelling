import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

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
            Event nextEvent = events.get(0);      // Найближча подія

            for (Event event : events) {
                if (event.tstate < tnext) {
                    tnext = event.tstate;
                    nextEvent = event;
                }
            }

            tcurr = tnext;      // перехід в момент tnext

            for (int i = 0; i < events.size(); i++) {
                if (Objects.equals(events.get(i).name, nextEvent.name)) {
                    Event next = null;
                    Event prev = null;
                    if (i < events.size() - 1) {
                        next = events.get(i + 1);
                    }
                    if (i > 0) {
                        prev = events.get(i - 1);
                    }
                    state = events.get(i).serve(tcurr, state, prev, next);
                    break;
                }
            }
            if (state == 1) {
                nextEvent.printInfo();
            }
        }
        printStatistic(timeModeling);
    }

    public void printStatistic(double timeModeling){
        for (Event event : events) {
            event.printStatistic(timeModeling);
        }
    }

}