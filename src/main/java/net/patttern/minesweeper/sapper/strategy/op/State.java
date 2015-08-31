/**
 * An <code>Element</code> state. State of the element is a minimum and
 * maximum number of mines in it.
 * 
 * @author Pestov Oleg
 * @version 1.0
 */

package net.patttern.minesweeper.sapper.strategy.op;

public class State
{
    /**
     * Minimum number of mines in the set.
     */
    private int min;

    /**
     * Maximum number of mines in the set.
     */
    private int max;

    /**
     * The default constructor is unavailable.
     */
    private State()
    {}

    /**
     * Creates a new <code>State</code> with given minimum and maximum number
     * of mines.
     * 
     * @param min
     *            minimum number of mines.
     * @param max
     *            maximum number of mines.
     */
    public State(int min, int max)
    {
        this.min = min;
        this.max = max;
    }

    /**
     * Returns the maximum number of mines that could be located in this set of
     * cells.
     * 
     * @return the maximum number of mines that could be located in this set of
     *         cells.
     */
    public int getMax()
    {
        return max;
    }

    /**
     * Sets the maximum number of mines that could be located in this set of
     * cells. Note, that this number couldn't increase during the time.
     * 
     * @param max
     *            the new value of the maximum number of mines.
     * @return true if the value was changed and false otherwise.
     * @throws AlgorithmException
     *             when maximum becomes more than minimum.
     */
    public boolean setMax(int max) throws AlgorithmException
    {
        boolean r = false;

        if (max < this.max)
        {
            r = true;
            this.max = max;
        }
        if (this.max < this.min)
            throw new AlgorithmException("The maximum value of mines became less than a minimum");

        return r;
    }

    /**
     * Returns the minimum number of mines that could be located in this set of
     * cells.
     * 
     * @return the minimum number of mines that could be located in this set of
     *         cells.
     */
    public int getMin()
    {
        return min;
    }

    /**
     * Sets the minimum number of mines that could be located in this set of
     * cells. Note, that this number couldn't decrease during the time.
     * 
     * @param min
     *            the new value of the minimum number of mines.
     * @return true if the value was changed and false otherwise.
     * @throws AlgorithmException
     *             when minimum becomes more than maximum.
     */
    public boolean setMin(int min) throws AlgorithmException
    {
        boolean r = false;

        if (min > this.min)
        {
            r = true;
            this.min = min;
        }
        if (this.max < this.min)
            throw new AlgorithmException("The minimum value of mines became more than a maximum");

        return r;
    }

    /**
     * Returns a string representation of this <code>State</code>.
     * 
     * @return a string representation of this <code>State</code>.
     */
    public String toString()
    {
        return min + "-" + max;
    }
}