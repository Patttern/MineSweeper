/**
 * A field cell.
 * 
 * @author Pestov Oleg
 * @version 1.0
 */

package net.patttern.sapper.field;

public class FieldCell implements Comparable
{
    /**
     * Row index of the cell.
     */
    private int row;

    /**
     * Column index of the cell.
     */
    private int column;

    /**
     * Hashcode of this <code>FieldCell</code>
     */
    private int hashCode = 0;

    /**
     * String representation of this <code>FieldCell</code>.
     */
    private String str = null;

    /**
     * The default constructor is unavailable.
     */
    private FieldCell()
    {
    }    
    
    /**
     * Creates a new field cell.
     * 
     * @param row
     *            cell's row index.
     * @param column
     *            cell's column index.
     */
    public FieldCell(int row, int column)
    {
        this.row = row;
        this.column = column;
    }

    /**
     * Creates a new field cell.
     * 
     * @param c
     *            cell.
     */
    public FieldCell(FieldCell c)
    {
        this.row = c.getRow();
        this.column = c.getColumn();
    }

    /**
     * Returns the column index of the cell.
     * 
     * @return the column index of the cell.
     */
    public int getColumn()
    {
        return column;
    }

    /**
     * Returns the row index of the cell.
     * 
     * @return the row index of the cell.
     */
    public int getRow()
    {
        return row;
    }

    /**
     * Compares two <code>FieldCell</code> s for ordering. The upper right
     * cell is considered to be smaller.
     * 
     * @param arg0
     *            <code>FieldCell</code> to be compared.
     * @return the value <code>0</code> if the argument <code>FieldCell</code>
     *         is equal to this <code>FieldCell</code>; a value less than
     *         <code>0</code> if this <code>FieldCell</code> is before the
     *         <code>FieldCell</code> argument; and a value greater than
     *         <code>0</code> if this <code>FieldCell</code> is after the
     *         <code>FieldCell</code> argument.
     */
    public int compareTo(Object arg0)
    {
        if (arg0 instanceof FieldCell)
        {
            FieldCell c = (FieldCell) arg0;

            if (row == c.row)
                return column - c.column;
            else
                return row - c.row;
        }
        else
            throw new ClassCastException();
    }

    /**
     * Determines whether or not two cells are equal. Two instances of
     * <code>FieldCell</code> are equal if the values of their
     * <code>row</code> and <code>column</code> member fields, representing
     * their position in the coordinate space, are the same.
     * 
     * @param arg0
     *            an object to be compared with this <code>FieldCell</code>.
     * @return <code>true</code> if the object to be compared is an instance
     *         of <code>FieldCell</code> and has the same values;
     *         <code>false</code> otherwise.
     */
    public boolean equals(Object arg0)
    {
        if (arg0 instanceof FieldCell)
        {
            FieldCell cell = (FieldCell) arg0;
            return (getRow() == cell.getRow()) && (getColumn() == cell.getColumn());
        }
        return super.equals(arg0);
    }

    /**
     * Returns the hashcode for this <code>FieldCell</code>.
     * 
     * @return a hash code for this <code>FieldCell</code>.
     */
    public int hashCode()
    {
        if (hashCode == 0)
            hashCode = row * 97 + column;

        return hashCode;
    }

    /**
     * Returns a string representation of this <code>FieldCell</code>.
     * 
     * @return a string representation of this <code>FieldCell</code>.
     */
    public String toString()
    {
        if (str == null)
            str = "(" + row + "," + column + ")";
        return str;
    }
}