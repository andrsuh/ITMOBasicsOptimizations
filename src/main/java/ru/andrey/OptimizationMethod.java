package ru.andrey;

import java.util.Arrays;

import static java.lang.Math.random;

abstract class OptimizationMethod {
    protected int dimension;
    protected Oracle oracle;

    public OptimizationMethod(int dimension) {
        this.dimension = dimension;
    }

    public abstract Solution searchOptimum();

    public double getDifference() {
        Solution optimum = searchOptimum();
        return Math.abs(optimum.getQuality() - oracle.getOptimum());
    }

    protected final double[] randomGeneratedSolution() {
        return Arrays.stream(new double[dimension]) // generate vector of numbers from [-10, 10)
                .map((x) -> random() * 20 - 10)
                .toArray();
    }
}
