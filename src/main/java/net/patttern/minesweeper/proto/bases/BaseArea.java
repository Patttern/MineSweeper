package net.patttern.minesweeper.proto.bases;

import net.patttern.minesweeper.proto.TempCell;
import net.patttern.minesweeper.proto.interfaces.Area;
import net.patttern.minesweeper.proto.interfaces.Cell;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by pattern on 30.08.15.
 */
public abstract class BaseArea implements Area {
  public static final int[][] nears = new int[][]{
    {-1,  0}, // n
    {-1,  1}, // ne
    { 0,  1}, // e
    { 1,  1}, // se
    { 1,  0}, // s
    { 1, -1}, // sw
    { 0, -1}, // w
    {-1, -1}  // nw
  };
  protected Cell[][] cells;
  protected int linesOnArea;
  protected int placesInLine;
  private Queue<TempCell> chekingCells;

  @Override
  abstract public void prepare(Cell[][] cells);

  @Override
  abstract public void drawBoard();

  @Override
  abstract public void drawBang();

  @Override
  abstract public void drawCongratulate();

  /**
   * Проверка, установлена ли в ячейке мина.
   * @param line Ряд.
   * @param place Место.
   * @return TRUE, если мина установлена в ячейке, FALSE в ином случае.
   */
  protected boolean cellMined(int line, int place) {
    return cellInRange(line, place) ? cells[line][place].isMine() : false;
  }

  /**
   * Проверка, существует ли ячейка.
   * @param line Ряд.
   * @param place Место.
   * @return TRUE, если ячейка существует, FALSE в ином случае.
   */
  protected boolean cellInRange(int line, int place) {
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
  protected int nearMinesCount(int line, int place) {
    int count = 1;
    for (int n = 0; n < nears.length; n++) {
      count += (cellMined(line + nears[n][0], place + nears[n][1]) ? 1 : 0);
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
    chekingCells = new LinkedList<TempCell>();
    chekingCells.add(new TempCell(cells[line][place].getId(), line, place));
    while (!chekingCells.isEmpty()) {
      TempCell tmp = chekingCells.remove();
      tmp.setNearMines(nearMinesCount(tmp.getLine(), tmp.getPlace()));
      if (tmp.getNearMines() == 0) {
        checkCells(tmp.getLine(), tmp.getPlace());
      }
      cells[tmp.getLine()][tmp.getPlace()].select();
    }
    drawBoard();
  }

  private void checkCells(int line, int place) {
    for (int n = 0; n < nears.length; n++) {
      int l = line + nears[n][0];
      int p = place + nears[n][1];
      if (cellInRange(l, p)) {
        TempCell tmp = new TempCell(cells[l][p].getId(), l, p);
        if (!chekingCells.contains(tmp)) {
          chekingCells.add(tmp);
        }
      }
    }
  }
}
