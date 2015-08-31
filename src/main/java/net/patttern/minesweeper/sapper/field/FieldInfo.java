/**
 * Field adapter that allows only to retrieve info from the field.
 * 
 * @author Pestov Oleg
 * @version 1.0
 */

package net.patttern.minesweeper.sapper.field;

public class FieldInfo
{
    /**
     * Underlying field.
     */
    private Field field;

    /**
     * The default constructor is unavailable.
     */
    private FieldInfo()
    {}

    /**
     * Creates a new field adapter.
     * 
     * @param field
     *            the minesweeper field for retrieving information.
     */
    public FieldInfo(Field field)
    {
        this.field = field;
    }

    /**
     * Returns the number of rows in the field.
     * 
     * @return the number of rows in the field.
     */
    public int getRowsCount()
    {
        return field.getRowsCount();
    }

    /**
     * Returns the number of columns in the field.
     * 
     * @return the number of columns in the field.
     */
    public int getColumnsCount()
    {
        return field.getColumnsCount();
    }

    /**
     * Returns the number of mines in the field.
     * 
     * @return the number of mines in the field.
     */
    public int getMinesCount()
    {
        return field.getMinesCount();
    }

    /**
     * Returns a state of the cell.
     * 
     * @param row
     *            row index
     * @param col
     *            column index
     * @return state of the cell
     */
    public int getCellState(int row, int col)
    {
        return field.getCellState(row, col);
    }

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
}