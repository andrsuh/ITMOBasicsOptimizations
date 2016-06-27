package main.java.ru.andrey;

import java.util.Arrays;

import static java.lang.Math.random;

public class IteratedLocalSearch {
    private int dimension;
    private Oracle oracle;
    private double step = 0.05;

    public static void main(String[] args) {
        double acc = 0.0;
        IteratedLocalSearch s = new IteratedLocalSearch(10);
        for (int i = 0; i < 100; ++i) {
            double opt = s.searchOptimum();
            acc += opt;
            System.out.println("Optimum " + opt);
        }
        System.out.println("Average " + acc / 100.0);
    }

    public IteratedLocalSearch(int dimension) {
        this.dimension = dimension;
    }

    public Double searchOptimum() {
        oracle = new Oracle(dimension);

        Solution potentialSolution = new Solution(
                oracle,
                Arrays.stream(new double[dimension])
                        .map((x) -> random() * 20 - 10) // [-10, 10)
                        .toArray()
        );
        Solution homeBase = new Solution(potentialSolution);
        Solution bestSolution = new Solution(potentialSolution);

        while (true) {
            int time = dimension * (int) Math.random() * 900 + 100;

            while (time-- > 0) {
                Solution newSolution = new Solution(potentialSolution);
                newSolution.mutate(step, true);

                if (newSolution.broken()) {
                    return bestSolution.getQuality();
                }

                if (potentialSolution.getQuality() > newSolution.getQuality()) {
//                    System.out.println("CurSolution: " + potentialSolution.getSolution() + " CurQuality: " + potentialSolution.getQuality());
//                    System.out.println("----------------------------------------------------------");
                    potentialSolution = newSolution;
                }
            }

            if (bestSolution.getQuality() > potentialSolution.getQuality()) {
                bestSolution = new Solution(potentialSolution);
            }

            if (homeBase.getQuality() > potentialSolution.getQuality() || 0.1 > random()) {
                homeBase = new Solution(potentialSolution);
            }

            double randomJump = Math.random() * 3 - 1.5; // [-1.5, 1.5)
            potentialSolution.mutate(randomJump, true);
            if (potentialSolution.broken()) {
                return bestSolution.getQuality();
            }
        }
    }
}
