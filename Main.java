import java.util.*;

public class Main {

    public static void main(String[] args) {
        ArrayList<HashMap<TaskClass, Integer>> priorities = getAllNecessaryCombinations();
        int countOfTests = 50;
        double timeModeling = 10000;
        System.out.println("Time modeling = " + timeModeling);
        for (HashMap<TaskClass, Integer> priority : priorities) {
            HashMap<StatisticDataTypes, Double> result = initializeResult();
            for(int i = 0; i < countOfTests; i++) {
                // А надходить 0.2 завдаання/сек. Це все одно що 1 завдання кожні 5 секунд
                Create taskA = new Create(5, "A", TaskClass.A);
                // B надходить 0.066 завдання/сек. Це все одно що 1 завдання кожні 15 секунд
                Create taskB = new Create(15, "B", TaskClass.B);
                // С надходить одне за 15 секунд
                Create taskC = new Create(15, "C", TaskClass.C);

                MSS mss = new MSS("MSS", priority);

                taskA.setNextElement(mss);
                taskB.setNextElement(mss);
                taskC.setNextElement(mss);

                Model model = new Model(List.of(taskA, taskB, taskC), List.of(mss));
                model.simulate(timeModeling);
                HashMap<StatisticDataTypes, Double> localResult = model.getStatistics();
                result = addLocalResultToResult(result, localResult);
            }
            printResult(priority, result, countOfTests);
        }
    }

    public static void printResult(HashMap<TaskClass, Integer> priority, HashMap<StatisticDataTypes, Double> result, int countOfTests){
        double meanQueueLength = result.get(StatisticDataTypes.meanQueueLength)/countOfTests;
        double meanTimeInSystem = result.get(StatisticDataTypes.meanTimeInSystem)/countOfTests;
        double meanTimeInQueue = result.get(StatisticDataTypes.meanTimeInQueue) / countOfTests;

        double servedA = result.get(StatisticDataTypes.servedA) / countOfTests;
        double servedB = result.get(StatisticDataTypes.servedB) / countOfTests;
        double servedC = result.get(StatisticDataTypes.servedC) / countOfTests;

        System.out.println("Results for discipline:");

        System.out.println("\t A : " + replacePrirityNumsWithWords(priority.get(TaskClass.A)) + "; served: " + servedA);
        System.out.println("\t B : " + replacePrirityNumsWithWords(priority.get(TaskClass.B))+ "; served: " + servedB);
        System.out.println("\t C : " + replacePrirityNumsWithWords(priority.get(TaskClass.C))+ "; served: " + servedC);

        System.out.println("Mean length of queue = " + meanQueueLength);
        System.out.println("Mean time in system = " + meanTimeInSystem);
        System.out.println("Mean time in queue = " + meanTimeInQueue);
        System.out.println();
        System.out.println();
    }

    public static String replacePrirityNumsWithWords(Integer priority){
        switch (priority){
            case 1:
                return  "High";
            case 2:
                return "Meduim";
            case 3:
                return  "Low";
            default:
                return "Unknown";
        }
    }

    public static HashMap<StatisticDataTypes, Double> initializeResult() {
        HashMap<StatisticDataTypes, Double> result = new HashMap<>();
        for (StatisticDataTypes type : StatisticDataTypes.values()) {
            result.put(type, 0.0);
        }
        return result;
    }

    public static HashMap<StatisticDataTypes, Double> addLocalResultToResult(HashMap<StatisticDataTypes, Double> result, HashMap<StatisticDataTypes, Double> localResult){
        for (StatisticDataTypes type : localResult.keySet()) {
            double currentValue = result.get(type);
            double localValue = localResult.get(type);
            result.put(type, currentValue + localValue);
        }
        return result;
    }
    public static ArrayList<HashMap<TaskClass, Integer>> getAllNecessaryCombinations(){
        ArrayList<HashMap<TaskClass, Integer>> combinations = new ArrayList<>();
        //А – вищий, В – середній, С – низький;
        combinations.add(new HashMap<>(){{
            put(TaskClass.A, 1);
            put(TaskClass.B, 2);
            put(TaskClass.C, 3);
        }});
        //В – вищий, А – середній, С – низький;
        combinations.add(new HashMap<>(){{
            put(TaskClass.A, 2);
            put(TaskClass.B, 1);
            put(TaskClass.C, 3);
        }});
        // С – вищий, А – середній, В – низький;
        combinations.add(new HashMap<>(){{
            put(TaskClass.A, 2);
            put(TaskClass.B, 3);
            put(TaskClass.C, 1);
        }});
        // С – вищий, А – середній, В – низький
        combinations.add(new HashMap<>(){{
            put(TaskClass.A, 2);
            put(TaskClass.B, 3);
            put(TaskClass.C, 1);
        }});
        // С – вищий, А і В – низький;
        combinations.add(new HashMap<>(){{
            put(TaskClass.A, 3);
            put(TaskClass.B, 3);
            put(TaskClass.C, 1);
        }});


        return combinations;
    }
}