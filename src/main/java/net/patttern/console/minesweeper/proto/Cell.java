package net.patttern.console.minesweeper.proto;

/**
 * Created by pattern on 26.08.15.
 */
public interface Cell<T> {
  boolean isBomb();

  boolean isSuggestBomb();

  boolean isSuggestEmpty();

  void suggestEmpty();

  void suggestBomb();

  void draw(T paint, boolean real);
}
