/**
 * Represents an abstract minesweeper field.
 * 
 * @author Pestov Oleg
 * @version 1.0
 */

package net.patttern.sapper.field;

public abstract class Field
{
    /**
     * Number of rows in the field.
     */
    protected int rowsCount;

    /**
     * Number of columns in the field.
     */
    protected int colsCount;

    /**
     * Number of mines in the field.
     */
    protected int minesCount;

    /**
     * Returns a state of the cell.
     * 
     * @param row
     *            row index
     * @param col
     *            column index
     * @return state of the cell
     */
    public abstract int getCellState(int row, int col);

    /**
     * Returns a state of the cell.
     * 
     * @param cell
     *            field cell
     * @return state of the cell
     */
    public int getCellState(FieldCell cell)
    {
        return getCellState(cell.getRow(), cell.getColumn());
    }

    /**
     * Returns the number of rows in the field.
     * 
     * @return the number of rows in the field.
     */
    public int getRowsCount()
    {
        return rowsCount;
    }

    /**
     * Returns the number of columns in the field.
     * 
     * @return the number of columns in the field.
     */
    public int getColumnsCount()
    {
        return colsCount;
    }

    /**
     * Returns the number of mines in the field.
     * 
     * @return the number of mines in the field.
     */
    public int getMinesCount()
    {
        return minesCount;
    }
}