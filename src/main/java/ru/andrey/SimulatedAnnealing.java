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
        temperature = 100;

        Solution globalOptimum = null;

        Solution currentSolution = new Solution(
                oracle,
                Arrays.stream(new double[dimension])
                        .map((x) -> random() * 20 - 10)
                        .toArray()
        );

        while (temperature > 0.00000001) {
            Solution pretendentSolution = new Solution(
                    oracle,
                    Arrays.stream(new double[dimension])
                            .map((x) -> random() * 20 - 10)
                            .toArray()
            );

            if (pretendentSolution.isBroken()) {
                break;
            }

            if (pretendentSolution.getQuality() < currentSolution.getQuality() ||
                    random() < exp((currentSolution.getQuality() - pretendentSolution.getQuality()) / temperature)) { // we are looking for minimum
//                System.out.println("CurSolution: " + currentSolution.getSolution() + " CurQuality: " + currentSolution.getQuality());
//                System.out.println("----------------------------------------------------------");
                currentSolution = new Solution(pretendentSolution);
            }

//            temperature /= 1.004;

            temperature -= 0.001;

            if (globalOptimum == null || currentSolution.getQuality() < globalOptimum.getQuality()) {
                globalOptimum = new Solution(currentSolution);
            }
        }

        return globalOptimum.getQuality();
    }
}
