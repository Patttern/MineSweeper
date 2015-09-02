/**
 * Represents an immutable set of unknown cells.
 * 
 * @author Pestov Oleg
 * @version 1.0
 */

package net.patttern.sapper.strategy.op;

import java.util.*;

import net.patttern.sapper.field.FieldCell;

public class Element implements Comparable
{
    /**
     * Empty element.
     */
    public static final Element EMPTY = new Element(new FieldCell[0]);

    /**
     * Unmodifiable sorted collection of cells.
     */
    private List cells;

    /**
     * Hashcode of this <code>Element</code>.
     */
    private int hashCode = 0;

    /**
     * String representation of this <code>Element</code>.
     */
    private String stringRepresentation = null;

    /**
     * The default constructor is unavailable.
     */
    private Element()
    {}

    /**
     * Create a new Element with a given cells in it.
     * 
     * @param c
     *            <code>Element's</code> cells.
     */
    public Element(FieldCell[] c)
    {
        List l = Arrays.asList(c);
        Collections.sort(l);

        cells = Collections.unmodifiableList(l);
    }

    /**
     * Create a new Element with a given collection of cells in it.
     * 
     * @param c
     *            <code>Element's</code> cells.
     */
    public Element(Collection c)
    {
        LinkedList l = new LinkedList(c);
        Collections.sort(l);

        cells = Collections.unmodifiableList(l);
    }

    /**
     * Create a new Element that is the copy of the specified one.
     * 
     * @param c
     *            <code>Element</code> to clone.
     */
    public Element(Element e)
    {
        cells = Collections.unmodifiableList(e.getCells());
    }

    /**
     * Returns the cells as unmodifiable collectioin.
     * 
     * @return the cells.
     */
    public List getCells()
    {
        return cells;
    }

    /**
     * Determines whether or not two elements are equal. Two instances of <code>Element</code> are
     * equal if the cells that they contains are the same.
     * 
     * @param arg0
     *            an object to be compared with this <code>Element</code>.
     * @return <code>true</code> if the object to be compared is an instance of
     *         <code>Element</code> and has the same cells; <code>false</code> otherwise.
     */
    public boolean equals(Object arg0)
    {
        if (arg0 instanceof Element)
            return cells.equals(((Element) arg0).getCells());
        return super.equals(arg0);
    }

    /**
     * Returns the hashcode for this <code>Element</code>.
     * 
     * @return a hash code for this <code>Element</code>.
     */
    public int hashCode()
    {
        if (hashCode == 0)
        {
            int res = 17;
            for (Iterator i = cells.iterator(); i.hasNext();)
            {
                FieldCell c = (FieldCell) i.next();
                res = 37 * res + (c.getRow() * 49 + c.getColumn());
            }
            hashCode = res;
        }
        return hashCode;
    }

    /**
     * Returns the number of cells in this <code>Element</code>.
     * 
     * @return the number of cells in this <code>Element</code>.
     */
    public int size()
    {
        return cells.size();
    }

    /**
     * Returns a set intersection of two <code>Elements</code>.
     * 
     * @param e
     *            element to intersect with this <code>Element</code>.
     * @return intersection of this <code>Element</code> with <code>e</code>.
     */
    public Element meet(Element e)
    {
        LinkedList res = new LinkedList();

        ListIterator i1 = cells.listIterator();
        ListIterator i2 = e.getCells().listIterator();
        while (i1.hasNext() && i2.hasNext())
        {
            FieldCell c1 = (FieldCell) i1.next();
            FieldCell c2 = (FieldCell) i2.next();

            int z = c1.compareTo(c2);
            if (z == 0)
                res.add(c1);
            else if (z < 0)
                i1.previous();
            else
                i2.previous();
        }

        return res.isEmpty() ? Element.EMPTY : new Element(res);
    }

    /**
     * Returns the set of all cells of this <code>Element<code> that are not in a
     * given <code>Element</code>.
     * 
     * @param e
     *            complemented <code>Element</code>. 
     * @return the set of all cells of this <code>Element<code> that are not in a
     * given <code>Element</code>.
     *  
     * @throws AlgorithmException when a given <code>Element</code> is not subset
     * of this <code>Element</code>.
     */
    public Element complement(Element e) throws AlgorithmException
    {
        LinkedList res = new LinkedList();

        ListIterator i1 = cells.listIterator();
        ListIterator i2 = e.getCells().listIterator();
        while (i1.hasNext() && i2.hasNext())
        {
            FieldCell c1 = (FieldCell) i1.next();
            FieldCell c2 = (FieldCell) i2.next();

            int z = c1.compareTo(c2);
            if (z < 0)
            {
                res.add(c1);
                i2.previous();
            }
            else if (z > 0)
                throw new AlgorithmException("The complemented element is not a subset of the universal element");
        }

        if (i2.hasNext())
            throw new AlgorithmException("The complemented element is not a subset of the universal element");

        while (i1.hasNext())
            res.add((FieldCell) i1.next());

        return res.isEmpty() ? Element.EMPTY : new Element(res);
    }

    /**
     * Returns a shift of this <code>Element</code> on specified number of rows and columns.
     * 
     * @param dr
     *            number of shifted rows.
     * @param dc
     *            number of shifted columns.
     * @return the <code>Element</code> that is a shift of this one.
     */
    public Element shift(int dr, int dc)
    {
        LinkedList res = new LinkedList();

        boolean ok = true;
        for (Iterator i = cells.iterator(); i.hasNext();)
        {
            FieldCell cell = (FieldCell) i.next();
            res.add(new FieldCell(dr + cell.getRow(), dc + cell.getColumn()));
        }

        return res.isEmpty() ? Element.EMPTY : new Element(res);
    }

    /**
     * Returns a string representation of this <code>Element</code>, containing the String
     * representation of each cell.
     * 
     * @return a string representation of this <code>Element</code>.
     */
    public String toString()
    {
        if (stringRepresentation == null)
        {
            StringBuffer sb = new StringBuffer();
            sb.append("{");

            for (Iterator i = cells.iterator(); i.hasNext();)
                sb.append(i.next());

            sb.append("}");
            stringRepresentation = sb.toString();
        }
        return stringRepresentation;
    }

    /**
     * Compares two <code>Element</code> s for lexicographic ordering.
     * 
     * @param arg0
     *            <code>Element</code> to be compared.
     * @return the value <code>0</code> if the argument <code>Element</code> is equal to this
     *         <code>Element</code>; a value less than <code>0</code> if this
     *         <code>Element</code> is before the <code>Element</code> argument; and a value
     *         greater than <code>0</code> if this <code>Element</code> is after the
     *         <code>Element</code> argument.
     */
    public int compareTo(Object arg0)
    {
        if (arg0 instanceof Element)
        {
            LinkedList res = new LinkedList();

            ListIterator i1 = cells.listIterator();
            ListIterator i2 = ((Element) arg0).getCells().listIterator();
            while (i1.hasNext() && i2.hasNext())
            {
                FieldCell c1 = (FieldCell) i1.next();
                FieldCell c2 = (FieldCell) i2.next();

                int z = c1.compareTo(c2);
                if (z != 0)
                    return z;
            }

            if (i2.hasNext())
                return -1;
            else if (i1.hasNext())
                return +1;
            else
                return 0;
        }
        else
            throw new ClassCastException();

    }

    /**
     * Determines whetere this <code>Element</code> contains a specified <code>FieldCell</code>
     * 
     * @param c
     *            the subelement to verify.
     * @return true if this <code>Element</code> contains the cell and false otherwise.
     */
    public boolean containsCell(FieldCell c)
    {
        ListIterator i = cells.listIterator();
        while (i.hasNext())
            if (c.equals(i.next()))
                return true;

        return false;
    }

    /**
     * Determines whetere this <code>Element</code> contains a specified <code>Element</code> as
     * a subset.
     * 
     * @param c
     *            the subelement to verify.
     * @return true if this <code>Element</code> a specified <code>Element</code> and false
     *         otherwise.
     */
    public boolean containsElement(Element e)
    {
        if (e.size() > size())
            return false;

        ListIterator i1 = cells.listIterator();
        ListIterator i2 = e.getCells().listIterator();
        while (i1.hasNext() && i2.hasNext())
        {
            FieldCell c1 = (FieldCell) i1.next();
            FieldCell c2 = (FieldCell) i2.next();

            int z = c1.compareTo(c2);
            if (z < 0)
                i2.previous();
            else if (z > 0)
                return false;
        }

        if (i2.hasNext())
            return false;

        return true;
    }
}