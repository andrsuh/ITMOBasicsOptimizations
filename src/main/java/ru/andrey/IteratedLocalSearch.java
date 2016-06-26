package main.java.ru.andrey;


import java.lang.reflect.Array;
import java.util.Arrays;

import static java.lang.Math.random;

public class IteratedLocalSearch {
    private int dimension;
    private Oracle oracle;
    private double step = 0.005;

    public static void main(String[] args) {
        double acc = 0.0;
        IteratedLocalSearch s = new IteratedLocalSearch(3);
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
            int time = (int) (Math.random() * 900 + 100);

            Solution temp = new Solution(potentialSolution);
            temp.mutate(step, false);
            if (temp.getQuality() > potentialSolution.getQuality()) {
                step *= -1;
            }

            while (time-- > 0) {
                Solution newSolution = new Solution(potentialSolution);
                newSolution.mutate(step, false);

                if (newSolution.isBroken()) {
//                    Arrays.stream(bestSolution.getSolution()).forEach(System.out::println);
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

            if (homeBase.getQuality() > potentialSolution.getQuality()) {
                homeBase = new Solution(potentialSolution);
            }

            double randomJump = (Math.random() * 2 - 1);
//            homeBase.mutate(randomJump, false);
//            potentialSolution = new Solution(oracle, homeBase.getSolution());
            potentialSolution.mutate(randomJump, false);
            if (potentialSolution.isBroken()) {
//                Arrays.stream(bestSolution.getSolution()).forEach(System.out::println);
                return bestSolution.getQuality();
            }
        }
    }
}
