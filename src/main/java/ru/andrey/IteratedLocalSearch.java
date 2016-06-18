package ru.andrey;

public class IteratedLocalSearch {
    private Oracle oracle = new Oracle();
    private double step = 0.005;

    public static void main(String[] args) {
        IteratedLocalSearch s = new IteratedLocalSearch();
        System.out.println("Optimum " + s.searchOptimum());
    }

    public Double searchOptimum() {
        double potentialSolution = Math.random() * 20 - 10; // [-10, 10)
        double potentialSolutionQuality = oracle.quality(potentialSolution);

        double homeBase = potentialSolution;
        double homeBaseQuality = potentialSolutionQuality;

        Double bestSolution = potentialSolution;
        double bestSolutionQuality = potentialSolutionQuality;

        while (true) {
            double time = (int) (Math.random() * 300 + 100); // [100, 999]
            while (time-- > 0) {
                double newSolution = potentialSolution + step;
                Double newSolutionQuality = oracle.quality(newSolution);
                if (newSolutionQuality == null) {
                    return bestSolution;
                }

                if (potentialSolutionQuality > newSolutionQuality) {
                    System.out.println("CurSolution: " + potentialSolution + " CurQuality: " + potentialSolutionQuality);
                    System.out.println("----------------------------------------------------------");

                    potentialSolution = newSolution;
                    potentialSolutionQuality = newSolutionQuality;
                }
            }

            if (bestSolutionQuality > potentialSolutionQuality) {
                bestSolution = potentialSolution;
                bestSolutionQuality = potentialSolutionQuality;
            }

            if (homeBaseQuality > potentialSolutionQuality) {
                homeBase = potentialSolution;
                homeBaseQuality = potentialSolutionQuality;
            }

            potentialSolution = homeBase + (Math.random() - 0.5);
        }
    }
}
