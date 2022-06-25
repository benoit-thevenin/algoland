package algorithms;

import model.Board;
import model.Solution;

public class MonteCarloAlgorithm extends Algorithm {

    /**
     * @param depth the fixed depth of the solutions tried during the algorithm
     * @param actionsSize the number of different actions composing a Solution
     * @param quantityLimit the maximum number of solutions tried by the algorithm
     * @param nanoTimeLimit the maximum execution time allocated to the algorithm in nanosecond
     */
    public MonteCarloAlgorithm(int depth, int actionsSize, int quantityLimit, long nanoTimeLimit) {
        super(depth, actionsSize, quantityLimit, nanoTimeLimit);
    }

    @Override
    public Solution searchBestSolution(Board board) {
        long begin = System.nanoTime();
        Solution bestSolution = null;
        int iteration = 0;
        double maxScore = Double.NEGATIVE_INFINITY;
        board.save();
        while (System.nanoTime() - begin < nanoTimeLimit && iteration < quantityLimit) {
            iteration++;
            Solution solution = board.buildSolution();
            solution.setDepth(depth);
            solution.setActionsSize(actionsSize);
            solution.randomize();
            board.simulate(solution);
            solution.setScore(board.evaluate());
            if (solution.getScore() > maxScore) {
                bestSolution = solution;
                maxScore = solution.getScore();
            }
            board.rollback();
        }
        return bestSolution;
    }
}
