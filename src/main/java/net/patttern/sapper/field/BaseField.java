/**
 * Represents a base minesweeper field. Each cell can contain a mine or be
 * empty. Correspondingly, state of the cell is either constant
 * <code>MINE</code> or the number of mines in adjacent cells. Two cells are
 * adjacent if they share an edge.
 * 
 * @author Pestov Oleg
 * @version 1.0
 */

package net.patttern.sapper.field;

import java.util.Random;

public class BaseField extends Field
{
    /**
     * Mine.
     */
    public static int MINE = -1;

    /**
     * Random numbers generator.
     */
    private Random random;

    /**
     * Default number of rows.
     */
    private static int ROWS = 16;

    /**
     * Default number of cols.
     */
    private static int COLS = 16;

    /**
     * Default number of mines.
     */
    private static int MINES = 16;

    /**
     * Minefield.
     */
    private int[][] field;

    /**
     * Creates a new random minefield with default number of rows, columns and
     * mines. Random generator seed is initialized to a value based on the
     * current time.
     */
    public BaseField()
    {
        this(0);
    }

    /**
     * Creates a new random minefield with default number of rows, columns,
     * mines and given seed for the random generator.
     * 
     * @param seed
     *            initial seed.
     */
    public BaseField(long seed)
    {
        this(seed, ROWS, COLS, MINES);
    }

    /**
     * Creates a new random minefield with given number of rows, columns and
     * mines. Random generator seed is initialized to a value based on the
     * current time
     * 
     * @param rows
     *            number of rows in the field.
     * @param cols
     *            number of columns in the field.
     * @param mines
     *            number of mines in the field.
     */
    public BaseField(int rows, int cols, int mines)
    {
        this(0, rows, cols, mines);
    }

    /**
     * Creates a new random minefield with given number of rows, columns, mines
     * and given seed for the random generator.
     * 
     * @param seed
     *            initial seed.
     * @param rows
     *            number of rows in the field.
     * @param cols
     *            number of columns in the field.
     * @param mines
     *            number of mines in the field.
     */
    public BaseField(long seed, int rows, int cols, int mines)
    {
        random = seed == 0 ? new Random() : new Random(seed);
        boolean[][] bombs = new boolean[rows][cols];

        boolean inverse = false;
        if (2 * minesCount > rows * cols)
        {
            mines = rows * cols - mines;
            inverse = true;
        }

        while (mines > 0)
        {
            int r, c;
            do
            {
                r = random.nextInt(rows);
                c = random.nextInt(cols);
            } while (bombs[r][c]);

            bombs[r][c] = true;
            mines--;
        }

        if (inverse)
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < cols; j++)
                    bombs[i][j] = !bombs[i][j];

        setup(bombs);
    }

    /**
     * Creates a new minefield based on given two dimensional array.
     * 
     * @param a
     *            boolean minefield representation where <code>TRUE</code>
     *            values indicate mines.
     */
    public BaseField(boolean[][] a)
    {
        setup(a);
    }

    /**
     * Creates a new minefield based on given two dimensional array.
     * 
     * @param a
     *            boolean minefield representation where <code>TRUE</code>
     *            values indicate mines.
     */
    private void setup(boolean[][] a)
    {
        rowsCount = a.length;
        colsCount = rowsCount > 0 ? a[0].length : 0;

        minesCount = 0;
        for (int r = 0; r < rowsCount; r++)
            for (int c = 0; c < colsCount; c++)
                if (a[r][c])
                    minesCount++;

        field = new int[rowsCount][colsCount];
        for (int r = 0; r < rowsCount; r++)
            for (int c = 0; c < colsCount; c++)
            {
                field[r][c] = 0;
                for (int dr = -1; dr <= 1; dr++)
                    for (int dc = -1; dc <= 1; dc++)
                        if (r + dr >= 0 && r + dr < rowsCount && c + dc >= 0 && c + dc < colsCount)
                            if (a[r + dr][c + dc])
                                field[r][c]++;
                if (a[r][c])
                    field[r][c] = MINE;
            }
    }

    /**
     * Returns the number of mines in the adjacent cells or constant
     * <code>MINE</code>.
     * 
     * @param row
     *            row index.
     * @param col
     *            column index.
     * @return the number of mines in the adjacent cells or constant
     *         <code>MINE</code>.
     */
    public int getCellState(int row, int col)
    {
        return field[row][col];
    }
}