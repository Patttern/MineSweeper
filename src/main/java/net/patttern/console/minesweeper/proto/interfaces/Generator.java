package net.patttern.console.minesweeper.proto.interfaces;

import net.patttern.console.minesweeper.proto.bases.BaseCell;

/**
 * Created by ebabenko on 28.08.15.
 */
public interface Generator {
  BaseCell[][] generate();

  int[] makeMine(int mineCount);
}
