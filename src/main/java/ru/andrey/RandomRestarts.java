package ru.andrey;

public class RandomRestarts extends OptimizationMethod {
    private double step = 0.1;

    public RandomRestarts(int dimension) {
        super(dimension);
    }

    public static void main(String[] args) {
        double acc = 0.0;
        RandomRestarts s = new RandomRestarts(10);
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
        Solution globalOptimum = null;

        while (true) {
            Solution currentSolution = new Solution(oracle, randomGeneratedSolution());

            if (currentSolution.broken()) {
                return globalOptimum;
            }

//            int time = (int) Math.pow(dimension, 2) * (int) (Math.random() * 300 - 100);
            int time = (int) Math.pow(dimension, 2) * (int) (Math.random() * 200 + 100);
            while (time-- != 0) {
                Solution pretendentSolution = new Solution(currentSolution);
                pretendentSolution.mutate(step);

                if (pretendentSolution.broken()) {
                    return globalOptimum;
                }

                if (pretendentSolution.getQuality() < currentSolution.getQuality()) { // we are looking for minimum
                    currentSolution = new Solution(pretendentSolution);
                }

                if (globalOptimum == null || currentSolution.getQuality() < globalOptimum.getQuality()) {
                    globalOptimum = new Solution(currentSolution);
                }
            }
        }
    }
}
