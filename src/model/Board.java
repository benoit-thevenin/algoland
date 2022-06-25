package model;

/**
 * Represents a 'world' where we can run simulation, evaluate the results and rollback to the initial state
 * @param <E> the class type of action contained in Solution
 */
public interface Board<E> {

    /**
     * Save the current state of the Board
     */
    void save();

    /**
     * @return an empty Solution of the chosen implementation
     */
    Solution<E> buildSolution();

    /**
     * Perform a simulation with a given solution. Alters the Board by performing the different actions of the solution
     * @param solution to simulate
     */
    void simulate(Solution<E> solution);

    /**
     * Evaluate the Board in order to sort the solutions.
     * @return the score of the Board. The higher the score, the better is the simulated solution.
     */
    double evaluate();

    /**
     * Use the saved values to apply a rollback on the Board after a simulation
     */
    void rollback();
}
