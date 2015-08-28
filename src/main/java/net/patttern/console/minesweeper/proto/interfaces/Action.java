package net.patttern.console.minesweeper.proto.interfaces;

/**
 * Created by ebabenko on 28.08.15.
 */
public interface Action {
  void initGame(Logic logic, Area area, Generator generator);

  void markCell(int line, int place);

  void selectCell(int line, int place);
}
