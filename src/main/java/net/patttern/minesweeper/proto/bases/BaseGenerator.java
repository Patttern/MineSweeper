package net.patttern.minesweeper.proto.bases;

import net.patttern.minesweeper.proto.interfaces.Cell;
import net.patttern.minesweeper.proto.interfaces.Generator;

/**
 * Created by ebabenko on 28.08.15.
 */
public abstract class BaseGenerator implements Generator {
  private final int[][] minedArea = new int[][]{
    {-1,  0, -2,  0},// top
    { 0,  1,  0,  2}, // right
    { 1,  0,  2,  0}, // bottom
    { 0, -1,  0, -2},// left
    { 0, -1,  0,  1}, // 1 left and 1 right
    {-1,  0,  1,  0}  // 1 top and 1 bottom
  };
  protected int linesOnArea;
  protected int placesInLine;
  protected Cell[][] cells;

  @Override
  public void prepare(int linesOnArea, int placesInLine) {
    this.linesOnArea = linesOnArea;
    this.placesInLine = placesInLine;
    cells = new Cell[linesOnArea][placesInLine];
  }

  @Override
  abstract public Cell[][] generate();

  @Override
  abstract public int[] makeMine(int mineCount);

  /**
   * Проверка, можно ли установить мину в ячейку.
   * Установить мину можно, если ближняя ячейка не мина или дальняя ячейка не мина.
   * [1][2][2][1]
   * [2][*][*][2]
   * [2][*][*][2]
   * [1][2][2][1]
   * @param line Ряд.
   * @param place Место.
   * @return TRUE, если можно установить мину в ячейку, FALSE в ином случае.
   */
  protected boolean canMined(int line, int place) {
    boolean can = !cellMined(line, place);
    if (can) {
      for (int m = 0; m < minedArea.length; m++) {
        can = can && (!cellMined(line + minedArea[m][0], place + minedArea[m][1]) || !cellMined(line + minedArea[m][2], place + minedArea[m][3]));
      }
    }
    return can;
  }

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
}
