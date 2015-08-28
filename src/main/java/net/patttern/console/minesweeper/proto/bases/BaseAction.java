package net.patttern.console.minesweeper.proto.bases;

import net.patttern.console.minesweeper.proto.interfaces.Action;
import net.patttern.console.minesweeper.proto.interfaces.Area;
import net.patttern.console.minesweeper.proto.interfaces.Generator;
import net.patttern.console.minesweeper.proto.interfaces.Logic;

/**
 * Created by ebabenko on 28.08.15.
 */
public abstract class BaseAction implements Action {
  private BaseLogic logic;
  private BaseArea area;
  private BaseGenerator generator;

  @Override
  public void initGame(final Logic logic, final Area area, final Generator generator) {
    this.logic = (BaseLogic) logic;
    this.area = (BaseArea) area;
    this.generator = (BaseGenerator) generator;
    this.generator.generate();
    this.generator.makeMine(this.logic.mineCount);
  }

  @Override
  public void markCell(int line, int place) {
    logic.markCell(line, place);
  }

  @Override
  public void selectCell(int line, int place) {
    logic.selectCell(line, place);
  }
}
