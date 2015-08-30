package net.patttern.console.minesweeper.console;

import net.patttern.console.minesweeper.proto.bases.BaseCell;

import java.io.PrintStream;

/**
 * Created by ebabenko on 27.08.15.
 */
public class CCell extends BaseCell<PrintStream> {
  public CCell(int id) {
    super(id);
  }

  @Override
  public boolean isSelected() {
    return false;
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
