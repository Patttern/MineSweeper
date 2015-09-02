package net.patttern.minesweeper.proto.bases;

import net.patttern.minesweeper.proto.interfaces.Cell;
import net.patttern.minesweeper.proto.interfaces.Generator;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by ebabenko on 28.08.15.
 */
public abstract class BaseGenerator implements Generator {
  private final int[][] minedArea = new int[][]{
    {-1,  0, -2,  0}, // top
    { 0,  1,  0,  2}, // right
    { 1,  0,  2,  0}, // bottom
    { 0, -1,  0, -2}, // left
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
  public int[] makeMine(int mineCount) {
    int[] mines = new int[mineCount];
    int[] possible = IntStream.rangeClosed(1, placesInLine * linesOnArea).toArray();
    Random rand = new Random();
    int mined = 0;
    while (possible.length > 0 && mined < mineCount) {
      int cellId = possible[rand.nextInt(possible.length)];
      possible = Arrays.stream(possible).filter(x -> x != cellId).toArray();
      int line = (int)Math.ceil((cellId - 1) / placesInLine);
      int place = cellId - 1 - line * placesInLine;
      if (canMined(line, place)) {
        cells[line][place].setMine();
        mines[mined] = cellId;
        mined++;
        System.out.println("Mined: cells[" + line + "][" + place + "]");
      } else {
        System.out.println("Remined: cells[" + line + "][" + place + "]");
      }
    }
    return mines;
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
    boolean can = !isCellMined(line, place);
    if (can) {
      for (int[] aMinedArea : minedArea) {
        can = can && (!isCellMined(line + aMinedArea[0], place + aMinedArea[1]) || !isCellMined(line + aMinedArea[2], place + aMinedArea[3]));
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
  private boolean isCellMined(int line, int place) {
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
}
