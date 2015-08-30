package net.patttern.console.minesweeper.con;

import net.patttern.console.minesweeper.proto.bases.BaseCell;

import java.io.PrintStream;

/**
 * Created by pattern on 30.08.15.
 */
public class ConCell extends BaseCell<PrintStream> {
  public ConCell(int id) {
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
      if (selected) {
        if (mine) {
          paint.print("[*]");
        } else {
          paint.print("[" + (count > 0 ? count : " ") + "]");
        }
      } else {
        paint.print("[-]");
      }
    }
  }
}
