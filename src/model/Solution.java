package model;

public interface Solution<E> extends Comparable<Solution<E>> {

    /**
     * @return a deep copy of this
     */
    Solution<E> deepCopy();

    /**
     * merge this with solution to generate a new Solution
     * @param solution to merge with
     * @return the result of the merge
     */
    Solution<E> merge(Solution<E> solution);

    /**
     * Randomly pick an action of the solution and change its value to a new random value
     */
    void mutate();

    /**
     * Change all actions of the solution to new random ones
     */
    void randomize();

    /**
     * @return the actions of the solution
     */
    E[] getActions();

    /**
     * @return the value of actionsSize
     */
    int getActionsSize();

    /**
     * set the value of actionsSize
     * @param actionsSize to set
     */
    void setActionsSize(int actionsSize);

    /**
     * @return the value of depth
     */
    int getDepth();

    /**
     * set the value of depth
     * @param depth to set
     */
    void setDepth(int depth);

    /**
     * @return the value of score
     */
    double getScore();

    /**
     * set the value of score
     * @param score to set
     */
    void setScore(double score);
}
