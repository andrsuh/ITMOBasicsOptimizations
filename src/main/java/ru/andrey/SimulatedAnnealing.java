package main.java.ru.andrey;

import java.util.Arrays;

import static java.lang.Math.exp;
import static java.lang.Math.random;

public class SimulatedAnnealing {
    private int dimension;
    private double temperature;
    private Oracle oracle;

    public SimulatedAnnealing(int dimension) {
        this.dimension = dimension;
    }

    public static void main(String[] args) {
        double acc = 0.0;
        SimulatedAnnealing s = new SimulatedAnnealing(10);
        for (int i = 0; i < 100; ++i) {
            double opt = s.searchOptimum();
            acc += opt;
            System.out.println("Optimum " + opt);
        }
        System.out.println("Average " + acc / 100.0);
    }

    public Double searchOptimum() {
        oracle = new Oracle(dimension);
        temperature = 1000;

        Solution globalOptimum = null;

        Solution currentSolution = new Solution(
                oracle,
                Arrays.stream(new double[dimension])
                        .map((x) -> random() * 20 - 10)
                        .toArray()
        );

//        Solution pretendentSolution = new Solution(currentSolution);
        while (true) {
//            pretendentSolution.mutate(0.5, true);
            Solution pretendentSolution = new Solution(
                    oracle,
                    Arrays.stream(new double[dimension])
                            .map((x) -> random() * 20 - 10)
                            .toArray()
            );


            if (pretendentSolution.broken()) {
                System.out.println("Attempt is ending");
                break;
            }

            if (pretendentSolution.getQuality() < currentSolution.getQuality() ||
                    random() < exp((currentSolution.getQuality() - pretendentSolution.getQuality()) / temperature)) { // we are looking for minimum
//                System.out.println("CurSolution: " + currentSolution.getSolution() + " CurQuality: " + currentSolution.getQuality());
//                System.out.println("----------------------------------------------------------");
//                currentSolution = new Solution(pretendentSolution);
                currentSolution = pretendentSolution;
            }

//            temperature /= 1.000000000001;

            temperature -= 0.00000001;

            if (globalOptimum == null || currentSolution.getQuality() < globalOptimum.getQuality()) {
                globalOptimum = new Solution(currentSolution);
            }
        }

//        Solution potentialSolution = new Solution(globalOptimum);
//        double step = 0.1;
//        potentialSolution.mutate(step, true);
//        if (potentialSolution.broken()) {
//            return globalOptimum.getQuality();
//        }
//        if (potentialSolution.getQuality() > globalOptimum.getQuality()) {
//            step += -1;
//            potentialSolution = new Solution(globalOptimum);
//        }
//
//        while (true) {
//            potentialSolution.mutate(step, true);
//            if (potentialSolution.getQuality() < globalOptimum.getQuality()) {
//                globalOptimum = new Solution(potentialSolution);
//            } else {
////                System.out.println("Local search: Attempt is ending");
//                break;
//            }
//        }

        return globalOptimum.getQuality();
    }
}
