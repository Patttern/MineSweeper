package net.patttern.console.minesweeper.common;

import net.patttern.console.minesweeper.proto.interfaces.Area;
import net.patttern.console.minesweeper.proto.Cell;

/**
 * Created by pattern on 26.08.15.
 */
public class GameBoard implements Area {
  private Cell[][] cells;

  @Override
  public void setAreaSize(int width, int height) {

  }

  public void drawBoard(Cell[][] cells) {
    this.cells = cells;
    this.redraw(false);
  }

  public void drawCell(int x, int y) {
    System.out.println("***** SELECT *****");
    this.redraw(false);
  }

  public void drawBang() {
    System.out.println("***** BANG *****");
    this.redraw(true);
  }

  public void drawCongratulate() {
    System.out.println("***** CONGRATULATE *****");
  }

  private void redraw(boolean real) {
    for (Cell[] row: cells) {
      for (Cell cell: row) {
        cell.draw(System.out, real);
      }
      System.out.println();
    }
    System.out.println();
  }
}