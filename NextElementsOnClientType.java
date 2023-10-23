import java.util.HashMap;
import java.util.Map;

public class
NextElementsOnClientType {
    Map<ClientType, NextElements> nextElementsOnClientType;

    public NextElementsOnClientType(Map<ClientType, NextElements> nextElementsOnClientType) {
        this.nextElementsOnClientType = nextElementsOnClientType;
    }

    public Element getNextElement(ClientType clientType) {
        return nextElementsOnClientType.get(clientType).getNextElement();
    }
}
