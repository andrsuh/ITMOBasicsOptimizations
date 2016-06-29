package ru.andrey;

import static java.lang.Math.*;

public class SimulatedAnnealing extends OptimizationMethod {
    private long temperature;

    public SimulatedAnnealing(int dimension) {
        super(dimension);
    }

    public static void main(String[] args) {
        double acc = 0.0;
        SimulatedAnnealing s = new SimulatedAnnealing(10);
        for (int i = 0; i < 10; ++i) {
            Solution opt = s.searchOptimum();
            acc += opt.getQuality();
            System.out.println("Optimum " + opt.getQuality());
        }
        System.out.println("Average " + acc / 10.0);
    }

    @Override
    public Solution searchOptimum() {
        Oracle oracle = new Oracle(dimension);
        temperature = ((int) 1E4 * (int) pow(dimension, 2)) - (100 * dimension);

        Solution currentSolution = new Solution(oracle, randomGeneratedSolution());
        Solution globalOptimum = new Solution(currentSolution);

        while (temperature-- > 0) {
            Solution pretendentSolution = new Solution(oracle, randomGeneratedSolution());
            if (pretendentSolution.broken()) {
                break;
            }

            if (pretendentSolution.getQuality() < currentSolution.getQuality() ||
                    random() < exp((currentSolution.getQuality() - pretendentSolution.getQuality()) / (1000 * temperature))) { // we are looking for minimum
                currentSolution = pretendentSolution;
            }

            if (globalOptimum == null || currentSolution.getQuality() < globalOptimum.getQuality()) {
                globalOptimum = new Solution(currentSolution);
            }
        }

        while (true) {
            Solution temp = new Solution(globalOptimum);
            temp.mutate(0.1);
            if (temp.broken()) {
                break;
            }

            if (temp.getQuality() < globalOptimum.getQuality()) {
                globalOptimum = new Solution(temp);
            }
        }

        return globalOptimum;
    }
}
