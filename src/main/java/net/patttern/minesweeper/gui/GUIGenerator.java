package net.patttern.minesweeper.gui;

import net.patttern.minesweeper.proto.bases.BaseGenerator;
import net.patttern.minesweeper.proto.interfaces.Cell;

/**
 * Created by pattern on 30.08.15.
 */
class GUIGenerator extends BaseGenerator {
  @Override
  public Cell[][] generate() {
    for (int line = 0; line < linesOnArea; line++) {
      for (int place = 0; place < placesInLine; place++) {
        int cellId = line * placesInLine + place + 1;
        GUICell cell = new GUICell(cellId);
        cell.setCoord(place, line);
        cells[line][place] = cell;
      }
    }
    return cells;
  }
}
