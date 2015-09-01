package net.patttern.minesweeper.console;

import net.patttern.minesweeper.proto.bases.BaseCell;

import java.io.PrintStream;

/**
 * Created by pattern on 30.08.15.
 */
public class ConsoleCell extends BaseCell<PrintStream> {
  public ConsoleCell(int id) {
    super(id);
  }

  @Override
  public void draw(PrintStream paint, int count) {
    if (flag) {
      paint.print("[?]");
    } else {
//      if (selected) {
        if (mine) {
          paint.print("[*]");
        } else {
          paint.print("[" + (count > 0 ? count : " ") + "]");
        }
//      } else {
//        paint.print("[-]");
//      }
    }
  }
}
