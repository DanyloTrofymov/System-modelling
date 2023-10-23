import java.util.List;

public class Main {

    public static void main(String[] args) {
        Create create = new Create(0.5, "Create");
        Process process1 = new Process(0.5, "Process1");
        MultiTaskProcessor multiTaskProcessor1 = new MultiTaskProcessor(List.of(process1), "MultiTaskProcessor1");
        create.setNextElement(multiTaskProcessor1);

        Model model = new Model(create, List.of(multiTaskProcessor1));
        model.simulate(1000);
    }
}