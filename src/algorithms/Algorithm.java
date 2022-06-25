package algorithms;

import model.Board;
import model.Solution;

public abstract class Algorithm {

    /**
     * The fixed depth of the solutions tried during the algorithm
     */
    protected final int depth;
    /**
     * The number of different actions composing a Solution
     */
    protected final int actionsSize;
    /**
     * The maximum number of solutions tried by the algorithm when called
     */
    protected final int quantityLimit;
    /**
     * The maximum execution time allocated to the algorithm in nanosecond
     */
    protected final long nanoTimeLimit;

    /**
     * @param depth the fixed depth of the solutions tried during the algorithm
     * @param actionsSize the number of different actions composing a Solution
     * @param quantityLimit the maximum number of solutions tried by the algorithm
     * @param nanoTimeLimit the maximum execution time allocated to the algorithm in nanosecond
     */
    public Algorithm(int depth, int actionsSize, int quantityLimit, long nanoTimeLimit) {
        if (depth < 1) throw new IllegalArgumentException("depth must be greater than 0");
        // There is no point of looking for the best solution when there is only 1 possible action each turn...
        if (actionsSize < 2) throw new IllegalArgumentException("actionsSize must be greater than 1");
        if (quantityLimit < 1) throw new IllegalArgumentException("quantityLimit must be greater than 0");
        if (nanoTimeLimit < 1) throw new IllegalArgumentException("nanoTimeLimit must be greater than 0");
        this.depth = depth;
        this.actionsSize = actionsSize;
        this.quantityLimit = quantityLimit;
        this.nanoTimeLimit = nanoTimeLimit;
    }

    public abstract Solution searchBestSolution(Board board);
}
