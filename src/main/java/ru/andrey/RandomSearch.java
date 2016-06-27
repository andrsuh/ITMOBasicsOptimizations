package main.java.ru.andrey;

import java.util.Arrays;

public class RandomSearch {
    private int dimension;
    private double step = 1;
    private Oracle oracle;

    public static void main(String[] args) {
        double acc = 0.0;
        RandomSearch s = new RandomSearch(5);
        for (int i = 0; i < 100; ++i) {
            double opt = s.searchOptimum();
            acc += opt;
            System.out.println("Optimum " + opt);
        }
        System.out.println("Average " + acc / 100.0);
    }

    public RandomSearch(int dimension) {
        this.dimension = dimension;
    }

    public Double searchOptimum() {
        oracle = new Oracle(dimension);
        Solution globalOptimum = null;

        while (true) {
            Solution currentSolution = new Solution(
                    oracle,
                    Arrays.stream(new double[dimension])
                        .map((x) -> Math.random() * 20 - 10) // generate vector of numbers from [-10, 10)
                        .toArray()
            );

            if (currentSolution.broken()) {
                return globalOptimum.getQuality();
            }

            while (true) {
                Solution pretendentSolution = new Solution(currentSolution);
                pretendentSolution.mutate(step, true);

                if (pretendentSolution.broken()) {
                    return globalOptimum.getQuality();
                }

                if (pretendentSolution.getQuality() < currentSolution.getQuality()) { // we are looking for minimum
//                    System.out.println("CurSolution: " + currentSolution + " CurQuality: " + currentSolution.getQuality());
//                    System.out.println("----------------------------------------------------------");
                    currentSolution = new Solution(pretendentSolution);
                } else {
                    if (globalOptimum == null || currentSolution.getQuality() < globalOptimum.getQuality()) {
                        globalOptimum = new Solution(currentSolution);
//                        int times = 1000;
//                        while (times-- == 0 || globalOptimum == null) {
//                            if (currentSolution.getQuality() < globalOptimum.getQuality()) {
//                                globalOptimum = new Solution(currentSolution);
//                            }
//                            currentSolution.mutate(0.1, true);
//                            if (currentSolution.broken()) {
//                                return globalOptimum.getQuality();
//                            }
//                        }
                    }
                    break;
                }
            }
        }
    }
}
