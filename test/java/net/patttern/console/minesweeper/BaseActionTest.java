package net.patttern.console.minesweeper;

import net.patttern.console.minesweeper.common.GameBoard;
import net.patttern.console.minesweeper.common.GameCell;
import net.patttern.console.minesweeper.modes.Easy;
import net.patttern.console.minesweeper.proto.BaseAction;
import net.patttern.console.minesweeper.proto.Cell;
import org.junit.Test;

/**
 * Created by pattern on 26.08.15.
 */
public class BaseActionTest {
  final BaseAction action = new BaseAction(
    new Easy(),
    new GameBoard(),
    () -> new Cell[][] {
      {new GameCell(true), new GameCell(false)},
      {new GameCell(true), new GameCell(false)}
    }
  );

  @Test
  public void successGame () {
    action.initGame();
    action.select(0, 0, true);
    action.select(1, 0, true);
    action.select(0, 1, false);
    action.select(1, 1, false);
  }

  @Test
  public void failureGame () {
    action.initGame();
    action.select(0, 0, true);
    action.select(1, 0, false);
  }
}
