package net.patttern.console.minesweeper.proto.interfaces;

/**
 * Created by ebabenko on 28.08.15.
 */
public interface Generator {
  void prepare(int linesOnArea, int placesInLine);

  Cell[][] generate();

  int[] makeMine(int mineCount);
}
