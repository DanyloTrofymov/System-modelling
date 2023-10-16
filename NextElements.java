import java.util.*;
public class NextElements {
    private List<NextElement> nextElements;
    private NextElementsType type;

    public NextElements(List<NextElement> nextElements, NextElementsType type) {
        if(type == NextElementsType.PROBABILITY) {
            double sum = 0;
            for (NextElement nextElement : nextElements) {
                sum += nextElement.probability;
            }
            if (sum != 1) {
                throw new IllegalArgumentException("Sum of probabilities must be 1");
            }
        }
        this.nextElements = nextElements;
        this.type = type;
    }

    public Element getNextElement() {
        switch (type) {
            case PRIORITY:
                return getPriorityElement();
            case PROBABILITY:
                return getProbabilityElement();
            default:
                throw new IllegalArgumentException("Unknown type");
        }
    }

    private Element getPriorityElement() {
        Element element = null;
        int maxPriority = Integer.MIN_VALUE;
        for (NextElement nextElement : nextElements) {
            if (nextElement.priority > maxPriority) {
                maxPriority = nextElement.priority;
                element = nextElement.element;
            }
        }
        return element;
    }

    private Element getProbabilityElement() {
        double random = Math.random();
        double sum = 0;
        for (NextElement nextElement : nextElements) {
            sum += nextElement.probability;
            if (random < sum) {
                return nextElement.element;
            }
        }
        return null;
    }
}
