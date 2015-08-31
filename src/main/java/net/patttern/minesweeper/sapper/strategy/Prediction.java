/**
 * Represents an algorithm prediction.
 * 
 * @author Pestov Oleg
 * @version 1.0
 */

package net.patttern.minesweeper.sapper.strategy;

import net.patttern.minesweeper.sapper.field.FieldCell;

public class Prediction
{
    /**
     * The predictable cell.
     */
    private FieldCell cell;

    /**
     * Indicates that the predictable cell is empty.
     */
    private boolean emptyCell;

    /**
     * Indicates that the prediction is absolute and error is impossible.
     */
    private boolean exactly;

    /**
     * The default constructor is unavailable.
     */
    private Prediction()
    {}

    /**
     * Creates a new algorithm prediction.
     * 
     * @param cell
     *            the predictable cell.
     * @param emptyCell
     *            indicates that the predictable cell is empty.
     * @param exactly
     *            indicates that the prediction is absolute and error is
     *            impossible
     */
    public Prediction(FieldCell cell, boolean emptyCell, boolean exactly)
    {
        this.cell = cell;
        this.emptyCell = emptyCell;
        this.exactly = exactly;
    }

    /**
     * Returns the predictable cell.
     * 
     * @return the predictable cell.
     */
    public FieldCell getCell()
    {
        return cell;
    }

    /**
     * Returns true if the predictable cell is empty and false otherwise.
     * 
     * @return true if the predictable cell is empty and false otherwise.
     */
    public boolean isEmptyCell()
    {
        return emptyCell;
    }

    /**
     * Returns true if the error of prediction is impossible.
     * 
     * @return false if there is no warranty that the cell is exactly the same
     *         as the prediction says.
     */
    public boolean isExactly()
    {
        return exactly;
    }
}