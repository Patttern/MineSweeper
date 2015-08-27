package net.patttern.console.minesweeper.console;

import java.io.PrintStream;

/**
 * Created by ebabenko on 27.08.15.
 */
public class CCell {
  private int id;
  private boolean mine = false;
  private boolean flag = false;
  private boolean selected = false;

  public CCell(int id) {
    this.id = id;
  }

//  public int getId() {
//    return id;
//  }
//
//  public boolean isFlag() {
//    return flag;
//  }

  public boolean isMine() {
    return mine;
  }

  public void setMine() {
    if (!mine && !flag && !selected) {
      mine = true;
    }
  }

  public void mark() {
    if (!selected) {
      flag = !flag;
    }
  }

  public void select() {
    if (!flag) {
      selected = true;
    }
  }

  public void draw(PrintStream paint) {
    draw(paint, 0);
  }

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
