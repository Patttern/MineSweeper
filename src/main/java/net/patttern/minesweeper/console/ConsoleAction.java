package net.patttern.minesweeper.console;

import net.patttern.minesweeper.proto.bases.BaseAction;
import net.patttern.minesweeper.proto.interfaces.Logic;

/**
 * Created by pattern on 29.08.15.
 */
public class ConsoleAction extends BaseAction {
  public ConsoleAction(Logic logic, ConsoleArea area) {
    super(logic, area);
  }
}
