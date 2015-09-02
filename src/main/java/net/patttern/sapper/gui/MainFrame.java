/**
 * Base dialog.
 * 
 * @author Pestov Oleg
 * @version 1.0
 */

//TODO: add the mode to open all possible cells.
package net.patttern.sapper.gui;

import java.awt.event.*;

import javax.swing.*;

import net.patttern.sapper.strategy.Prediction;
import net.patttern.sapper.strategy.Solver;
import net.patttern.sapper.strategy.op.AlgorithmException;
import net.patttern.sapper.strategy.op.OPSolver;
import net.patttern.sapper.field.FieldInfo;
import net.patttern.sapper.field.GUIField;

public class MainFrame extends JFrame implements MouseListener
{
    /**
     * Default game mode. Mode determines the number of rows, columns and mines.
     */
    private final static int DEFAULT_MODE = 2;

    /**
     * Number of rows in the corresponding mode.
     */
    private final static int[] ROWS = {9, 16, 16};

    /**
     * Number of mines in the corresponding mode.
     */
    private final static int[] COLUMNS = {9, 16, 30};

    /**
     * Number of mines in the corresponding mode.
     */
    private final static int[] MINES = {10, 40, 99};

    /**
     * 'Game' menu structure.
     */
    private JMenuItem[] game = {new JMenuItem("New"), new JRadioButtonMenuItem("Beginer"), new JRadioButtonMenuItem("Intermediate"), new JRadioButtonMenuItem("Expert", true), new JMenuItem("Hint"), new JMenuItem("Exit")};

    /**
     * 'Help' menu structure.
     */
    private JMenuItem[] help = {new JMenuItem("About")};

    /**
     * Mnemonics for 'Game' menu.
     */
    private int[] gameMnemonics = {KeyEvent.VK_N, KeyEvent.VK_B, KeyEvent.VK_I, KeyEvent.VK_E, KeyEvent.VK_H, KeyEvent.VK_X};

    /**
     * Mnemonics for 'Help' menu.
     */
    private int[] helpMnemonics = {KeyEvent.VK_A};

    /**
     * Underlying field.
     */
    private GUIField field;

    /**
     * Read-only adapter for the field.
     */
    private FieldInfo fieldInfo;

    /**
     * Object to automatic calculation of the next turn.
     */
    private Solver solver;

    /**
     * GUI panel for painting the minesweeper field.
     */
    private FieldPanel fieldPanel;

    /**
     * Current game mode.
     */
    private int mode;

    /**
     * Number of opened cells.
     */
    private int opened;

    /**
     * The flag that indicates that the game is over.
     */
    private boolean isOver;

    /**
     * The flag that indicates that the field was opened successfully.
     */
    private boolean isWin;

    /**
     * Creates a new <code>MainFrame</code>, with a random generated field
     * and <code>OPSolver</code> solver.
     */
    public MainFrame()
    {
        super("Tips minesweeper");

        fieldPanel = new FieldPanel(fieldInfo);
        fieldPanel.addMouseListener(this);

        mode = 2;
        init(mode);

        solver = new OPSolver();

        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenu helpMenu = new JMenu("Help");

        menuBar.add(gameMenu);
        menuBar.add(helpMenu);

        for (int i = 0; i < game.length; i++)
        {
            gameMenu.add(game[i]);
            if (i == 0 || i == 3 || i == 4)
                gameMenu.addSeparator();
        }
        for (int i = 0; i < help.length; i++)
            helpMenu.add(help[i]);

        gameMenu.setMnemonic(KeyEvent.VK_G);
        helpMenu.setMnemonic(KeyEvent.VK_H);
        for (int i = 0; i < game.length; i++)
            game[i].setMnemonic(gameMnemonics[i]);
        for (int i = 0; i < help.length; i++)
            help[i].setMnemonic(helpMnemonics[i]);

        ButtonGroup bGroup = new ButtonGroup();
        for (int i = 1; i < 4; i++)
            bGroup.add(game[i]);

        game[0].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
        game[0].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                init(mode);
                fieldPanel.repaint();
            }
        });

        game[4].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
        game[4].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    if (!isOver)
                    {
                        Prediction p = solver.predict(fieldInfo);
                        if (p.isExactly() == true)
                            fieldPanel.setPrediction(p);
                        else
                        {
                            fieldPanel.setPrediction(null);
                            showIDontKnow();
                        }
                    }
                }
                catch (AlgorithmException ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        help[0].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                showAboutDialog();
            }
        });

        class SelectListener implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                int i = 1;
                while (e.getSource() != game[i])
                    i++;

                mode = i - 1;
                init(mode);
                fieldPanel.repaint();
            }
        }
        SelectListener selectListener = new SelectListener();
        for (int i = 1; i < 4; i++)
            game[i].addActionListener(selectListener);

        setJMenuBar(menuBar);

        getContentPane().add(fieldPanel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        pack();
    }

    /**
     * Shows 'I don't know message'.
     */
    private void showIDontKnow()
    {
        JOptionPane.showMessageDialog(this, "I don't know!", "Prediction", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Shows about dialog.
     */
    private void showAboutDialog()
    {
        JOptionPane.showMessageDialog(this, "Tips minesweeper. Pestov Oleg, 2005", "About", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Initializes a new game.
     * 
     * @param mode
     *            game mode.
     */
    private void init(int mode)
    {
        init(ROWS[mode], COLUMNS[mode], MINES[mode]);
    }

    /**
     * Initializes a new game with a random generated field.
     * 
     * @param rowCount
     *            number of rows in the field.
     * @param colCount
     *            nubmer of columns in the field.
     * @param minesCount
     *            number of mines in the field.
     */
    private void init(int rowCount, int colCount, int minesCount)
    {
        field = new GUIField(rowCount, colCount, minesCount);
        fieldInfo = new FieldInfo(field);

        isOver = false;
        isWin = false;
        opened = 0;

        fieldPanel.setGUIFieldInfo(fieldInfo);
        fieldPanel.repaint();
        pack();
    }

    /**
     * Initializes a new game with a specified field.
     * 
     * @param x
     *            field to play.
     */
    private void init(boolean[][] x)
    {
        field = new GUIField(x);
        fieldInfo = new FieldInfo(field);

        isOver = false;
        isWin = false;
        opened = 0;

        fieldPanel.setGUIFieldInfo(fieldInfo);
        fieldPanel.setPrediction(null);
        pack();
    }

    /**
     * Opens cell at the specified position. If the opened cell is empty and
     * there are no mines around it then automatically calls
     * <code>openCell(int, int)</code> for all neighbors.
     * 
     * @param row
     *            row index of the cell.
     * @param col
     *            column index of the cell.
     */
    private void openCell(int row, int col)
    {
        if (row < 0 || row >= field.getRowsCount() || col < 0 || col >= field.getColumnsCount())
            return;

        if (field.getCellState(row, col) != GUIField.MINE_MARK && field.getCellState(row, col) != GUIField.UNKNOWN)
            return;

        int res = field.openCell(row, col);
        if (res == GUIField.MINE)
            isOver = true;
        else
        {
            opened++;
            if (opened == field.getRowsCount() * field.getColumnsCount() - field.getMinesCount())
            {
                isOver = true;
                isWin = true;
            }

            if (res == 0)
                for (int dr = -1; dr <= 1; dr++)
                    for (int dc = -1; dc <= 1; dc++)
                        if (dr * dr + dc * dc != 0)
                            openCell(row + dr, col + dc);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseClicked(MouseEvent e)
    {
        if (isOver)
            return;

        int row = e.getY() / FieldPanel.SIZE;
        int col = e.getX() / FieldPanel.SIZE;

        if (field.getCellState(row, col) != GUIField.MINE_MARK && field.getCellState(row, col) != GUIField.UNKNOWN)
            return;

        if (SwingUtilities.isLeftMouseButton(e))
            openCell(row, col);
        else if (SwingUtilities.isRightMouseButton(e))
            field.markCell(row, col);

        fieldPanel.setPrediction(null);
        fieldPanel.repaint();

        if (isWin)
            JOptionPane.showMessageDialog(this, "You did it!", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
        else if (isOver)
            JOptionPane.showMessageDialog(this, "Oops...May be next time!", "Sorry", JOptionPane.INFORMATION_MESSAGE);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    public void mouseEntered(MouseEvent e)
    {}

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    public void mouseExited(MouseEvent e)
    {}

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed(MouseEvent e)
    {}

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    public void mouseReleased(MouseEvent e)
    {}
}