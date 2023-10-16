import java.util.List;
import java.util.ArrayList;

public class Model {
    private double tnext;
    private double tcurr;
    private List <Element> elements = new ArrayList<>();

    public Model(Element create, List<Element> process) {
        tnext=0.0;
        tcurr = tnext;
        elements.add(create);
        for (Element element : process) {
            elements.add(element);
        }
    }

    /**
     * Метод моделювання
     * @param timeModeling - час моделювання в умовних одиницях часу
     */
    public void simulate(double timeModeling){
        while(tcurr<timeModeling) {
            tnext = Double.MAX_VALUE;       // Час наступної події
            Element nextElement = null;         // Подія, яка станеться найближчою

            for (Element element : elements) {
                if (element.tstate < tnext) {
                    tnext = element.tstate;
                    nextElement = element;
                }
            }
            System.out.println("\nIt's time for element in " +
                    nextElement.name +
                    ", time = " + tnext);
            for (Element e : elements) {
                e.doStatistics(tnext - tcurr);
            }
            tcurr = tnext;

            for (Element element : elements) {
                if(element.tstate == tcurr) {
                    element.outAct(tcurr);
                }
            }
            printInfo();
        }
        printResult(timeModeling);
    }
    public void printInfo() {
        for (Element e : elements) {
            if(e.state == 1)
                e.printInfo();
        }
    }
    public void printResult( double timeModeling) {
        System.out.println("\n-------------RESULTS-------------");
        for (Element e : elements) {
            e.printResult();
            if (e instanceof MultiProcess) {
                MultiProcess p = (MultiProcess) e;
                System.out.println("mean length of queue = " +
                        p.meanQueue / tcurr
                        + "\nfailure probability = " +
                        p.failure / ((double) p.served + p.failure) +
                        "\nload time = " + p.totalWorkTime / timeModeling);
            }
            System.out.println();
        }
    }
}