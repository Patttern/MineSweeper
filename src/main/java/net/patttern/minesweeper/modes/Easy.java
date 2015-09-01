package net.patttern.minesweeper.modes;

import net.patttern.minesweeper.proto.bases.BaseLogic;
import net.patttern.minesweeper.proto.interfaces.Area;
import net.patttern.minesweeper.proto.interfaces.Generator;

/**
 * Created by ebabenko on 28.08.15.
 */
public class Easy extends BaseLogic {
  public static final int linesOnArea = 9;
  public static final int placesInLine = 9;
  public static final int mineCount = 10;

  public Easy(Area area, Generator generator) {
    super(area, generator);
  }

  @Override
  public void start() {
    if (!started) {
      generator.prepare(linesOnArea, placesInLine);
      cells = generator.generate();
      mines = generator.makeMine(mineCount);
      area.prepare(cells);
      area.drawBoard();
      super.start();
    }
  }
}
