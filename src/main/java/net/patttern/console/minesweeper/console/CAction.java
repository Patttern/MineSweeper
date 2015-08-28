package net.patttern.console.minesweeper.console;

import net.patttern.console.minesweeper.proto.bases.BaseAction;
import net.patttern.console.minesweeper.proto.bases.BaseGenerator;
import net.patttern.console.minesweeper.proto.bases.BaseLogic;

/**
 * Created by ebabenko on 28.08.15.
 */
public class CAction extends BaseAction {
  private BaseLogic logic;
  private CArea area;
  private BaseGenerator generator;

  public CAction(BaseLogic logic, CArea area, BaseGenerator generator) {
    this.area = area;
  }
}
