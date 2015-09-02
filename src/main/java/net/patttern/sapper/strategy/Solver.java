/**
 * <code>Solver</code> discovers a new empty cell or mine.
 * 
 * @author Pestov Oleg
 * @version 1.0
 */

package net.patttern.sapper.strategy;

import net.patttern.sapper.strategy.op.AlgorithmException;
import net.patttern.sapper.field.FieldInfo;

public interface Solver
{
    /**
     * Predicts a new mine or a new cell to open.
     * 
     * @param field
     *            the field to calculate next empty cell o mine.
     * @return prediction with a new mine or a new empty cell.
     * @throws AlgorithmException
     *             if the underlaying algorithm performs incorrect actions.
     */
    public Prediction predict(FieldInfo field) throws AlgorithmException;
}