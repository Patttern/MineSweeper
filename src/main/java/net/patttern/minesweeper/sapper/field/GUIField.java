/**
 * Represents a user field. User field differs from the playing field by
 * possibility for each cell to be marked as mine.
 * 
 * @author Pestov Oleg
 * @version 1.0
 */

package net.patttern.minesweeper.sapper.field;

public class GUIField extends PlayingField
{
    /**
     * Indicates that cell is marked as mine.
     */
    public static final int MINE_MARK = -3;

    /**
     * Wrapper for playing field. <code>TRUE</code> indicates that the cell is
     * marked as mine.
     */
    private boolean[][] isMarkedAsMine;

    /**
     * Creates a new user field where each cell by default is not marked as
     * mine.
     * 
     * @see field.PlayingField#PlayingField().
     */
    public GUIField()
    {
        isMarkedAsMine = new boolean[rowsCount][colsCount];
    }

    /**
     * Creates a new user field where each cell by default is not marked as
     * mine.
     * 
     * @see field.PlayingField#PlayingField(boolean[][]).
     */
    public GUIField(boolean[][] a)
    {
        super(a);
        isMarkedAsMine = new boolean[rowsCount][colsCount];
    }

    /**
     * Creates a new user field where each cell by default is not marked as
     * mine.
     * 
     * @see field.PlayingField#PlayingField(int, int, int).
     */
    public GUIField(int rows, int cols, int mines)
    {
        super(rows, cols, mines);
        isMarkedAsMine = new boolean[rowsCount][colsCount];
    }

    /**
     * Creates a new user field where each cell by default is not marked as
     * mine.
     * 
     * @see field.PlayingField#PlayingField(long).
     */
    public GUIField(long seed)
    {
        super(seed);
        isMarkedAsMine = new boolean[rowsCount][colsCount];
    }

    /**
     * Creates a new user field where each cell by default is not marked as
     * mine.
     * 
     * @see field.PlayingField#PlayingField(long, int, int, int).
     */
    public GUIField(long seed, int rows, int cols, int mines)
    {
        super(seed, rows, cols, mines);
        isMarkedAsMine = new boolean[rowsCount][colsCount];
    }

    /**
     * Marks cell. If the cell was <code>UNKNOWN</code> then it becames
     * <code>MINE_MARK</code> and it becames <code>UNKNOWN</code> if it was
     * <code>MINE_MARK</code>.
     * 
     * @param row
     *            row index of the opening cell.
     * @param col
     *            column index of the opening cell.
     */
    public void markCell(int row, int col)
    {
        if (getCellState(row, col) == MINE_MARK)
            isMarkedAsMine[row][col] = false;
        else if (getCellState(row, col) == UNKNOWN)
            isMarkedAsMine[row][col] = true;
    }

    /**
     * Returns a state of the cell.
     * 
     * @param row
     *            row index.
     * @param col
     *            column index.
     * @return the constant <code>MINE_MARK</code> if the cell is not not
     *         opened and it is marked as mine, the constant
     *         <code>UNKNOWN</code> if the cell is not opened and not marked
     *         as mine, the constant <code>MINE</code> if the cell is opened
     *         and it contains mine and then number of mines in adjacent cells
     *         if the cell is opened and it is empty.
     */
    public int getCellState(int row, int col)
    {
        if (isMarkedAsMine[row][col])
            return MINE_MARK;
        else
            return super.getCellState(row, col);
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
        isMarkedAsMine[row][col] = false;
        return super.openCell(row, col);
    }
}