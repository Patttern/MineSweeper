package net.patttern.console.minesweeper.proto;

/**
 * Created by pattern on 26.08.15.
 */
public interface Board {
  void drawBoard(Cell[][] cells);

  void drawCell(int x, int y);

  void drawBang();

  void drawCongratulate();
}
