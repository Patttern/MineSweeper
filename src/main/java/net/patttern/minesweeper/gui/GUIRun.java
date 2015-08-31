package net.patttern.minesweeper.gui;

import net.patttern.minesweeper.modes.Easy;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

/**
 * Created by pattern on 30.08.15.
 */
public class GUIRun {
  public static BufferedImage block;
  public static BufferedImage transp;
  public static BufferedImage flag;
  public static BufferedImage mine;
  private static final JPanel controlPanel = new JPanel();
  private static final GUIArea area = new GUIArea();

  public static void main(String[] args) {
    try {
      transp = ImageIO.read(GUIRun.class.getResourceAsStream("/images/cell-transparent.gif"));
      block = ImageIO.read(GUIRun.class.getResourceAsStream("/images/cell-unknown.gif"));
      flag = ImageIO.read(GUIRun.class.getResourceAsStream("/images/cell-mine.gif"));
      mine = ImageIO.read(GUIRun.class.getResourceAsStream("/images/cell-bomb.gif"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    SwingUtilities.invokeLater(() -> {
      final JFrame frame = new JFrame();
      frame.setTitle("Сапёр");
      frame.setLayout(new BorderLayout());
      frame.setSize(500, 500);
      frame.add(area, BorderLayout.CENTER);
      area.setBorder(new EmptyBorder(10, 10, 10, 10));
      frame.add(controlPanel, BorderLayout.PAGE_END);
      controlPanel.setLayout(new FlowLayout());
      final JButton generate = new JButton("Начать");
      GUIAction action = new GUIAction(new Easy(area, new GUIGenerator()), area);
      area.addMouseListener(action);
      generate.addActionListener(action);
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
