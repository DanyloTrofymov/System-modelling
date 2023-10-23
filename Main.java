import java.util.*;

public class Main {

    public static void main(String[] args) {
        //they are comming to hospital
        Create firstType = new Create(15, "First type patient", ClientType.FIRST);
        Create secondType = new Create(15, "Second type patient", ClientType.SECOND);
        Create thirdType = new Create(15, "Third type patient", ClientType.THIRD);

        NextElement firstTypeNextElement = new NextElement(firstType, 0.5);
        NextElement secondTypeNextElement = new NextElement(secondType, 0.1);
        NextElement thirdTypeNextElement = new NextElement(thirdType, 0.4);

        //delay zero because sets later
        Process firstDoctor = new Process(0, "First Doctor");
        Process secondDoctor = new Process(0, "Second Doctor");
        NextElements comeToHospital  = new NextElements(List.of(firstTypeNextElement, secondTypeNextElement, thirdTypeNextElement), NextElementsType.PROBABILITY);

        // now they are comming to doctor
        MultiTaskProcessor multiDoctorProcessor = new MultiTaskProcessor(List.of(firstDoctor, secondDoctor), "multiDoctorProcessor", Integer.MAX_VALUE);
        multiDoctorProcessor.isDoctor = true;

        Map map = new HashMap<ClientType, NextElements>();
        map.put(ClientType.FIRST, multiDoctorProcessor);
        map.put(ClientType.SECOND, multiDoctorProcessor);
        map.put(ClientType.THIRD, multiDoctorProcessor);

        NextElementsOnClientType afterComming = new NextElementsOnClientType(map);

        firstType.setNextElement(afterComming);
        secondType.setNextElement(afterComming);
        thirdType.setNextElement(afterComming);

        Process firstAccompanying = new Process(0, "First Accompanying");
        Process secondAccompanying = new Process(0, "Second Accompanying");
        Process thirdAccompanying = new Process(0, "Third Accompanying");
        firstAccompanying.setUniformDistribution(3, 8);
        secondAccompanying.setUniformDistribution(3, 8);
        thirdAccompanying.setUniformDistribution(3, 8);

        MultiTaskProcessor multiAccompanyingProcessor = new MultiTaskProcessor(List.of(firstAccompanying, secondAccompanying, thirdAccompanying), "multiAccompanyingProcessor", Integer.MAX_VALUE);


        Process goToLab = new Process(0, "Go to laboratory");
        goToLab.setUniformDistribution(2, 5);
        MultiTaskProcessor multiGoToLabProcessor = new MultiTaskProcessor(List.of(goToLab), "multiGoToLabProcessor", Integer.MAX_VALUE);

        Map map2 = new HashMap<ClientType, NextElements>();
        map2.put(ClientType.FIRST, multiAccompanyingProcessor);
        map2.put(ClientType.SECOND, multiGoToLabProcessor);
        map2.put(ClientType.THIRD, multiGoToLabProcessor);

        NextElementsOnClientType afterDoctor = new NextElementsOnClientType(map2);

        firstDoctor.setNextElement(afterDoctor);
        secondDoctor.setNextElement(afterDoctor);

        Process registrationToLab = new Process(0, "registrationToLaboratory");
        registrationToLab.setErlangDistribution(4.5, 3);
        MultiTaskProcessor multiregistrationToLabProcessor = new MultiTaskProcessor(List.of(registrationToLab), "multiregistrationToLabProcessor", Integer.MAX_VALUE);


        Map map2_2 = new HashMap<ClientType, NextElements>();
        map2_2.put(ClientType.FIRST, null);
        map2_2.put(ClientType.SECOND, multiregistrationToLabProcessor);
        map2_2.put(ClientType.THIRD, multiregistrationToLabProcessor);


        NextElementsOnClientType afterComeToLab = new NextElementsOnClientType(map2_2);

        goToLab.setNextElement(afterComeToLab);

        Process firstLaborant = new Process(0, "First Laborant");
        Process secondLaborant = new Process(0, "Second Laborant");
        firstLaborant.setErlangDistribution(4, 2);
        secondLaborant.setErlangDistribution(4, 2);

        MultiTaskProcessor multiLaborantProcessor = new MultiTaskProcessor(List.of(firstLaborant, secondLaborant), "multiLaborantProcessor", Integer.MAX_VALUE);

        Map map3 = new HashMap<ClientType, NextElements>();
        map3.put(ClientType.FIRST, null);
        map3.put(ClientType.SECOND, multiLaborantProcessor);
        map3.put(ClientType.THIRD, multiLaborantProcessor);

        NextElementsOnClientType afterLab = new NextElementsOnClientType(map3);

        registrationToLab.setNextElement(afterLab);

        Process goToDoctor = new Process(0, "Go to doctor");
        goToDoctor.setUniformDistribution(2, 5);

        MultiTaskProcessor multiGoToDoctorProcessor = new MultiTaskProcessor(List.of(goToDoctor), "multiGoToDoctorProcessor", Integer.MAX_VALUE);

        Map map3_2 = new HashMap<ClientType, NextElements>();
        map3_2.put(ClientType.FIRST, null);
        map3_2.put(ClientType.SECOND, multiGoToDoctorProcessor);
        map3_2.put(ClientType.THIRD, multiGoToDoctorProcessor);

        NextElementsOnClientType afterLab2 = new NextElementsOnClientType(map3_2);
        goToDoctor.setNextElement(afterLab2);


        Model model = new Model(comeToHospital, List.of(multiDoctorProcessor));
        model.simulate(1000);
    }
}