package net.patttern.console.minesweeper.proto.bases;

import net.patttern.console.minesweeper.proto.interfaces.Area;
import net.patttern.console.minesweeper.proto.interfaces.Cell;
import net.patttern.console.minesweeper.proto.interfaces.Generator;
import net.patttern.console.minesweeper.proto.interfaces.Logic;

/**
 * Created by ebabenko on 28.08.15.
 */
public abstract class BaseLogic implements Logic {
  protected Area area;
  protected Generator generator;
  protected Cell[][] cells;
  protected int[] mines;

  public BaseLogic(Area area, Generator generator) {
    this.area = area;
    this.generator = generator;
  }

  @Override
  abstract public void start();

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
    return isMinesFound() && isCellsOpened();
  }

  private boolean isMinesFound() {
    boolean result = true;
    for (int m = 0; m < mines.length; m++) {
      result = result && getCellById(mines[m]).isFlag();
      if (!result) {
        break;
      }
    }
    return result;
  }

  private  boolean isCellsOpened() {
    boolean result = true;
    if (result) {
      for (int line = 0; line < cells.length; line++) {
        for (int place = 0; place < cells[0].length; place++) {
          result = result && ((cells[line][place].isMine() && cells[line][place].isFlag()) || cells[line][place].isSelected());
          if (!result) {
            break;
          }
        }
        if (!result) {
          break;
        }
      }
    }
    return result;
  }

  private Cell getCellById(int cellId) {
    int line = (int)Math.ceil((cellId - 1) / cells.length);
    int place = cellId - 1 - line * cells[0].length;
    return cells[line][place];
  }
}
