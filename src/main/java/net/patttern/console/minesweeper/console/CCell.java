package net.patttern.console.minesweeper.console;

/**
 * Created by ebabenko on 27.08.15.
 */
public class CCell {
  private int id;
  private boolean bomb;
  private boolean flag;
  private boolean empty;

  public CCell(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public boolean isBomb() {
    return bomb;
  }

  public void setBomb(boolean bomb) {
    this.bomb = bomb;
  }

  public boolean isEmpty() {
    return empty;
  }

  public void setEmpty(boolean empty) {
    this.empty = empty;
  }

  public void select() {
    flag = !flag;
  }
}
