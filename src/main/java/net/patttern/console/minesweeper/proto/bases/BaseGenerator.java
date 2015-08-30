package net.patttern.console.minesweeper.proto.bases;

import net.patttern.console.minesweeper.proto.interfaces.Cell;
import net.patttern.console.minesweeper.proto.interfaces.Generator;

/**
 * Created by ebabenko on 28.08.15.
 */
public abstract class BaseGenerator implements Generator {
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
    return
      // Ячейка еще не заминирована?
      !cellMined(line, place) &&
        // Top
        // [*]
        // [*]
        // [?]
        (!cellMined(line-1, place) || !cellMined(line-2, place))
        // Right
        // [?][*][*]
        && (!cellMined(line, place+1) || !cellMined(line, place+2))
        // Bottom
        // [?]
        // [*]
        // [*]
        && (!cellMined(line+1, place) || !cellMined(line+2, place))
        // Left
        // [*][*][?]
        && (!cellMined(line, place-1) || !cellMined(line, place-2))
        // Left and Right
        // [*][?][*]
        && (!cellMined(line, place-1) || !cellMined(line, place+1))
        // Top and Bottom
        // [*]
        // [?]
        // [*]
        && (!cellMined(line-1, place) || !cellMined(line+1, place))
      ;
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
    return (cellMined(line-1, place) ? 1 : 0)
      + (cellMined(line-1, place+1) ? 1 : 0)
      + (cellMined(line, place+1) ? 1 : 0)
      + (cellMined(line+1, place+1) ? 1 : 0)
      + (cellMined(line+1, place) ? 1 : 0)
      + (cellMined(line+1, place-1) ? 1 : 0)
      + (cellMined(line, place-1) ? 1 : 0)
      + (cellMined(line-1, place-1) ? 1 : 0);
  }
}
