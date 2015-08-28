package net.patttern.console.minesweeper.modes;

import net.patttern.console.minesweeper.proto.bases.BaseLogic;

/**
 * Created by ebabenko on 28.08.15.
 */
public class Easy extends BaseLogic {
  public Easy(final int placesInLine, final int linesOnArea, final int mineCount) {
    this.placesInLine = placesInLine;
    this.linesOnArea = linesOnArea;
    this.mineCount = mineCount;
  }
}
