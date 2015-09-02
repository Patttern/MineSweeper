package net.patttern.minesweeper.gui;

import net.patttern.minesweeper.proto.bases.BaseArea;
import net.patttern.minesweeper.proto.interfaces.Area;
import net.patttern.minesweeper.proto.interfaces.Cell;

import javax.swing.*;
import java.awt.*;

/**
 * Created by pattern on 30.08.15.
 */
class GUIArea extends JPanel {
  public static final int PADDING = 41;
  private final Component self = this;

  private final BaseArea area = new BaseArea() {
    @Override
    public void prepare(Cell[][] cells) {
      this.cells = cells;
      linesOnArea = this.cells.length;
      placesInLine = this.cells[0].length;
    }

    @Override
    public void drawBoard() {
      self.repaint();
    }

    @Override
    public void drawBang() {
      JOptionPane.showMessageDialog(null, "<html><p>Вы подорвались на мине и проиграли...<br/>:(</p></html>", "BANG!!!", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void drawCongratulate() {
      JOptionPane.showMessageDialog(null, "<html><p>Вы отличный сапер, так как разминировали все мины!!!<br/>=)</p></html>", "УРА!!!", JOptionPane.ERROR_MESSAGE);
    }
  };

  @Override
  public void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);
    Cell[][] cells = area.getCells();
    if (cells != null) {
      for (int line = 0; line < area.getLinesOnArea(); line++) {
        for (int place = 0; place < area.getPlacesInLine(); place++) {
          ((GUICell)cells[line][place]).draw(graphics, area.nearMinesCount(line, place));
          graphics.setColor(Color.CYAN);
          graphics.drawRect(place * PADDING, line * PADDING, PADDING, PADDING);
        }
      }
    }
  }

  public Area getArea() {
    return area;
  }
}
