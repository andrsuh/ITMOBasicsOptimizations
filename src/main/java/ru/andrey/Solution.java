package ru.andrey;

import java.util.Arrays;

public class Solution {
    private double[] solution;
    private Double quality;
    private Oracle oracle;

    public Solution(Oracle oracle, double initialSolution) {
        this(oracle, new double[] {initialSolution});
    }

    public Solution(Oracle oracle, double[] initialSolution) {
        this.oracle = oracle;
        this.solution = initialSolution;
        quality = oracle.quality(solution);
    }

    private Solution(Solution other) {
        this.oracle = other.oracle;
        this.solution = Arrays.copyOf(other.solution, other.solution.length);
        this.quality = other.quality;
    }

    public static Solution copyOf(Solution other) {
        return new Solution(other);
    }

    public void mutate(double step) {
        solution = Arrays.stream(solution)
                .map((x) -> x + (Math.random() * (2 * step) - step)) // for each x in vector plus random number from [-step, step)
                .toArray();

        quality = oracle.quality(solution);
    }

    public double[] getSolution() {
        return solution;
    }

    public Double getQuality() {
        return quality;
    }

    public boolean broken() {
        return quality == null;
    }
}
