public class Create extends Element{
    TaskClass currentTaskClass;
    public Create(double delay, String name, TaskClass taskClass) {
        super(delay, name);
        this.currentTaskClass = taskClass;
    }
    @Override
    public void outAct(double tcurr, TaskClass taskClass1) {
        super.outAct(tcurr);
        Model.timeIn.add(tcurr);
        double delay = getDelay();
        totalWorkTime += delay;
        tstate = tcurr + delay;
        next.inAct(tcurr, currentTaskClass);
    }
    @Override
    public void printResult(){
        System.out.println(name+ " created = "+ served);
    }
}