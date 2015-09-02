/**
 * <code>Solver</code> that is based on cellular automatons. Cell is
 * considedred as element with a specified state. Each element is a set of sets.
 * All cells in the element should be neighbors of already opened empty cell
 * (one for the all set). This opened empty cell is called base.
 */

package net.patttern.sapper.strategy.op;

import java.util.*;

import net.patttern.sapper.field.FieldInfo;
import net.patttern.sapper.field.GUIField;
import net.patttern.sapper.strategy.Prediction;
import net.patttern.sapper.strategy.Solver;
import net.patttern.sapper.field.FieldCell;

public class OPSolver implements Solver
{
    /**
     * Row shifts. Along with <code>dc</code> specifies all possible relative
     * position of the cells with respect to the base.
     */
    public static final int dr[] = {-1, -1, -1, 0, 1, 1, 1, 0};

    /**
     * Column shifts. Along with <code>dr</code> specifies all possible
     * relative position of the cells with respect to the base.
     */
    public static final int dc[] = {-1, 0, 1, 1, 1, 0, -1, -1};

    /**
     * List of the elements with the base (0,0).
     */
    public static List elementsAround00;
    static
    {
        HashSet s = new HashSet();
        LinkedList a = new LinkedList();
        for (int mask = 0; mask < 256; mask++)
        {
            a.clear();
            for (int i = 0; i < dr.length; i++)
                if ((mask & (1 << i)) != 0)
                    a.add(new FieldCell(dr[i], dc[i]));
            if (!a.isEmpty())
                s.add(new Element(a));
        }

        LinkedList x = new LinkedList(s);
        Collections.sort(x);
        elementsAround00 = Collections.unmodifiableList(x);
    }

    /**
     * List of the elements, containing the cell (0,0).
     */
    public static List cell00ParentElements;
    static
    {
        HashSet s = new HashSet();
        FieldCell origin = new FieldCell(0, 0);
        for (int r = -1; r <= 1; r++)
            for (int c = -1; c <= 1; c++)
                for (Iterator i = elementsAround00.iterator(); i.hasNext();)
                {
                    Element e = ((Element) i.next()).shift(r, c);
                    if (e.containsCell(origin))
                        s.add(e);
                }

        LinkedList y = new LinkedList(s);
        Collections.sort(y);
        cell00ParentElements = Collections.unmodifiableList(y);
    }

    /**
     * Map that associates each element around the base (0,0) with the list of
     * the elements that are contained in the given element.
     */
    public static Map inner00Neighbors;
    /**
     * Map that associates each element around the base (0,0) with the list of
     * the elements that contains the given element.
     */
    public static Map outer00Neighbors;
    static
    {
        HashMap m = new HashMap();
        LinkedList l = new LinkedList();
        for (Iterator i = elementsAround00.iterator(); i.hasNext();)
        {
            Element a = (Element) i.next();

            l.clear();
            for (Iterator j = elementsAround00.iterator(); j.hasNext();)
            {
                Element b = (Element) j.next();
                if (!a.equals(b) && a.containsElement(b))
                    l.add(b);
            }

            Collections.sort(l);
            m.put(a, Collections.unmodifiableList(new LinkedList(l)));
        }
        inner00Neighbors = Collections.unmodifiableMap(new HashMap(m));

        m.clear();
        for (Iterator i = elementsAround00.iterator(); i.hasNext();)
        {
            Element a = (Element) i.next();
            FieldCell c = (FieldCell) a.getCells().get(0);

            l.clear();
            for (Iterator j = cell00ParentElements.iterator(); j.hasNext();)
            {
                Element b = ((Element) j.next()).shift(c.getRow(), c.getColumn());
                if (!a.equals(b) && b.containsElement(a))
                    l.add(b);
            }
            Collections.sort(l);
            m.put(a, Collections.unmodifiableList(new LinkedList(l)));
        }
        outer00Neighbors = Collections.unmodifiableMap(new HashMap(m));
    }

    /**
     * Field where prediction shoud be calculated.  
     */
    private FieldInfo field;

    /**
     * Map that associate each <code>Element</code> with the appropriate
     * <code>State</code>.
     */
    private HashMap elementsState;

    /**
     * Map that associates each element with the list of the elements that
     * contains the given element.
     */
    private HashMap outerNeighbors;

    /**
     * Map that associates each element with the list of the elements that are
     * contained in the given element.
     */
    private HashMap innerNeighbors;

    /**
     * Set of the elements that changed their own state and should be processed.
     */
    private HashSet touchedElements;

    /**
     * The discovered mines.
     */
    private boolean mines[][];

    /**
     * The number of discovered mines around the each cell.
     */
    private int minesAround[][];

    /**
     * The number of empty cells around the each cell.
     */
    private int freeAround[][];

    /**
     * Determines whether the given cell is located inside of the field.
     * 
     * @param cell
     *            the cell to check.
     * @return <code>true</code> if the specified cell is located inside of
     *         the field and <code>false</code> otherwise.
     */
    private boolean correctCell(FieldCell cell)
    {
        int r = cell.getRow();
        int c = cell.getColumn();

        return (r >= 0 && r < field.getRowsCount() && c >= 0 && c < field.getColumnsCount());
    }

    /**
     * Determines whether the given cell is located inside of the field.
     * 
     * @param row
     *            row index of the cell to check.
     * @param col
     *            column index of the cell to check.
     * @return <code>true</code> if the specified cell is located inside of
     *         the field and <code>false</code> otherwise.
     */
    private boolean correctCell(int row, int col)
    {
        return (row >= 0 && row < field.getRowsCount() && col >= 0 && col < field.getColumnsCount());
    }

    /**
     * Determines whether the given cell is not open.
     * 
     * @param cell
     *            the cell to check.
     * @return <code>true</code> if the specified cell is not open
     *         <code>false</code> otherwise.
     */
    private boolean unknownCell(FieldCell cell)
    {
        int state = field.getCellState(cell);
        return (state == GUIField.MINE_MARK || state == GUIField.UNKNOWN);
    }

    /**
     * Determines whether the given cell is not open.
     * 
     * @param row
     *            row index of the cell to check.
     * @param col
     *            column index of the cell to check.
     * @return <code>true</code> if the specified cell is not open
     *         <code>false</code> otherwise.
     */
    private boolean unknownCell(int row, int col)
    {
        int state = field.getCellState(row, col);
        return (state == GUIField.MINE_MARK || state == GUIField.UNKNOWN);
    }

    /**
     * Determines whether the given element is correct. Another words whether
     * the each cell of the specified element is correct and not open.
     * 
     * @param e
     *            the element to check.
     * @return <code>true</code> if the specified element is correct and
     *         <code>false</code> otherwise.
     */
    private boolean correctElement(Element e)
    {
        for (Iterator i = e.getCells().iterator(); i.hasNext();)
        {
            FieldCell cell = (FieldCell) i.next();
            if (!correctCell(cell) || !unknownCell(cell))
                return false;
        }
        return true;
    }

    /**
     * Removes the specified element.
     * 
     * @param e
     *            element to remove.
     */
    private void removeElement(Element e)
    {
        elementsState.remove(e);
        touchedElements.remove(e);

        if (innerNeighbors.containsKey(e))
        {
            for (Iterator i = ((HashSet) innerNeighbors.get(e)).iterator(); i.hasNext();)
            {
                Element o = (Element) i.next();
                ((HashSet) outerNeighbors.get(o)).remove(e);
            }
            innerNeighbors.remove(e);
        }
        if (outerNeighbors.containsKey(e))
        {
            for (Iterator i = ((HashSet) outerNeighbors.get(e)).iterator(); i.hasNext();)
            {
                Element o = (Element) i.next();
                ((HashSet) innerNeighbors.get(o)).remove(e);
            }
            outerNeighbors.remove(e);
        }
    }

    /**
     * Removes the specified cell. It attracts the removing of some elements and
     * recalculation of state for some other elements.
     * 
     * @param cell
     *            cell to remove
     * @throws AlgorithmException
     *             if the algorithm performs incorrect actions.
     */
    private void removeCell(FieldCell cell) throws AlgorithmException
    {
        mines[cell.getRow()][cell.getColumn()] = true;
        for (Iterator i = cell00ParentElements.iterator(); i.hasNext();)
        {
            Element e = ((Element) i.next()).shift(cell.getRow(), cell.getColumn());
            removeElement(e);
        }

        int r = cell.getRow();
        int c = cell.getColumn();
        for (int k = 0; k < dr.length; k++)
            if (correctCell(r + dr[k], c + dc[k]))
            {
                int nr = r + dr[k];
                int nc = c + dc[k];
                minesAround[nr][nc]++;
                freeAround[nr][nc]--;

                int state = field.getCellState(nr, nc);
                if (state >= 0 && state <= 8)
                    for (Iterator i = elementsAround00.iterator(); i.hasNext();)
                    {
                        Element e = ((Element) i.next()).shift(nr, nc);
                        State eState = (State) elementsState.get(e);
                        if (eState != null)
                        {
                            if (eState.setMin(state - minesAround[nr][nc] - (freeAround[nr][nc] - e.size())))
                                touchedElements.add(e);
                            if (eState.setMax(state - minesAround[nr][nc]))
                                touchedElements.add(e);
                        }
                    }
            }
    }

    /**
     * Determines the correct elements for the current field state and
     * calculates their state.
     * 
     * @throws AlgorithmException
     *             if the algorithm performs incorrect actions.
     */
    private void init() throws AlgorithmException
    {
        mines = new boolean[field.getRowsCount()][field.getColumnsCount()];
        minesAround = new int[field.getRowsCount()][field.getColumnsCount()];
        freeAround = new int[field.getRowsCount()][field.getColumnsCount()];

        for (int r = 0; r < field.getRowsCount(); r++)
            for (int c = 0; c < field.getColumnsCount(); c++)
                for (int k = 0; k < dr.length; k++)
                    if (correctCell(r + dr[k], c + dc[k]) && unknownCell(r + dr[k], c + dc[k]))
                        freeAround[r][c]++;

        touchedElements = new HashSet();

        elementsState = new HashMap();
        innerNeighbors = new HashMap();
        outerNeighbors = new HashMap();

        // iterates over all possible bases
        for (int r = 0; r < field.getRowsCount(); r++)
            for (int c = 0; c < field.getColumnsCount(); c++)
            {
                int state = field.getCellState(r, c);
                if (state >= 0 && state <= 8) // the cell is empty
                {
                    // iterates over all possible elements around the cell (0,0)
                    for (Iterator i = elementsAround00.iterator(); i.hasNext();)
                    {
                        Element z = (Element) i.next();
                        Element e = z.shift(r, c);
                        if (!correctElement(e))
                            continue;

                        if (!elementsState.containsKey(e))
                        {
                            elementsState.put(e, new State(0, e.size()));
                            touchedElements.add(e);

                            innerNeighbors.put(e, new HashSet());
                            outerNeighbors.put(e, new HashSet());

                            for (Iterator j = ((List) inner00Neighbors.get(z)).iterator(); j.hasNext();)
                            {
                                Element x = ((Element) j.next()).shift(r, c);
                                if (correctElement(x) && elementsState.containsKey(x))
                                {
                                    ((HashSet) innerNeighbors.get(e)).add(x);
                                    ((HashSet) outerNeighbors.get(x)).add(e);
                                }
                            }
                            for (Iterator j = ((List) outer00Neighbors.get(z)).iterator(); j.hasNext();)
                            {
                                Element x = ((Element) j.next()).shift(r, c);
                                if (correctElement(x) && elementsState.containsKey(x))
                                {
                                    ((HashSet) outerNeighbors.get(e)).add(x);
                                    ((HashSet) innerNeighbors.get(x)).add(e);
                                }
                            }
                        }

                        State s = (State) elementsState.get(e);
                        s.setMin(state - (freeAround[r][c] - e.size()));
                        s.setMax(state);
                    }
                }
            }
    }

    /**
     * Recalculates the state of the element <code>b</code>.
     * 
     * @param a
     *            element A.
     * @param b
     *            element B. A and B should be disjoint.
     * @param c
     *            element C. C should contains both A and B.
     * @throws AlgorithmException
     *             if the algorithm performs incorrect actions.
     */
    private void changeState(Element a, Element b, Element c) throws AlgorithmException
    {
        State aState = (State) elementsState.get(a);
        State bState = (State) elementsState.get(b);
        State cState = (State) elementsState.get(c);

        if (bState.setMin(cState.getMin() - aState.getMax()) || bState.setMax(cState.getMax() - aState.getMin()))
            touchedElements.add(b);
    }

    /**
     * Recalculates the state of the elements that are neighbors to the
     * specified one.
     * 
     * @param e
     *            element to process.
     * @throws AlgorithmException
     *             if the algorithm performs incorrect actions.
     */
    private void processElement(Element e) throws AlgorithmException
    {
        Element o;
        for (Iterator i = ((HashSet) innerNeighbors.get(e)).iterator(); i.hasNext();)
        {
            o = (Element) i.next();
            changeState(o, e.complement(o), e);
        }

        for (Iterator i = ((HashSet) outerNeighbors.get(e)).iterator(); i.hasNext();)
        {
            o = (Element) i.next();
            changeState(e, o.complement(e), o);
        }
    }

    /**
     * Calculates the element that consists only of mines or only of empty
     * cells.
     * 
     * @return the element that consists only of mines or empty cells or null if
     *         the algorithm couldn't retrieve new information.
     * @throws AlgorithmException
     *             if the algorithm performs incorrect actions.
     */
    public Element predict() throws AlgorithmException
    {
        while (!touchedElements.isEmpty())
        {
            Element e = (Element) touchedElements.iterator().next();
            touchedElements.remove(e);

            State s = (State) elementsState.get(e);
            // all cells in this element are mines.
            if (s.getMin() == s.getMax() && s.getMin() == e.size())
                return e;
            // all cells in this element are empty.
            else if (s.getMax() == 0)
                return e;
            else
                processElement(e);
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see strategy.Solver#predict(field.FieldInfo)
     */
    public Prediction predict(FieldInfo field) throws AlgorithmException
    {
        this.field = field;
        init();

        Element e = predict();
        while (e != null)
        {
            State s = (State) elementsState.get(e);
            if (s.getMin() == s.getMax() && s.getMin() == e.size())
            {
                for (Iterator i = e.getCells().iterator(); i.hasNext();)
                    removeCell((FieldCell) i.next());
            }
            else if (s.getMax() == 0)
            {
                return new Prediction((FieldCell) e.getCells().iterator().next(), true, true);
            }
            e = predict();
        }

        return new Prediction(new FieldCell(0, 0), true, false);
    }
}