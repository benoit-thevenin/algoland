package model;

import utils.Rand;

public class ExampleSolution implements Solution<Integer> {
    /**
     * Each int value in the array represents a possible action.
     */
    private Integer[] actions;
    /**
     * the number of different actions
     */
    private int actionsSize;
    /**
     * The score for this solution.
     */
    private double score = Double.NEGATIVE_INFINITY;

    public ExampleSolution() {
        super();
    }

    public ExampleSolution(ExampleSolution solution) {
        actions = new Integer[solution.getActions().length];
        System.arraycopy(solution.getActions(), 0, actions, 0, actions.length);
        actionsSize = solution.getActionsSize();
        score = solution.getScore();
    }

    @Override
    public Solution<Integer> deepCopy() {
        return new ExampleSolution(this);
    }

    @Override
    public Solution<Integer> merge(Solution<Integer> solution) {
        Solution<Integer> child = new ExampleSolution();
        child.setActionsSize(actionsSize);
        child.setDepth(actions.length);
        for (int i = 0; i < actions.length; i++) {
            if (Rand.randInt(2) == 0) child.getActions()[i] = actions[i];
            else child.getActions()[i] = solution.getActions()[i];
        }
        return child;
    }

    @Override
    public void mutate() {
        actions[Rand.randInt(actions.length)] = Rand.randInt(actionsSize);
    }

    @Override
    public void randomize() {
        for (int i = 0; i < actions.length; i++) {
            actions[i] = Rand.randInt(actionsSize);
        }
    }

    @Override
    public Integer[] getActions() {
        return actions;
    }

    @Override
    public int getActionsSize() {
        return actionsSize;
    }

    @Override
    public void setActionsSize(int actionsSize) {
        this.actionsSize = actionsSize;
    }

    @Override
    public int getDepth() {
        return actions.length;
    }

    @Override
    public void setDepth(int depth) {
        actions = new Integer[depth];
    }

    @Override
    public double getScore() {
        return score;
    }

    @Override
    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public int compareTo(Solution<Integer> o) {
        return Double.compare(o.getScore(), score);
    }
}
