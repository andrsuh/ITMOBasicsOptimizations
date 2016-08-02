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

    /**
     * @return difference between minimum of function which was found
     * and realy existing minimum
     */
    public double getDifference() {
        Solution optimum = searchOptimum();
        return Math.abs(optimum.getQuality() - oracle.getOptimum());
    }

    /**
     * Generate vector of real valued numbers of range [-10, 10)
     */
    protected final double[] randomGeneratedSolution() {
        return Arrays.stream(new double[dimension])
                .map((x) -> random() * 20 - 10)
                .toArray();
    }
}
