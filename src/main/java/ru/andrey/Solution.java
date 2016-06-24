package main.java.ru.andrey;

public class Solution {
    private Double solution;
    private Double quality;
    private Oracle oracle;

    public Solution(Oracle oracle, double initialSolution) {
        this.oracle = oracle;
        this.solution = initialSolution;

        quality = oracle.quality(solution);
    }

    public Solution(Solution other) {
        this.oracle = other.oracle;
        this.solution = other.solution.doubleValue();
        this.quality = other.quality.doubleValue();
    }

    public Double getSolution() {
        return solution;
    }

    public Double getQuality() {
        return quality;
    }

    public boolean isBroken() {
        return quality == null;
    }
}
