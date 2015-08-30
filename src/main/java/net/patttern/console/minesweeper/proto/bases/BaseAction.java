package net.patttern.console.minesweeper.proto.bases;

import net.patttern.console.minesweeper.proto.interfaces.Action;
import net.patttern.console.minesweeper.proto.interfaces.Logic;

/**
 * Created by ebabenko on 28.08.15.
 */
public abstract class BaseAction implements Action {
  private Logic logic;

  public BaseAction(Logic logic) {
    this.logic = logic;
  }

  @Override
  public void initGame() {
    this.logic.start();
  }

  @Override
  public void selectCell(int line, int place) {
    logic.selectCell(line, place);
  }

  @Override
  public void markCell(int line, int place) {
    logic.markCell(line, place);
  }
}
