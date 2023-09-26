import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> Created = new ArrayList<>();
        List<Integer> Served1 = new ArrayList<>();
        List<Integer> Served2 = new ArrayList<>();
        List<Integer> Served3 = new ArrayList<>();
        List<Double> MeanLegth1 = new ArrayList<>();
        List<Double> MeanLegth2 = new ArrayList<>();
        List<Double> MeanLegth3 = new ArrayList<>();
        List<Double> FailtureProb1 = new ArrayList<>();
        List<Double> FailtureProb2 = new ArrayList<>();
        List<Double> FailtureProb3 = new ArrayList<>();


        for (int i = 0; i < 100; i++){
            EventCreate eventCreate = new EventCreate(2, "Create");
            EventProcess eventProcess1 = new EventProcess(5, "Process1", 5);
            EventProcess eventProcess2 = new EventProcess(1, "Process2", 5);
            EventProcess eventProcess3 = new EventProcess(1, "Process3", 5);

            eventCreate.setNextElement(eventProcess1);
            eventProcess1.setNextElement(eventProcess2);
            eventProcess2.setNextElement(eventProcess3);

            List<Event> events = List.of(eventProcess1, eventProcess2, eventProcess3);
            Model model = new Model(eventCreate, events);
            model.simulate(1000);

            Created.add(eventCreate.served);
            Served1.add(eventProcess1.served);
            Served2.add(eventProcess2.served);
            Served3.add(eventProcess3.served);
            MeanLegth1.add(eventProcess1.meanQueue);
            MeanLegth2.add(eventProcess2.meanQueue);
            MeanLegth3.add(eventProcess3.meanQueue);
            FailtureProb1.add((double) eventProcess1.failure / (eventCreate.served + eventProcess1.failure));
            FailtureProb2.add((double) eventProcess2.failure / (eventCreate.served + eventProcess1.failure));
            FailtureProb3.add((double) eventProcess3.failure / (eventCreate.served + eventProcess1.failure));
        }

        System.out.println("_______________________AVG RESULT________________________");
        System.out.println("Created: " + Created.stream().mapToInt(Integer::intValue).sum() / Created.size());
        System.out.println("Served1: " + Served1.stream().mapToInt(Integer::intValue).sum() / Served1.size());
        System.out.println("MeanLegth1: " + MeanLegth1.stream().mapToDouble(Double::doubleValue).sum() / MeanLegth1.size());
        System.out.println("FailtureProb1: " + FailtureProb1.stream().mapToDouble(Double::doubleValue).sum() / FailtureProb1.size());
        System.out.println();
        System.out.println("Served2: " + Served2.stream().mapToInt(Integer::intValue).sum() / Served2.size());
        System.out.println("MeanLegth2: " + MeanLegth2.stream().mapToDouble(Double::doubleValue).sum() / MeanLegth2.size());
        System.out.println("FailtureProb2: " + FailtureProb2.stream().mapToDouble(Double::doubleValue).sum() / FailtureProb2.size());\
        System.out.println();
        System.out.println("Served3: " + Served3.stream().mapToInt(Integer::intValue).sum() / Served3.size());
        System.out.println("MeanLegth3: " + MeanLegth3.stream().mapToDouble(Double::doubleValue).sum() / MeanLegth3.size());
        System.out.println("FailtureProb3: " + FailtureProb3.stream().mapToDouble(Double::doubleValue).sum() / FailtureProb3.size());

    }
}
