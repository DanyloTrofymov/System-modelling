import java.util.*;

public class MSS extends Element {
    protected List<Process> processes;
    protected int totalCustomersExited = 0;
    protected double totalExitTime = 0.0;
    protected double lastExitTime = 0.0; // Останній час виходу клієнта
    protected double totalEnterTimeStart = 0.0;
    protected double totalEnterTimeEnd = 0.0;
    protected int maxQueue;

    HashMap<TaskClass, Integer> priority;
    protected List<TaskClass> queue = new ArrayList<>();

    public MSS(String name, HashMap<TaskClass, Integer> priority) {
        super(name);
        this.processes = List.of(new Process("Process 1"), new Process("Process 2"));
        this.priority = priority;
        this.maxQueue = Integer.MAX_VALUE;
        setTState();
    }

    public MSS(String name, List<Process> processes, HashMap<TaskClass, Integer> priority) {
        super(name);
        this.processes = processes;
        this.priority = priority;
        this.maxQueue = Integer.MAX_VALUE;
        setTState();
    }


    @Override
    public void inAct(double tcurr, TaskClass taskClass) {
        this.currentTaskClass = taskClass;
        inAct(tcurr);
    }
    @Override
    public void inAct(double tcurr) {
        super.inAct(tcurr);
        double delay = FunRand.exp(getDelayOnType(currentTaskClass));
        if (currentTaskClass == TaskClass.C) {
            int freeProcessCount = getFreeProcessCount();
            if (freeProcessCount != processes.size()) {
                tryToAddToQueue(tcurr);
            } else {
                for (Process process : processes) {
                    inActProcessing(tcurr, delay, process);
                }
            }
        } else {
            Process process = getFreeProcess();
            if (process != null) {
                inActProcessing(tcurr, delay, process);
            } else {
                tryToAddToQueue(tcurr);
            }
        }
    }
    private void inActProcessing(double tcurr, double delay, Process process) {
        totalEnterTimeStart += tcurr;
        process.currentTaskClass = currentTaskClass;
        Model.queueIn.add(tcurr);
        Model.queueOut.add(tcurr);
        process.outAct(tcurr, delay);
        setTState();
    }

    private void tryToAddToQueue(double tcurr){
        if (this.queue.size() < this.maxQueue) {
            this.queue.add(currentTaskClass);
            Model.queueIn.add(tcurr);
        } else {
            this.failure++;
        }
    }
    @Override
    public void outAct(double tcurr, TaskClass taskClass) {
        outAct(tcurr);
    }

    @Override
    public void outAct(double tcurr) {
        super.outAct(tcurr);
        TaskClass taskClass = null;
        if (!this.queue.isEmpty()) {
            taskClass = getTaskClassByPriority();
        }
        double delay = FunRand.exp(getDelayOnType(taskClass));
        if( taskClass == TaskClass.C){
            int freeProcessCount = getFreeProcessCount();
            if (freeProcessCount != processes.size()) {
                Process process = getThisProcess(tcurr);
                if (process != null) {
                    outActProcessing(tcurr, null, process, delay);
                }
            } else {

                for (Process process : processes) {
                    outActProcessing(tcurr, taskClass, process, delay);
                }
            }
        }
        else {
            Process process = getThisProcess(tcurr);
            if (process != null) {
                outActProcessing(tcurr, taskClass, process, delay);
            }
        }
    }

    private void outActProcessing(double tcurr, TaskClass taskClass, Process process, double delay) {
        if(taskClass != null){
            this.queue.remove(taskClass);
            Model.queueOut.add(tcurr);
        }
        process.setState(0);
        process.setTstate(Double.MAX_VALUE);
        setTState();

        if (taskClass != null) {
            process.currentTaskClass = taskClass;
            process.outAct(tcurr, delay);
            setTState();
        }

        double exitTime = tcurr;
        totalCustomersExited++;
        totalExitTime += exitTime - lastExitTime;
        lastExitTime = exitTime;
        totalEnterTimeEnd += tcurr;
    }
    private Process getFreeProcess() {
        for (Process process : processes) {
            if (process.state == 0) {
                return process;
            }
        }
        return null;
    }


    private int getFreeProcessCount() {
        int count = 0;
        for (Process process : processes) {
            if (process.state == 0) {
                count++;
            }
        }
        return count;
    }

    private Process getThisProcess(double tcurr) {
        for (Process process : processes) {
            if (process.tstate == tcurr) {
                return process;
            }
        }
        return null;
    }

    private void setTState() {
        this.tstate = Collections.min(processes.stream().map(process -> process.tstate).toList());
        if(this.tstate == Double.MAX_VALUE) {
            this.state = 0;
        }
        else {
            this.state = 1;
        }
    }

    @Override
    public void setNextElement(Element element) {
        for (Process process : processes) {
            process.setNextElement(element);
        }
    }

    @Override
    public void calcMeanQueueLength(double delta) {
        meanQueue = meanQueue + queue.size() * delta;
    }

    @Override
    public void printResult() {
        int totalServed = 0;
        int servedA = 0;
        int servedB = 0;
        int servedC = 0;
        for (Process process : processes) {
            process.printResult();
            servedA += process.servedA;
            servedB += process.servedB;
            servedC += process.servedC;
        }

        System.out.println(name+ " served = "+ (servedA + servedB + (servedC/2)));
        System.out.println("\tServed A = " + servedA);
        System.out.println("\tServed B = " + servedB);
        System.out.println("\tServed C = " + servedC/processes.size());
        System.out.println("failure = " + this.failure);
    }

    public double getTotalWorkTime() {
        double totalWorkTime = 0;
        for (Process process : processes) {
            totalWorkTime += process.totalWorkTime;
        }
        return  totalWorkTime;
    }

    public int getProucessCount() {
        return processes.size();
    }

    public List<Process> getProcesses() {
        return processes;
    }

    private double getDelayOnType(TaskClass taskClass) {
        if( taskClass == null) {
            return 0.0;
        }
        switch (taskClass) {
            case A:
                // Завдання типу А –протягом інтервалу часу, що є випадковою величиною, яка
                //розподілена за експоненціальним законом з інтенсивністю 0,25
                //завдання/хвилину. Це все одно, що випадкова величина, що виконання цілого завдання за 4 хвилини.
                return 4;
            case B:
                // Завдання типу В –протягом інтервалу часу, що є випадковою величиною, яка
                // розподілена за експоненціальним законом з інтенсивністю 0,166
                // завдання/хвилину. Це все одно, що випадкова величина, що виконання цілого завдання за 6 хвилин.
                return 6;
            case C:
                // Завдання типу С –протягом інтервалу часу, що є випадковою величиною, яка
                // розподілена за експоненціальним законом з інтенсивністю 0,083
                // завдання/хвилину. Це все одно, що випадкова величина, що виконання цілого завдання за 12 хвилин.
                return 12;
            default:
                return 0.0;
        }
    }

    private TaskClass getTaskClassByPriority() {
        TaskClass taskClass = null;
        int minPriority = Integer.MAX_VALUE;
        for (TaskClass key : queue) {
            if (priority.get(key) < minPriority) {
                minPriority = priority.get(key);
                taskClass = key;
            }
        }
        return taskClass;
    }
}



    /*    @Override
    public void outAct(double tcurr) {
        super.outAct(tcurr);
        Process process = getThisProcess(tcurr);
        if (process != null) {
            process.setState(0);
            process.setTstate(Double.MAX_VALUE);
            setTState();
            if (!this.queue.isEmpty()) {
                TaskClass taskClass = getTaskClassByPriority();
                process.currentTaskClass = taskClass;
                process.outAct(tcurr);
                this.queue.remove(taskClass);
                setTState();
            }
            double exitTime = tcurr; // Час виходу клієнта
            totalCustomersExited++;
            totalExitTime += exitTime - lastExitTime; // Різниця між поточним та попереднім часом виходу
            lastExitTime = exitTime; // Оновлення останнього часу виходу
            totalEnterTimeEnd += tcurr;
        }
    }
    */