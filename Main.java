import java.util.List;

public class Main {

    public static void main(String[] args) {
        Create create = new Create(0.5, "Create");
        create.setDistributionType(DistributionType.EXPONENTIAL);
        Process process1_1 = new Process(0.3, "Cashier1");
        process1_1.setDistributionType(DistributionType.EXPONENTIAL);
        Process process1_2 = new Process(0.3, "Cashier2");
        process1_2.setDistributionType(DistributionType.EXPONENTIAL);

        MultiTaskProcessor multiTaskProcessor1 = new MultiTaskProcessor(List.of(process1_1), "MultiProcess1", 3);
        MultiTaskProcessor multiTaskProcessor2 = new MultiTaskProcessor(List.of(process1_2), "MultiProcess2", 3);

        NextElement nextElement1 = new NextElement(multiTaskProcessor1, 1);
        NextElement nextElement2 = new NextElement(multiTaskProcessor2, 2);

        NextElements nextElements = new NextElements(List.of(nextElement1, nextElement2), NextElementsType.PRIORITY_WITH_QUEUE);

        create.setNextElement(nextElements);
        multiTaskProcessor1.setNextElement(nextElements);
        multiTaskProcessor2.setNextElement(nextElements);

        List<MultiTaskProcessor> multiTaskProcessors = List.of(multiTaskProcessor1, multiTaskProcessor2);
        Model model = new Model(create, multiTaskProcessors);
        model.simulate(1000);
    }
}