package main.java.ru.andrey;

public class RandomSearch extends OptimizationMethod {
    private double step = 1;

    public RandomSearch(int dimension) {
        super(dimension);
    }

    public static void main(String[] args) {
        double acc = 0.0;
        RandomSearch s = new RandomSearch(5);
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

            while (true) {
                Solution pretendentSolution = new Solution(currentSolution);
                pretendentSolution.mutate(step);

                if (pretendentSolution.broken()) {
                    return globalOptimum;
                }

                if (pretendentSolution.getQuality() < currentSolution.getQuality()) { // we are looking for minimum
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
