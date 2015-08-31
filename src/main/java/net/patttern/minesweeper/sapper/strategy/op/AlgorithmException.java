/**
 * Represents an algorithm specific exception.
 * 
 * @author Pestov Oleg
 * @version 1.0
 */

package net.patttern.minesweeper.sapper.strategy.op;

public class AlgorithmException extends Exception
{
    /**
     * The default constructor is unavailable.
     */
    private AlgorithmException()
    {}

    /**
     * Creates a new AlgorithmException with a given exception message.
     * 
     * @param exception
     *            exception message
     */
    public AlgorithmException(String message)
    {
        super(message);
    }
}