package net.patttern.minesweeper.proto.bases;

import net.patttern.minesweeper.proto.interfaces.Action;
import net.patttern.minesweeper.proto.interfaces.Logic;

/**
 * Created by ebabenko on 28.08.15.
 */
public abstract class BaseAction implements Action {
  protected final Logic logic;

  protected BaseAction(Logic logic) {
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
