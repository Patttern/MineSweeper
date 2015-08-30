package net.patttern.console.minesweeper.con;

import net.patttern.console.minesweeper.modes.Easy;

/**
 * Created by pattern on 30.08.15.
 */
public class ConRun {
  public static void main(String[] args) {
    ConAction action = new ConAction(new Easy(new ConArea(), new ConGenerator()));
    action.initGame();
  }
}
