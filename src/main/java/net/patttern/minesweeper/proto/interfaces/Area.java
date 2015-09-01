package net.patttern.minesweeper.proto.interfaces;

/**
 * Created by pattern on 26.08.15.
 */
public interface Area {
  void prepare(Cell[][] cells);

  void drawBoard();

  void drawBang();

  void drawCongratulate();

  void openAllCells();

  void openCell(int line, int place);
}
