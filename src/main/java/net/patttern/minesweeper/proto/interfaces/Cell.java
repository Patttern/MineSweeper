package net.patttern.minesweeper.proto.interfaces;

/**
 * Created by pattern on 27.08.15.
 */
public interface Cell<T> {
  int getId();

  void setMine();

  boolean isMine();

  boolean isFlag();

  boolean isSelected();

  void mark();

  void select();

  void draw(T paint, int count);
}
