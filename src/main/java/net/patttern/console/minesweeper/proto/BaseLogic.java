package net.patttern.console.minesweeper.proto;

/**
 * Created by pattern on 26.08.15.
 */
public interface BaseLogic {
  void loadBoard(Cell[][] cells);

  boolean shouldBang(int x, int  y);

  boolean finish();

  void suggest(int x, int y, boolean bomb);
}
