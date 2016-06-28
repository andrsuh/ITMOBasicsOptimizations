package main.java.ru.andrey;

import static java.lang.Math.random;

public class IteratedLocalSearch extends OptimizationMethod {
    private double step = 0.05;

    public IteratedLocalSearch(int dimension) {
        super(dimension);
    }

    public static void main(String[] args) {
        double acc = 0.0;
        IteratedLocalSearch s = new IteratedLocalSearch(10);
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

        Solution potentialSolution = new Solution(oracle, randomGeneratedSolution());
        Solution homeBase = new Solution(potentialSolution);
        Solution bestSolution = new Solution(potentialSolution);

        while (true) {
            int time = dimension * (int) Math.random() * 900 + 100;

            while (time-- > 0) {
                Solution newSolution = new Solution(potentialSolution);
                newSolution.mutate(step);

                if (newSolution.broken()) {
                    return bestSolution;
                }

                if (potentialSolution.getQuality() > newSolution.getQuality()) {
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
            potentialSolution.mutate(randomJump);
            if (potentialSolution.broken()) {
                return bestSolution;
            }
        }
    }
}
