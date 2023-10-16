import java.util.List;

public class Main {

    public static void main(String[] args) {
        Create create = new Create(2, "Create");
        Process process1_1 = new Process(3, "Process1_1");
        Process process1_2 = new Process(3, "Process1_2");
        Process process1_3 = new Process(3, "Process1_3");
        Process process2_1 = new Process(3, "Process2_1");
        Process process2_2 = new Process(3, "Process2_2");
        Process process2_3 = new Process(3, "Process2_3");
        Process process3_1 = new Process(3, "Process3_1");
        Process process3_2 = new Process(3, "Process3_2");
        Process process3_3 = new Process(3, "Process3_3");
        MultiTaskProcessor multiTaskProcessor1 = new MultiTaskProcessor(List.of(process1_1, process1_2, process1_3), "MultiProcess1", 5);
        MultiTaskProcessor multiTaskProcessor2 = new MultiTaskProcessor(List.of(process2_1, process2_2, process2_3), "MultiProcess2", 5);
        MultiTaskProcessor multiTaskProcessor3 = new MultiTaskProcessor(List.of(process3_1, process3_2, process3_3), "MultiProcess3", 5);

        NextElement nextElement = new NextElement(multiTaskProcessor1, 0.5, 3);
        NextElement nextElement1 = new NextElement(multiTaskProcessor2, 0.2, 1);
        NextElement nextElement2 = new NextElement(multiTaskProcessor3, 0.3, 2);

        NextElements nextElements = new NextElements(List.of(nextElement, nextElement1, nextElement2), NextElementsType.PROBABILITY);

        create.setNextElement(nextElements);
        multiTaskProcessor1.setNextElement(nextElements);
        multiTaskProcessor2.setNextElement(nextElements);

        List<MultiTaskProcessor> multiTaskProcessors = List.of(multiTaskProcessor1, multiTaskProcessor2, multiTaskProcessor3);
        Model model = new Model(create, multiTaskProcessors);
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