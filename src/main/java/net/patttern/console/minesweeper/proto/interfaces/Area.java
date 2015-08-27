package net.patttern.console.minesweeper.proto.interfaces;

import net.patttern.console.minesweeper.proto.Cell;

/**
 * Created by pattern on 26.08.15.
 */
public interface Area {
  void setAreaSize(int width, int height);

  void drawBoard(Cell[][] cells);

  void drawCell(int x, int y);

  void drawBang();

  void drawCongratulate();
}
