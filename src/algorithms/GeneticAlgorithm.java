package algorithms;

import model.Board;
import model.Solution;
import utils.Rand;

import java.util.ArrayList;
import java.util.List;

public class GeneticAlgorithm extends Algorithm {

    /**
     * The number of solutions in a population
     */
    private final int populationSize;
    /**
     * A double between 0.0 and 1.0 determining the mutation probability. A mutationProbability of 0.25 means that 25%
     * of children will mutate accross generations.
     */
    private final double mutationProbability;
    /**
     * The maximum number of generations to be produced by the algorithm
     */
    private final int generationLimit;

    /**
     * The best Solution found so far
     */
    private Solution bestSolution;
    /**
     * The System.nanoTime() value when the algorithm started
     */
    private long begin;

    /**
     * @param depth the fixed depth of the solutions tried during the algorithm
     * @param actionSize the number of different actions composing a Solution
     * @param quantityLimit the maximum number of solutions tried by the algorithm
     * @param nanoTimeLimit the maximum execution time allocated to the algorithm in nanosecond
     * @param populationSize the number of solutions in a generation
     * @param mutationProbability the probability for a children to mutate
     * @param generationLimit the maximum number of generations to be produced by the algorithm
     */
    public GeneticAlgorithm(int depth,int actionSize, int quantityLimit, long nanoTimeLimit,
                            int populationSize, double mutationProbability, int generationLimit) {
        super(depth, actionSize, quantityLimit, nanoTimeLimit);
        // It's impossible to cross solutions if the population contains less than 2 elements
        if (populationSize < 2) throw new IllegalArgumentException("populationSize must be greater than 1");
        if (mutationProbability < 0.0 || mutationProbability > 1.0) throw new IllegalArgumentException("mutationProbability must be between 0.0 and 1.0");
        if (generationLimit < 1) throw new IllegalArgumentException("generationLimit must be greater than 0");
        this.populationSize = populationSize;
        this.mutationProbability = mutationProbability;
        this.generationLimit = generationLimit;
    }

    @Override
    public Solution searchBestSolution(Board board) {
        begin = System.nanoTime();
        // Initialize an empty bestSolution with a score of Double.NEGATIVE_INFINITY
        bestSolution = board.buildSolution();
        board.save();
        List<Solution> population = buildFirstGeneration(board);
        int numberOfGenerations = 1;
        int numberOfSolutions = populationSize;
        while (System.nanoTime() - begin < nanoTimeLimit && numberOfSolutions < quantityLimit && numberOfGenerations < generationLimit) {
            numberOfGenerations++;
            numberOfSolutions += populationSize;
            population = buildNextGeneration(board, population);
        }
        return bestSolution;
    }

    /**
     * @param board used to simulate the solutions and evaluate them
     * @return a randomly generated first population
     */
    private List<Solution> buildFirstGeneration(Board board) {
        List<Solution> population = new ArrayList<>(populationSize);
        for (int i = 0; i < populationSize; i++) {
            Solution solution = board.buildSolution();
            solution.setDepth(depth);
            solution.setActionsSize(actionsSize);
            solution.randomize();
            board.simulate(solution);
            solution.setScore(board.evaluate());
            board.rollback();
            if (solution.getScore() > bestSolution.getScore()) bestSolution = solution;
            population.add(solution);
        }
        return population;
    }

    /**
     * @param board used to simulate the solutions and evaluate them
     * @param population the previous population used to generate the next one
     * @return a new population issued from the previous one by crossing the solutions and applying mutations
     */
    private List<Solution> buildNextGeneration(Board board, List<Solution> population) {
        List<Solution> nextPopulation = new ArrayList<>();
        // Here we keep the bestSolution from the previous generation in the next one
        Solution solution = bestSolution.deepCopy();
        // We force a mutation on it because otherwise there would be no point in evaluating it again
        solution.mutate();
        board.simulate(solution);
        solution.setScore(board.evaluate());
        board.rollback();
        if (solution.getScore() > bestSolution.getScore()) bestSolution = solution;
        nextPopulation.add(solution);
        while (System.nanoTime() - begin < nanoTimeLimit && nextPopulation.size() < populationSize) {
            Solution child = buildNextChild(population);
            board.simulate(child);
            child.setScore(board.evaluate());
            board.rollback();
            if (child.getScore() > bestSolution.getScore()) bestSolution = child;
            nextPopulation.add(child);
        }
        return nextPopulation;
    }

    /**
     * We first pick 2 potential parents that fight with there getScore() to have the right to breed. The winner will
     * be the first parent. We then do it again with 2 other parents. The 2 winners can breed the child.
     * @param population the potential parents for the generated child
     * @return a child that is a cross between 2 parents and have a potential mutation
     */
    private Solution buildNextChild(List<Solution> population) {
        int index1 = Rand.randInt(populationSize);
        int index2 = Rand.randInt(populationSize);
        while (index1 == index2) index2 = Rand.randInt(populationSize);
        // Between 2 potential parents, only the best will be able to breed
        int parentIndex1 = population.get(index1).getScore() > population.get(index2).getScore() ? index1 : index2;
        index1 = Rand.randInt(populationSize);
        index2 = Rand.randInt(populationSize);
        while (index1 == index2) index2 = Rand.randInt(populationSize);
        // Between 2 other potential parents, only the best will be able to breed
        int parentIndex2 = population.get(index1).getScore() > population.get(index2).getScore() ? index1 : index2;
        Solution child = population.get(parentIndex1).merge(population.get(parentIndex2));
        if (Math.random() < mutationProbability) child.mutate();
        return child;
    }
}
