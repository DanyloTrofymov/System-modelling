import java.util.*;

public class Main {

    public static void main(String[] args) {
        Map<Integer, Double> results = new HashMap<>();
        int k = 5;

        for (int N = 10; N <= 300; N += 10) {
            double[] Nresults = new double[k];

            for (int i = 0; i < k; i++) {
                List<Model> model = getNModels(N);

                long startTime = System.currentTimeMillis();
                for (Model m : model)
                    m.simulate(10000);

                long endTime = System.currentTimeMillis();
                Nresults[i] = endTime - startTime;
            }

            double averageTime = calculateAverage(Nresults);
            results.put(N, averageTime);
        }

        printResults(results);
    }

    public static List<Model> getNModels (int N){
        List<Model> models = new ArrayList<>();
        for (int i = 0; i < N; i++){
            models.add(createOneChainModel());
        }
        return models;
    }
    public static Model createTwoChainModel() {
        Create create = new Create(1, "Create");
        create.setDistributionType(DistributionType.EXPONENTIAL);
        List<NextElement> nextElementList = new ArrayList<>();
        List<MultiTaskProcessor> multiTaskProcessors1 = new ArrayList<>();
        List<MultiTaskProcessor> multiTaskProcessors2 = new ArrayList<>();
        Process Process1 = new Process(1, "Process1");
        Process1.setDistributionType(DistributionType.EXPONENTIAL);

        MultiTaskProcessor process = new MultiTaskProcessor(List.of(Process1), "MultiTaskProcessor1", Integer.MAX_VALUE);

        NextElement hostesNext1 = new NextElement(process, 1);
        nextElementList.add(hostesNext1);

        multiTaskProcessors1.add(process);
        create.setNextElement(new NextElements(nextElementList, NextElementsType.PRIORITY));


        Process Process2 = new Process(1, "Process2");
        Process2.setDistributionType(DistributionType.EXPONENTIAL);

        MultiTaskProcessor process2 = new MultiTaskProcessor(List.of(Process2), "MultiTaskProcessor2", Integer.MAX_VALUE);

        NextElement hostesNext2 = new NextElement(process2, 1);
        nextElementList.add(hostesNext2);

        multiTaskProcessors2.add(process2);

        process.setNextElement(new NextElements(List.of(new NextElement(process, 1)), NextElementsType.PRIORITY));

        multiTaskProcessors1.addAll(multiTaskProcessors2);
        return new Model(create, multiTaskProcessors1);
    }

    public static Model createOneChainModel() {
        Create create = new Create(1, "Create");
        create.setDistributionType(DistributionType.EXPONENTIAL);
        List<NextElement> nextElementList = new ArrayList<>();
        List<MultiTaskProcessor> multiTaskProcessors = new ArrayList<>();

        Process Process1 = new Process(1, "Process1");
        Process1.setDistributionType(DistributionType.EXPONENTIAL);

        MultiTaskProcessor process = new MultiTaskProcessor(List.of(Process1), "MultiTaskProcessor", Integer.MAX_VALUE);

        NextElement hostesNext1 = new NextElement(process, 1);
        nextElementList.add(hostesNext1);

        multiTaskProcessors.add(process);
        create.setNextElement(new NextElements(nextElementList, NextElementsType.PRIORITY));
        return new Model(create, multiTaskProcessors);
    }

    private static double calculateAverage(double[] array) {
        double sum = 0;
        for (double value : array) {
            sum += value;
        }
        return sum / array.length;
    }

    private static void printResults(Map<Integer, Double> results) {
        Map<Integer, Double> sortedResults = new TreeMap<>(results);
        System.out.println("Results:");
        System.out.println("N\tAverage Time (ms)");

        System.out.println("N" + ";" + "time" + ";");
        for (Map.Entry<Integer, Double> entry : sortedResults.entrySet()) {
            System.out.println(entry.getKey() + ";" + entry.getValue() + ";");
        }

    }

}