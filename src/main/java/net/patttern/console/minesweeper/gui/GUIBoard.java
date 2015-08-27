package net.patttern.console.minesweeper.gui;

import net.patttern.console.minesweeper.proto.interfaces.Area;
import net.patttern.console.minesweeper.proto.Cell;

import javax.swing.*;
import java.awt.*;

/**
 * Created by pattern on 26.08.15.
 */
public class GUIBoard extends JPanel implements Area {
  public static final int PADDING = 41;
  public Cell<Graphics>[][] cells;

  @Override
  protected void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);
    if (this.cells != null) {
      for (int x = 0; x < cells.length; x++) {
        for (int y = 0; y < cells[0].length; y++) {
          graphics.setColor(Color.cyan);
          cells[x][y].draw(graphics, ((x + y) % 2 == 0));
          graphics.drawRect(x * PADDING, y * PADDING, PADDING, PADDING);
        }
      }
    }
  }

  @Override
  public void setAreaSize(int width, int height) {

  }

  @Override
  public void drawBoard(Cell[][] cells) {
    this.cells = cells;
    this.repaint();
  }

  @Override
  public void drawCell(int x, int y) {
    this.repaint();
  }

  @Override
  public void drawBang() {
    this.repaint();
  }

  @Override
  public void drawCongratulate() {

  }
}
