/**
 * GUI panel for painting the minesweeper field.
 * 
 * @author Pestov Oleg
 * @version 1.0
 */

package net.patttern.minesweeper.sapper.gui;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import net.patttern.minesweeper.sapper.strategy.Prediction;
import net.patttern.minesweeper.sapper.field.FieldInfo;
import net.patttern.minesweeper.sapper.field.GUIField;

class FieldPanel extends JPanel
{
    /**
     * Constant that
     */
    private static int RISE = 13;

    /**
     * Length of the the one cell in pixels.
     */
    public static int SIZE = 16;

    /**
     * Colors of the digits, indicating the number of mines in neighbour cells.
     */
    public static final Color[] colors = {Color.LIGHT_GRAY, new Color(0, 0, 255), new Color(0, 164, 0), new Color(255, 0, 0), new Color(0, 0, 128), new Color(128, 0, 0), new Color(0, 128, 128), new Color(0, 0, 0), new Color(128, 128, 128)};

    /**
     * Underlaying field.
     */
    private FieldInfo fieldInfo;

    /**
     * Last autoprediction.
     */
    private Prediction lastPrediction;

    /**
     * The default constructor is unavailable.
     */
    private FieldPanel()
    {}

    /**
     * Creates a new <code>FieldPanel</code> with specified underlaying field.
     * 
     * @param fieldInfo
     *            field to paint.
     */
    public FieldPanel(FieldInfo fieldInfo)
    {
        this.fieldInfo = fieldInfo;
        this.lastPrediction = null;
    }

    /**
     * Sets autoprediction.
     * 
     * @param p
     *            last autoprediction.
     */
    public void setPrediction(Prediction p)
    {
        this.lastPrediction = p;
        this.repaint();
    }

    /**
     * Sets a new field.
     * 
     * @param fieldInfo
     *            new underlaying field.
     */
    public void setGUIFieldInfo(FieldInfo fieldInfo)
    {
        this.fieldInfo = fieldInfo;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.Component#paint(java.awt.Graphics)
     */
    public void paint(Graphics g)
    {
        super.paint(g);

        BufferedImage bufferedImage = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D b = bufferedImage.createGraphics();

        b.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 16));
        b.setBackground(Color.LIGHT_GRAY);
        b.clearRect(0, 0, getSize().width, getSize().height);

        for (int row = 0; row < fieldInfo.getRowsCount(); row++)
            for (int col = 0; col < fieldInfo.getColumnsCount(); col++)
            {
                int state = fieldInfo.getCellState(row, col);
                if (state == GUIField.MINE)
                {
                    b.setColor(Color.RED);
                    b.fill3DRect(col * SIZE + 2, row * SIZE + 2, SIZE - 3, SIZE - 3, true);
                }
                else
                {
                    b.setColor(Color.GRAY);
                    b.drawRect(col * SIZE, row * SIZE, SIZE, SIZE);
                }

                if (state >= 0 && state <= 8)
                {
                    b.setColor(colors[state]);
                    b.drawString("" + state, col * SIZE + 3, row * SIZE + SIZE - 2);
                }
                if (state == GUIField.MINE_MARK)
                {
                    b.setColor(Color.BLACK);
                    b.drawString("*", col * SIZE + 3, row * SIZE + SIZE);
                }
                if (state == GUIField.MINE)
                {
                    b.setColor(Color.BLACK);
                    b.drawString("X", col * SIZE + 4, row * SIZE + SIZE - 2);
                }

            }

        for (int row = 0; row < fieldInfo.getRowsCount(); row++)
            for (int col = 0; col < fieldInfo.getColumnsCount(); col++)
            {
                int state = fieldInfo.getCellState(row, col);
                if (state == GUIField.MINE_MARK || state == GUIField.UNKNOWN)
                {
                    b.setColor(Color.LIGHT_GRAY);
                    b.fill3DRect(col * SIZE + 2, row * SIZE + 2, RISE, RISE, true);

                    if (state == GUIField.MINE_MARK)
                    {
                        b.setColor(Color.BLACK);
                        b.drawString("*", col * SIZE + 3, row * SIZE + SIZE);
                    }
                }

                if (lastPrediction != null && lastPrediction.getCell().getRow() == row && lastPrediction.getCell().getColumn() == col)
                {
                    if (lastPrediction.isExactly())
                        b.setColor(Color.ORANGE);
                    else
                        b.setColor(Color.YELLOW);
                    b.fill3DRect(col * SIZE + 2, row * SIZE + 2, RISE, RISE, true);
                    if (!lastPrediction.isEmptyCell())
                    {
                        b.setColor(Color.BLACK);
                        b.drawString("*", col * SIZE + 3, row * SIZE + SIZE);
                    }
                }
            }
        g.drawImage(bufferedImage, 0, 0, this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.Component#getMinimumSize()
     */
    public Dimension getMinimumSize()
    {
        return new Dimension(SIZE * fieldInfo.getColumnsCount(), SIZE * fieldInfo.getRowsCount());
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.Component#getPreferredSize()
     */
    public Dimension getPreferredSize()
    {
        return new Dimension(SIZE * fieldInfo.getColumnsCount(), SIZE * fieldInfo.getRowsCount());
    }
}