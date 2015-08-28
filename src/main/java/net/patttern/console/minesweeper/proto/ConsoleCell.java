package net.patttern.console.minesweeper.proto;

import net.patttern.console.minesweeper.proto.bases.BaseCell;

import java.io.PrintStream;

/**
 * Created by ebabenko on 28.08.15.
 */
public abstract class ConsoleCell extends BaseCell<PrintStream> {
  public ConsoleCell(int id) {
    super(id);
  }

  @Override
  public void draw(PrintStream paint) {
    draw(paint, 0);
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
