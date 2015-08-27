package net.patttern.console.minesweeper.common;

import net.patttern.console.minesweeper.proto.Cell;

import java.io.PrintStream;

/**
 * Created by pattern on 26.08.15.
 */
public class GameCell implements Cell<PrintStream> {
  private boolean bomb;
  private boolean suggestBomb = false;
  private boolean suggestEmpty = false;

  public GameCell(boolean bomb) {
    this.bomb = bomb;
  }

  @Override
  public boolean isBomb() {
    return this.bomb;
  }

  @Override
  public boolean isSuggestBomb() {
    return this.suggestBomb;
  }

  @Override
  public boolean isSuggestEmpty() {
    return this.suggestEmpty;
  }

  @Override
  public void suggestEmpty() {
    this.suggestEmpty = true;
  }

  @Override
  public void suggestBomb() {
    this.suggestBomb = true;
  }

  @Override
  public void draw(PrintStream paint, boolean real) {
    if (real) {
      if (this.isBomb()) {
        paint.print("[*] ");
      } else {
        paint.print("[ ] ");
      }
    } else {
      if (this.suggestBomb) {
        paint.print("[?] ");
      } else if (this.suggestEmpty) {
        paint.print("[ ] ");
      } else {
        paint.print("[X] ");
      }
    }
  }
}
