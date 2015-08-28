package net.patttern.console.minesweeper.proto.bases;

import net.patttern.console.minesweeper.proto.interfaces.Generator;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by ebabenko on 28.08.15.
 */
public abstract class BaseGenerator<T> implements Generator {
  private int linesOnArea;
  private int placesInLine;
  private BaseCell<T>[][] cells;

  @Override
  public BaseCell[][] generate() {
    cells = new BaseCell[linesOnArea][placesInLine];
    for (int line = 0; line < linesOnArea; line++) {
      for (int place = 0; place < placesInLine; place++) {
        int cellId = line * placesInLine + place + 1;
        cells[line][place] = new BaseCell<T>(cellId) {
          @Override
          public void draw(T paint) {}

          @Override
          public void draw(T paint, int count) {}
        };
      }
    }
    return cells;
  }

  @Override
  public int[] makeMine(int mineCount) {
    int[] possible = IntStream.rangeClosed(1, placesInLine * linesOnArea).toArray();
    Random rand = new Random();
    int[] result = new int[]{};
    int mined = 0;
    while (possible.length > 0 && mined < mineCount) {
      int cellId = possible[rand.nextInt(possible.length)];
      possible = Arrays.stream(possible).filter(x -> x != cellId).toArray();
      int line = (int)Math.ceil((cellId - 1) / linesOnArea);
      int place = cellId - 1 - line * placesInLine;
      if (canMined(line, place)) {
        cells[line][place].setMine();
        result[mined] = cellId;
        mined++;
        System.out.println("Mined: cells[" + line + "][" + place + "]");
      } else {
        System.out.println("Remined: cells[" + line + "][" + place + "]");
      }
    }
    return result;
  }

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
  private boolean canMined(int line, int place) {
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
  private boolean cellMined(int line, int place) {
    return cellInRange(line, place) ? cells[line][place].isMine() : false;
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
