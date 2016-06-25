package main.java.ru.andrey;


public class IteratedLocalSearch {
    private Oracle oracle = new Oracle();
    private double step = 0.005;

    public static void main(String[] args) {
        double acc = 0.0;
        for (int i = 0; i < 100; ++i) {
            IteratedLocalSearch s = new IteratedLocalSearch();
            double opt = s.searchOptimum();
            acc += opt;
            System.out.println("Optimum " + opt);
        }
        System.out.println("Average " + acc / 100.0);
    }

    public Double searchOptimum() {
        Solution potentialSolution = new Solution(oracle, Math.random() * 20 - 10); // [-10, 10)
        Solution homeBase = new Solution(potentialSolution);
        Solution bestSolution = new Solution(potentialSolution);

        while (true) {
//            int time = (int) (Math.random() * 900 + 100);

            int time = 800;
//            Solution temp = new Solution(potentialSolution);
//            temp.mutation(step, false);
//            if (temp.getQuality() > potentialSolution.getQuality()) {
//                step *= -1;
//            }

            while (time-- > 0) {
                Solution newSolution = new Solution(potentialSolution);
                newSolution.mutation(step, false);

                if (newSolution.isBroken()) {
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

            double randomJump = (Math.random() * 1 - 1);
//            homeBase.mutation(randomJump, false);
//            potentialSolution = new Solution(oracle, homeBase.getSolution());
            potentialSolution.mutation(randomJump, false);
            if (potentialSolution.isBroken()) {
                return bestSolution.getQuality();
            }
        }
    }
}
