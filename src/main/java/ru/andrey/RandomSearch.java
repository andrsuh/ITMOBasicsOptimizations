package ru.andrey;

public class RandomSearch {
    private double step = 0.001;
    private Oracle oracle = new Oracle();

    public static void main(String[] args) {
        RandomSearch s = new RandomSearch();
        System.out.println("Optimum " + s.searchOptimum());
    }

    public Double searchOptimum() {
        Double globalOptimum = null;
        double globalOptimumQuality = Double.POSITIVE_INFINITY;
        while (true) {
            double currentSolution = Math.random() * 20 - 10; // [-10, 10)
            double currentQuality = oracle.quality(currentSolution);

            if (oracle.quality(currentSolution + step) > oracle.quality(currentSolution)) {
                step *= -1;
            }

            while (true) {
                double pretendentSolution = currentSolution + step;
                Double pretendentQuality = oracle.quality(pretendentSolution);

                if (pretendentQuality == null) {
                    return globalOptimum;
                }

                if (pretendentQuality < currentQuality) { // we are looking for minimum
                    System.out.println("CurSolution: " + currentSolution + " CurQuality: " + currentQuality);
                    System.out.println("----------------------------------------------------------");
                    currentSolution = pretendentSolution;
                    currentQuality = pretendentQuality;
                } else {
                    if (currentQuality < globalOptimumQuality) {
                        globalOptimum = currentSolution;
                        globalOptimumQuality = currentQuality;
                    }
                    break;
                }
            }
        }
    }
}
