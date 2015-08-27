package net.patttern.console.minesweeper.console;

import java.util.Random;

/**
 * Created by ebabenko on 27.08.15.
 */
public class CBoard {
  private int mineCount;
  private int rowCells;
  private int boardRows;
  private CCell[][] cells;

  public CBoard(int rowCells, int boardRows, int mineCount) {
    this.rowCells = rowCells;
    this.boardRows = boardRows;
    this.mineCount = mineCount;
    this.cells = new CCell[boardRows][rowCells];
    generate();
    makeMine();
  }

  private void generate() {
    for (int y = 0; y < this.boardRows; y++) {
      for (int x = 0; x < this.rowCells; x++) {
        int id = y * this.rowCells + x + 1;
        this.cells[y][x] = new CCell(id);
        System.out.println("cells[" + y + "][" + x + "]: " + id);
      }
    }
  }

  private void makeMine() {
    Random rand = new Random();
    int mined = 0;
    while (mined < this.mineCount) {
      if (nearBomb() < MAX_NEAR_BOMBS) {
        mined++;
      }
    }
    for (int m = 0; m < this.mineCount; m++) {
      int mineId = rand.nextInt(this.rowCells * this.boardRows) + 1;
      System.out.println("Mine id: " + mineId);

      int row = (int)Math.ceil((mineId - 1) / this.boardRows);
      System.out.println("Row: " + row);
      int cell = mineId - 1 - row * this.rowCells;
      System.out.println("Cell: " + cell);

      System.out.println("Cell id: " + this.cells[row][cell].getId());
    }
  }

  /*
   * Установить мину можно, если:
   * - Дальняя ячейка: если не null или не мина
   * - Ближняя ячейка либо null, либо мина - нельзя
  [1][2][2][1]
  [2][*][*][2]
  [2][*][*][2]
  [1][2][2][1]
   */
  private boolean canSetBomb(int row, int cell) {
    boolean can = true;

    return
      // [*]
      // [*]
      // [?]
      !cellIsBomb(row-1, cell, false) || !cellIsBomb(row-2, cell, true) &&
      !cellIsBomb(row-2, cell, true) && !cellIsBomb(row-1, cell, false)
        //       [*]
        //    [*]
        // [?]
      && !cellIsBomb(row-2, cell+2, true) && !cellIsBomb(row-1, cell+1, false)
        // [?][*][*]
      && !cellIsBomb(row, cell+2, true) && !cellIsBomb(row, cell+1, false)
        // [?]
        //    [*]
        //       [*]
      && !cellIsBomb(row+2, cell+2, true) && !cellIsBomb(row+1, cell+1, false)
        // [?]
        // [*]
        // [*]
      && !cellIsBomb(row+2, cell, true) && !cellIsBomb(row+1, cell, false)
        //       [?]
        //    [*]
        // [*]
      && !cellIsBomb(row+2, cell-2, true) && !cellIsBomb(row+1, cell-1, false)
        // [*][*][?]
      && !cellIsBomb(row, cell-2, true) && !cellIsBomb(row, cell-1, false)
        // [*]
        //    [*]
        //       [?]
      && !cellIsBomb(row-2, cell-2, true) && !cellIsBomb(row-1, cell-1, false);

//    return can;
  }

  private boolean cellExists(int row, int cell) {
    return (row - 2) >= 0
      && (row - 1) >= 0
      && (cell - 2) >= 0
      && (cell - 1) >= 0
      && (row + 2) < boardRows
      && (row - 1) < boardRows
      && (cell + 2) < rowCells
      && (row - 1) < rowCells;
  }

  /**
   * Проверка, установлена ли в ячейке мина.
   * Если ячейка не существует, возвращаем входной параметр result, иначе установлена ли в ячейке мина.
   * @param row
   * @param cell
   * @param result
   * @return
   */
  private boolean cellIsBomb(int row, int cell, boolean result) {
    if (!cellExists(row, cell)) {
      return result;
    } else {
      return this.cells[row][cell].isBomb();
    }
  }

  public void drawBoard() {
  }

  public void drawCell(CCell cell) {
  }

  public void drawBang() {

  }

  public void drawCongratulation() {

  }
}
