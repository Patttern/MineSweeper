package net.patttern.minesweeper.gui;

import net.patttern.minesweeper.modes.Easy;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.FlowLayout;

/**
 * Created by pattern on 30.08.15.
 */
public class GUIRun {
  public static BufferedImage block;
  public static BufferedImage transp;
  public static BufferedImage flag;
  public static BufferedImage mine;
  private static final JPanel controlPanel = new JPanel();
  public static final JButton generate = new JButton("Новая игра");
  private static final GUIArea area = new GUIArea();
  private static int width;
  private static int height;

  public static void main(String[] args) {
    try {
      transp = ImageIO.read(GUIRun.class.getResourceAsStream("/images/cell-transparent.gif"));
      block = ImageIO.read(GUIRun.class.getResourceAsStream("/images/cell-unknown.gif"));
      flag = ImageIO.read(GUIRun.class.getResourceAsStream("/images/cell-mine.gif"));
      mine = ImageIO.read(GUIRun.class.getResourceAsStream("/images/cell-bomb.gif"));
      width = Easy.placesInLine * GUIArea.PADDING + 1;
      height = Easy.linesOnArea * GUIArea.PADDING + 66;
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      System.setProperty("awt.useSystemAAFontSettings", "true");
      System.setProperty("swing.aatext", "true");
    } catch (Exception e) {
      e.printStackTrace();
    }
    SwingUtilities.invokeLater(() -> {
      final JFrame frame = new JFrame();
      frame.setTitle("Сапёр");
      frame.setLayout(new BorderLayout());
      frame.setSize(width, height);
      frame.add(area, BorderLayout.CENTER);
      area.setBorder(new EmptyBorder(10, 10, 10, 10));
      frame.add(controlPanel, BorderLayout.PAGE_END);
      controlPanel.setLayout(new FlowLayout());
      GUIAction action = new GUIAction(new Easy(area, new GUIGenerator()), area);
      area.addMouseListener(action);
      generate.addActionListener(action);
      controlPanel.add(generate);
      frame.setLocationRelativeTo(null);
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

  public static void closePerform(JFrame frame) {
    frame.setVisible(false);
    frame.dispose();
    System.exit(0);
  }
}
