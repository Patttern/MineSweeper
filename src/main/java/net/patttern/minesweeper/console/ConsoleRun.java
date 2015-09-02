package net.patttern.minesweeper.console;

import net.patttern.minesweeper.modes.Easy;

/**
 * Created by pattern on 30.08.15.
 */
public class ConsoleRun {
  private static final ConsoleArea area = new ConsoleArea();
  public static void main(String[] args) {
    ConsoleAction action = new ConsoleAction(new Easy(area, new ConsoleGenerator()));
    action.initGame();
  }
}
