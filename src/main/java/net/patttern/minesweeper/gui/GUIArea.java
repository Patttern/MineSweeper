package net.patttern.minesweeper.gui;

import net.patttern.minesweeper.proto.TempCell;
import net.patttern.minesweeper.proto.interfaces.Area;
import net.patttern.minesweeper.proto.interfaces.Cell;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by pattern on 30.08.15.
 */
class GUIArea extends JPanel implements Area {
  public static final int PADDING = 41;
  private static final int[][] nears = new int[][]{
    {-1,  0}, // n
    {-1,  1}, // ne
    { 0,  1}, // e
    { 1,  1}, // se
    { 1,  0}, // s
    { 1, -1}, // sw
    { 0, -1}, // w
    {-1, -1}  // nw
  };
  private Cell[][] cells;
  private int linesOnArea;
  private int placesInLine;
  private  Queue<TempCell> checkingCells;

  @Override
  public void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);
    if (this.cells != null) {
      for (int line = 0; line < linesOnArea; line++) {
        for (int place = 0; place < placesInLine; place++) {
          cells[line][place].draw(graphics, nearMinesCount(line, place));
          graphics.setColor(Color.CYAN);
          graphics.drawRect(place * PADDING, line * PADDING, PADDING, PADDING);
        }
      }
    }
  }

  @Override
  public void prepare(Cell[][] cells) {
    this.cells = cells;
    linesOnArea = this.cells.length;
    placesInLine = this.cells[0].length;
  }

  @Override
  public void drawBoard() {
    this.repaint();
  }

  @Override
  public void drawBang() {
    JOptionPane.showMessageDialog(null, "<html><p>Вы подорвались на мине и проиграли...<br/>:(</p></html>", "BANG!!!", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void drawCongratulate() {
    JOptionPane.showMessageDialog(null, "<html><p>Вы отличный сапер, так как разминировали все мины!!!<br/>=)</p></html>", "УРА!!!", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Проверка, установлена ли в ячейке мина.
   * @param line Ряд.
   * @param place Место.
   * @return TRUE, если мина установлена в ячейке, FALSE в ином случае.
   */
  private boolean cellMined(int line, int place) {
    return cellInRange(line, place) && cells[line][place].isMine();
  }

  /**
   * Проверка, существует ли ячейка.
   * @param line Ряд.
   * @param place Место.
   * @return TRUE, если ячейка существует, FALSE в ином случае.
   */
  private boolean cellInRange(int line, int place) {
    return line >=0 && line < linesOnArea && place >=0 && place < placesInLine;
  }

  /**
   * Количество заминированных ячеек, находящихся рядом.
   * [*][*][*]
   * [*][?][*]
   * [*][*][*]
   * @param line Ряд.
   * @param place Место.
   * @return Количество заминированных ячеек.
   */
  private int nearMinesCount(int line, int place) {
    int count = 0;
    for (int[] near : nears) {
      count += (cellMined(line + near[0], place + near[1]) ? 1 : 0);
    }
    return count;
  }

  @Override
  public void openAllCells() {
    for (int line = 0; line < linesOnArea; line++) {
      for (int place = 0; place < placesInLine; place++) {
        cells[line][place].select();
      }
    }
    drawBoard();
  }

  @Override
  public void openCell(int line, int place) {
    checkingCells = new LinkedList<>();
    checkingCells.add(new TempCell(cells[line][place].getId(), line, place));
    while (!checkingCells.isEmpty()) {
      TempCell tmp = checkingCells.remove();
      tmp.setNearMines(nearMinesCount(tmp.getLine(), tmp.getPlace()));
      if (tmp.getNearMines() == 0) {
        checkCells(tmp.getLine(), tmp.getPlace());
      }
      cells[tmp.getLine()][tmp.getPlace()].select();
    }
    drawBoard();
  }

  private void checkCells(int line, int place) {
    for (int[] near : nears) {
      int l = line + near[0];
      int p = place + near[1];
      if (cellInRange(l, p)) {
        TempCell tmp = new TempCell(cells[l][p].getId(), l, p);
        if (!checkingCells.contains(tmp) && !cells[l][p].isFlag() && !cells[l][p].isSelected()) {
          checkingCells.add(tmp);
        }
      }
    }
  }
}
