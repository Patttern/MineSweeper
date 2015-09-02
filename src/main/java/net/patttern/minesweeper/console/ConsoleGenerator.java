package net.patttern.minesweeper.console;

import net.patttern.minesweeper.proto.bases.BaseGenerator;
import net.patttern.minesweeper.proto.interfaces.Cell;

/**
 * Created by pattern on 29.08.15.
 */
public class ConsoleGenerator extends BaseGenerator {
  @Override
  public Cell[][] generate() {
    for (int line = 0; line < linesOnArea; line++) {
      for (int place = 0; place < placesInLine; place++) {
        int cellId = line * placesInLine + place + 1;
        cells[line][place] = new ConsoleCell(cellId);
      }
    }
    return cells;
  }
}
