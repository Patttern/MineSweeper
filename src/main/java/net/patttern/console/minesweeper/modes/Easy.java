package net.patttern.console.minesweeper.modes;

import net.patttern.console.minesweeper.proto.BaseLogic;
import net.patttern.console.minesweeper.proto.Cell;

/**
 * Created by pattern on 26.08.15.
 */
public class Easy implements BaseLogic {
  private Cell[][] cells;
  private int boardWidth;
  private int boardHeight;

  public Easy() {
    this.boardWidth = 9;
    this.boardHeight = 9;
  }

  @Override
  public void loadBoard(Cell[][] cells) {
    this.cells = cells;
  }

  @Override
  public boolean shouldBang(int x, int y) {
    final Cell selected = this.cells[x][y];
    return selected.isBomb() && !selected.isSuggestBomb();
  }

  @Override
  public boolean finish() {
    boolean finish = false;
    for (Cell[] row: cells) {
      for (Cell cell: row) {
        finish = (
            (cell.isSuggestBomb() && cell.isBomb()) ||
            (cell.isSuggestEmpty() && !cell.isBomb())
          );
      }
    }
    return finish;
  }

  @Override
  public void suggest(int x, int y, boolean bomb) {
    if (bomb) {
      this.cells[x][y].suggestBomb();
    } else {
      this.cells[x][y].suggestEmpty();
    }
  }

  public int getBoardWidth() {
    return boardWidth;
  }

  public int getBoardHeight() {
    return boardHeight;
  }
}
