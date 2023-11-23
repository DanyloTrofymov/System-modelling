import java.util.*;

public class Main {

    public static void main(String[] args) {
        HashMap<TaskClass, Integer> priority = new HashMap<>(){{
            put(TaskClass.A, 1);
            put(TaskClass.B, 2);
            put(TaskClass.C, 3);
        }};


        // А надходить 0.2 завдаання/сек. Це все одно що 1 завдання кожні 5 секунд
        Create taskA = new Create(5, "A", TaskClass.A);
        // B надходить 0.066 завдання/сек. Це все одно що 1 завдання кожні 15 секунд
        Create taskB = new Create(15, "B", TaskClass.B);
        // С надходить одне за 15 секунд
        Create taskC = new Create(15, "C", TaskClass.C);

        Process process1 = new Process("Process 1");
        Process process2 = new Process("Process 2");

        MSS mss = new MSS("MSS", List.of(process1, process2), priority);

        taskA.setNextElement(mss);
        taskB.setNextElement(mss);
        taskC.setNextElement(mss);

        Model model = new Model(List.of(taskA, taskB, taskC), List.of(mss));
        model.simulate(10000);
    }

    public ArrayList<HashMap<TaskClass, Integer>> getAllNecessaryCombinations(){
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