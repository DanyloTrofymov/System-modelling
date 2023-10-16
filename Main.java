import java.util.List;

public class Main {

    public static void main(String[] args) {
        Create create = new Create(2, "Create");
        Process process1_1 = new Process(3, "Process1_1", 5);
        Process process1_2 = new Process(3, "Process1_2", 5);
        Process process1_3 = new Process(1, "Process1_3", 5);
        Process process2_1 = new Process(3, "Process2_1", 5);
        Process process2_2 = new Process(3, "Process2_2", 5);
        Process process2_3 = new Process(1, "Process2_3", 5);
        Process process3_1 = new Process(3, "Process3_1", 5);
        Process process3_2 = new Process(3, "Process3_2", 5);
        Process process3_3 = new Process(1, "Process3_3", 5);
        MultiProcess multiProcess1 = new MultiProcess(List.of(process1_1, process1_2, process1_3), "MultiProcess1", 5, true);
        MultiProcess multiProcess2 = new MultiProcess(List.of(process2_1, process2_2, process2_3), "MultiProcess2", 5, true);
        MultiProcess multiProcess3 = new MultiProcess(List.of(process3_1, process3_2, process3_3), "MultiProcess3", 5, true);

        create.setNextElement(multiProcess1);
        multiProcess1.setNextElement(multiProcess2);
        multiProcess2.setNextElement(multiProcess3);

        List<Element> events = List.of(multiProcess1, multiProcess2, multiProcess3);
        Model model = new Model(create, events);
        model.simulate(1000);
    }
    /*
    public static void main_old(String[] args) {
        Create create = new Create(2, "Create");
        Process_old process1 = new Process_old(1, "Process1", 5, 3);
        Process_old process2 = new Process_old(1, "Process2", 5, 3);
        Process_old process3 = new Process_old(1, "Process3", 5, 3);

        create.setNextElement(process1);
        process1.setNextElement(process2);
        process2.setNextElement(process3);

        List<Element> events = List.of(process1, process2, process3);
        Model model = new Model(create, events);
        model.simulate(1000);

    }*/
}