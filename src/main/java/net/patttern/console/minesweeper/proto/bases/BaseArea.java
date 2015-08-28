package net.patttern.console.minesweeper.proto.bases;

import net.patttern.console.minesweeper.proto.Cell;
import net.patttern.console.minesweeper.proto.interfaces.Area;

/**
 * Created by ebabenko on 27.08.15.
 */
public abstract class BaseArea<T> implements Area {
  protected Cell[][] cells;

  @Override
  public void setAreaSize(int width, int height) {
    this.cells = new Cell[width][height];
  }

  @Override
  public void drawBoard(Cell[][] cells) {
    for (Cell[] row: cells) {
      for (Cell cell: row) {

      }
    }
  }

  @Override
  public void drawCell(int x, int y) {

  }

  @Override
  public void drawBang() {

  }

  @Override
  public void drawCongratulate() {

  }
}
/*
[X][X][X][X][X][X][X][X][X]
[X][X][X][X][X][X][X][X][X]
[X][X][X][X][X][X][X][X][X]
[X][X][X][X][X][X][X][X][X]
[X][X][X][X][?][X][X][X][X]
[X][X][X][X][X][X][X][X][X]
[X][X][X][X][X][X][X][X][X]
[X][X][X][X][X][X][X][X][X]
[X][X][X][X][X][X][X][X][X]
 */
