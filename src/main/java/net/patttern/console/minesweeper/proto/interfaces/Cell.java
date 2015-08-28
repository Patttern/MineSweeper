package net.patttern.console.minesweeper.proto.interfaces;

/**
 * Created by pattern on 27.08.15.
 */
public interface Cell<T> {
  boolean isMine();

  void setMine();

  boolean isFlag();

  void mark();

  void select();

  void draw(T paint);

  void draw(T paint, int count);
}
