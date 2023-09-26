import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EventCreate eventCreate = new EventCreate(2, "Create");
        EventProcess eventProcess1 = new EventProcess(1, "Process1", 5, 2);
        EventProcess eventProcess2 = new EventProcess(1, "Process2", 5, 2);
        EventProcess eventProcess3 = new EventProcess(1, "Process3", 5, 2);

        eventCreate.setNextElement(eventProcess1);
        eventProcess1.setNextElement(eventProcess2);
        eventProcess2.setNextElement(eventProcess3);

        List<Event> events = List.of(eventProcess1, eventProcess2, eventProcess3);
        Model model = new Model(eventCreate, events);
        model.simulate(1000);

    }
}