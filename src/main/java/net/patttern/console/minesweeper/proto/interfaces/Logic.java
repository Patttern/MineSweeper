package net.patttern.console.minesweeper.proto.interfaces;

/**
 * Created by ebabenko on 28.08.15.
 */
public interface Logic {
  void start();

  void selectCell(int line, int place);

  void markCell(int line, int place);

  boolean finish();
}
