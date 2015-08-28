package net.patttern.console.minesweeper.proto.bases;

import net.patttern.console.minesweeper.console.CCell;
import net.patttern.console.minesweeper.proto.interfaces.Logic;

/**
 * Created by ebabenko on 28.08.15.
 */
public abstract class BaseLogic<T> implements Logic {
  private int[] mines;
  protected int mineCount;
  protected int placesInLine;
  protected int linesOnArea;
  protected BaseCell<T>[][] cells;

  @Override
  public void start() {
    cells = new BaseCell[linesOnArea][placesInLine];
    //TODO ???
  }

  @Override
  public void selectCell(int line, int place) {
    cells[line][place].select();
  }

  @Override
  public void markCell(int line, int place) {
    cells[line][place].mark();
  }

  @Override
  public boolean finish() {
    return isMinesFound();
  }

  /**
   * Проверка, существует ли ячейка.
   * @param line Ряд.
   * @param place Место.
   * @return TRUE, если ячейка существует, FALSE в ином случае.
   */
  public boolean cellInRange(int line, int place) {
    return line >=0 && line < linesOnArea && place >=0 && place < placesInLine;
  }

  public void setMines(int[] mines) {
    this.mines = mines;
  }

  private boolean isMinesFound() {
    boolean result = true;
    for (int i = 0; i < mines.length; i++) {
      BaseCell<T> cell = getCellById(mines[i]);
      result = result && cell.isMine() && cell.isFlag();
    }
    return result;
  }

  private BaseCell getCellById(int cellId) {
    int line = (int)Math.ceil((cellId - 1) / linesOnArea);
    int place = cellId - 1 - line * placesInLine;
    return cells[line][place];
  }
}
