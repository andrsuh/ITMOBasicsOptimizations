package ru.andrey;

import static java.lang.Math.*;

public class SimulatedAnnealing {
    private double temperature = 1000;
//    private double scedule = 0.1;
    private Oracle oracle = new Oracle();

    public static void main(String[] args) {
        SimulatedAnnealing s = new SimulatedAnnealing();
        System.out.println("Optimum " + s.searchOptimum());
    }

    public Double searchOptimum() {
        Double globalOptimum = null;
        double globalOptimumQuality = Double.POSITIVE_INFINITY;

        double currentSolution = random() * 20 - 10; // [-10, 10)
        double currentQuality = oracle.quality(currentSolution);

        while (temperature > 0 + 0.0001) {
            double pretendentSolution = random() * 20 - 10;
            Double pretendentQuality = oracle.quality(pretendentSolution);

            if (pretendentQuality == null) {
                System.out.println("Number of attempts = 0");
                break;
            }

            if (pretendentQuality < currentQuality || random() < exp((currentQuality - pretendentQuality) / temperature)) { // we are looking for minimum
                System.out.println("CurSolution: " + currentSolution + " CurQuality: " + currentQuality);
                System.out.println("----------------------------------------------------------");
                currentSolution = pretendentSolution;
                currentQuality = pretendentQuality;
            }

            temperature /= 1.004;

            if (currentQuality < globalOptimumQuality) {
                globalOptimum = currentSolution;
                globalOptimumQuality = currentQuality;
            }

            if (temperature < 0 + 0.0001) {
                System.out.println("Temperature = 0");
            }
        }

        return globalOptimum;
    }
}
