package net.patttern.minesweeper.proto.bases;

import net.patttern.minesweeper.proto.interfaces.Area;
import net.patttern.minesweeper.proto.interfaces.Cell;
import net.patttern.minesweeper.proto.interfaces.Generator;
import net.patttern.minesweeper.proto.interfaces.Logic;

import javax.swing.*;

/**
 * Created by ebabenko on 28.08.15.
 */
public abstract class BaseLogic implements Logic {
  protected boolean started = false;
  protected Area area;
  protected Generator generator;
  protected Cell[][] cells;
  protected int[] mines;
  private int count = 0;

  public BaseLogic(Area area, Generator generator) {
    this.area = area;
    this.generator = generator;
  }

  @Override
  public void start() {
    count = 0;
    started = true;
  }

  @Override
  public void selectCell(int line, int place) {
    if (started && !cells[line][place].isFlag()) {
      if (cells[line][place].isMine()) {
        end();
        area.drawBang();
      } else if (finish()) {
        end();
        area.drawCongratulate();
      } else {
        area.openCell(line, place);
      }
    }
  }

  @Override
  public void markCell(int line, int place) {
    if (started) {
      if (count < mines.length || cells[line][place].isFlag()) {
        cells[line][place].mark();
        if (cells[line][place].isFlag()) {
          count++;
        } else {
          count--;
        }
        if (finish()) {
          end();
          area.drawCongratulate();
        }
      } else {
        JOptionPane.showMessageDialog(null, "<html><p>Можно использовать не более " + mines.length +
          " флагов.<br/>:(</p></html>", "Флагов больше нет", JOptionPane.INFORMATION_MESSAGE);
      }
    }
  }

  @Override
  public boolean finish() {
    return isMinesFound() && isCellsOpened();
  }

  private void end() {
    started = false;
    area.openAllCells();
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
        for (int place = 0; place < cells[line].length; place++) {
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
    int line = (int)Math.ceil((cellId - 1) / cells[0].length);
    int place = cellId - 1 - line * cells[0].length;
    return cells[line][place];
  }

  @Override
  public Cell[][] getCells() {
    return cells;
  }

  @Override
  public int[] getMines() {
    return mines;
  }

  @Override
  public boolean isStarted() {
    return started;
  }
}
