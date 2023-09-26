import java.util.List;

public class Main {
    public static void main(String[] args) {
        EventCreate eventCreate = new EventCreate(2, "Create");
        EventProcess eventProcess1 = new EventProcess(1, "Process1", 1);
        EventProcess eventProcess2 = new EventProcess(1, "Process2", 1);
        EventProcess eventProcess3 = new EventProcess(1, "Process3", 1);
        List<Event> events = List.of(eventProcess1, eventProcess2, eventProcess3);
        Model model = new Model(eventCreate, events);
        model.simulate(1000);
    }
}
