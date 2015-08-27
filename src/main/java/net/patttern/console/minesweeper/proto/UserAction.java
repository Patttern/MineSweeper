package net.patttern.console.minesweeper.proto;

/**
 * Created by pattern on 26.08.15.
 */
public interface UserAction {
  void initGame();

  void select(int x, int y, boolean bomb);
}
