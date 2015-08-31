package net.patttern.minesweeper.console;

import net.patttern.minesweeper.proto.bases.BaseGenerator;
import net.patttern.minesweeper.proto.interfaces.Cell;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

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

  @Override
  public int[] makeMine(int mineCount) {
    int[] mines = new int[mineCount];
    int[] possible = IntStream.rangeClosed(1, placesInLine * linesOnArea).toArray();
    Random rand = new Random();
    int mined = 0;
    while (possible.length > 0 && mined < mineCount) {
      int cellId = possible[rand.nextInt(possible.length)];
      possible = Arrays.stream(possible).filter(x -> x != cellId).toArray();
      int line = (int)Math.ceil((cellId - 1) / linesOnArea);
      int place = cellId - 1 - line * placesInLine;
      if (canMined(line, place)) {
        cells[line][place].setMine();
        mines[mined] = cellId;
        mined++;
        System.out.println("Mined: cells[" + line + "][" + place + "]");
      } else {
        System.out.println("Remined: cells[" + line + "][" + place + "]");
      }
    }
    return mines;
  }
}