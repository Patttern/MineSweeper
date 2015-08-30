package net.patttern.console.minesweeper.proto.interfaces;

/**
 * Created by pattern on 26.08.15.
 */
public interface Area {
  void prepare(Cell[][] cells);

  void drawBoard();

  void drawCell(Cell cell, int count);

  void drawBang();

  void drawCongratulate();
}
