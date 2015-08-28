package net.patttern.console.minesweeper;

import net.patttern.console.minesweeper.gui.GUIAction;
import net.patttern.console.minesweeper.gui.GUIBoard;
import net.patttern.console.minesweeper.gui.GUICell;
import net.patttern.console.minesweeper.modes.EasyOld;
import net.patttern.console.minesweeper.proto.Cell;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

/**
 * Created by pattern on 26.08.15.
 */
public class Main {
  private static final JPanel controlPanel = new JPanel();
  private static final GUIBoard board = new GUIBoard();
  public static BufferedImage block;
  public static BufferedImage transp;
  public static BufferedImage flag;
  public static BufferedImage mine;

  public static void main(String[] args) {
    try {
      transp = ImageIO.read(Main.class.getResourceAsStream("/images/cell-transparent.gif"));
      block = ImageIO.read(Main.class.getResourceAsStream("/images/cell-unknown.gif"));
      flag = ImageIO.read(Main.class.getResourceAsStream("/images/cell-mine.gif"));
      mine = ImageIO.read(Main.class.getResourceAsStream("/images/cell-bomb.gif"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    SwingUtilities.invokeLater(() -> {
      final JFrame frame = new JFrame();
      frame.setTitle("Сапёр");
      frame.setLayout(new BorderLayout());
      frame.setSize(500, 500);
      frame.add(board, BorderLayout.CENTER);
      board.setBorder(new EmptyBorder(10, 10, 10, 10));
      frame.add(controlPanel, BorderLayout.PAGE_END);
      controlPanel.setLayout(new FlowLayout());
      final JButton generate = new JButton("Начать");
      EasyOld easy = new EasyOld();
      generate.addActionListener(
        new GUIAction(
          easy,
          board,
          () -> {
            int w = easy.getBoardWidth();
            int h = easy.getBoardHeight();
            Cell[][] cells = new Cell[w][h];
            for (int x = 0; x < cells.length; x++) {
              for (int y = 0; y < cells[0].length; y++) {
                cells[x][y] = new GUICell(x, y);
              }
            }
            return cells;
          }
        )
      );
      controlPanel.add(generate);
      centre(frame);
      frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      frame.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
          closePerform(frame);
        }
      });
      frame.setVisible(true);
    });
  }

  public static void centre(Window w) {
    Dimension me = w.getSize();
    Dimension them = Toolkit.getDefaultToolkit().getScreenSize();
    int newX = (them.width - me.width) / 2;
    int newY = (them.height - me.height) / 2;
    w.setLocation(newX, newY);
  }

  public static void closePerform(JFrame frame) {
    frame.setVisible(false);
    frame.dispose();
    System.exit(0);
  }
}
