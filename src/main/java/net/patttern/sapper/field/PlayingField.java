/**
 * Represents a playing field. Playing field differs from the base field by
 * possibility for each cell to be unknown.
 * 
 * @author Pestov Oleg
 * @version 1.0
 */

package net.patttern.sapper.field;

public class PlayingField extends BaseField
{
    /**
     * Unknown cell.
     */
    public static final int UNKNOWN = -2;

    /**
     * Wrapper for minefield. <code>FALSE</code> indicates that the cell is
     * unknown.
     */
    private boolean[][] isOpened;

    /**
     * Creates a new playing field where each cell by default is unknown.
     * 
     * @see field.BaseField#BaseField().
     */
    public PlayingField()
    {
        isOpened = new boolean[rowsCount][colsCount];
    }

    /**
     * Creates a new playing field where each cell by default is unknown.
     * 
     * @see field.BaseField#BaseField(boolean[][]).
     */
    public PlayingField(boolean[][] a)
    {
        super(a);
        isOpened = new boolean[rowsCount][colsCount];
    }

    /**
     * Creates a new playing field where each cell by default is unknown.
     * 
     * @see field.BaseField#BaseField(int, int, int).
     */
    public PlayingField(int rows, int cols, int mines)
    {
        super(rows, cols, mines);
        isOpened = new boolean[rowsCount][colsCount];
    }

    /**
     * Creates a new playing field where each cell by default is unknown.
     * 
     * @see field.BaseField#BaseField(long).
     */
    public PlayingField(long seed)
    {
        super(seed);
        isOpened = new boolean[rowsCount][colsCount];
    }

    /**
     * Creates a new playing field where each cell by default is unknown.
     * 
     * @see field.BaseField#BaseField(long, int, int, int).
     */
    public PlayingField(long seed, int rows, int cols, int mines)
    {
        super(seed, rows, cols, mines);
        isOpened = new boolean[rowsCount][colsCount];
    }

    /**
     * Opens cell.
     * 
     * @param row
     *            row index of the opening cell.
     * @param col
     *            column index of the opening cell.
     */
    public int openCell(int row, int col)
    {
        isOpened[row][col] = true;
        return getCellState(row, col);
    }

    /**
     * Returns a state of the cell.
     * 
     * @param row
     *            row index.
     * @param col
     *            column index.
     * @return the constant <code>UNKNOWN</code> if the cell is not opened,
     *         the constant <code>MINE</code> if the cell is opened and it
     *         contains mine and then number of mines in adjacent cells if the
     *         cell is opened and it is empty.
     */
    public int getCellState(int row, int col)
    {
        if (!isOpened[row][col])
            return UNKNOWN;
        else
            return super.getCellState(row, col);
    }
}