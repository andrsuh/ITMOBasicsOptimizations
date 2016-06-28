package main.java.ru.andrey;

import static java.lang.Math.exp;
import static java.lang.Math.pow;
import static java.lang.Math.random;

public class SimulatedAnnealing extends OptimizationMethod {
    private double temperature;

    public SimulatedAnnealing(int dimension) {
        super(dimension);
    }

    public static void main(String[] args) {
        double acc = 0.0;
        SimulatedAnnealing s = new SimulatedAnnealing(5);
        for (int i = 0; i < 100; ++i) {
            Solution opt = s.searchOptimum();
            acc += opt.getQuality();
            System.out.println("Optimum " + opt.getQuality());
        }
        System.out.println("Average " + acc / 100.0);
    }

    @Override
    public Solution searchOptimum() {
        Oracle oracle = new Oracle(dimension);
        temperature = ((int) 10E4 * pow(dimension, 2)) - (100 * dimension);
//        temperature = ((int) 10E4 * pow(dimension, 2)) - (100 * dimension);
//        temperature = ((int) 10E4 * pow(dimension, 2)) / 2;


        Solution currentSolution = new Solution(oracle, randomGeneratedSolution());
        Solution globalOptimum = new Solution(currentSolution);

        while (true) {
            Solution pretendentSolution = new Solution(oracle, randomGeneratedSolution());
            if (pretendentSolution.broken()) {
                break;
            }

            if (pretendentSolution.getQuality() < currentSolution.getQuality() ||
                    random() < exp((currentSolution.getQuality() - pretendentSolution.getQuality()) / temperature)) { // we are looking for minimum
                currentSolution = pretendentSolution;
            }

            if (globalOptimum == null || currentSolution.getQuality() < globalOptimum.getQuality()) {
                globalOptimum = new Solution(currentSolution);
            }

            temperature--;
        }
//        System.out.println(temperature);

//        Solution potentialSolution = new Solution(globalOptimum);
//        double step = 0.1;
//        potentialSolution.mutate(step);
//        if (potentialSolution.broken()) {
//            return globalOptimum;
//        }
//        if (potentialSolution.getQuality() > globalOptimum.getQuality()) {
//            step += -1;
//            potentialSolution = new Solution(globalOptimum);
//        }
//
//        while (true) {
//            potentialSolution.mutate(step);
//            if (potentialSolution.getQuality() < globalOptimum.getQuality()) {
//                globalOptimum = new Solution(potentialSolution);
//            } else {
//                break;
//            }
//        }

        return globalOptimum;
    }
}
