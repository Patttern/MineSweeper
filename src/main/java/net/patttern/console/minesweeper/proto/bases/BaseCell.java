package net.patttern.console.minesweeper.proto.bases;

import net.patttern.console.minesweeper.proto.interfaces.Cell;

/**
 * Created by ebabenko on 28.08.15.
 */
public abstract class BaseCell<T> implements Cell<T> {
  protected int id;
  protected boolean mine = false;
  protected boolean flag = false;
  protected boolean selected = false;

  public BaseCell(int id) {
    this.id = id;
  }

  @Override
  public boolean isMine() {
    return mine;
  }

  @Override
  public void setMine() {
    if (!mine && !flag && !selected) {
      mine = true;
    }
  }

  @Override
  public boolean isFlag() {
    return flag;
  }

  @Override
  public void mark() {
    if (!selected) {
      flag = !flag;
    }
  }

  @Override
  public void select() {
    if (!flag) {
      selected = true;
    }
  }

  @Override
  abstract public void draw(T paint);

  @Override
  abstract public void draw(T paint, int count);
}
