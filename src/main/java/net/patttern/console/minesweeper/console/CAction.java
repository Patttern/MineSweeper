package net.patttern.console.minesweeper.console;

import net.patttern.console.minesweeper.proto.bases.BaseAction;
import net.patttern.console.minesweeper.proto.bases.BaseGenerator;
import net.patttern.console.minesweeper.proto.bases.BaseLogic;
import net.patttern.console.minesweeper.proto.interfaces.Logic;

/**
 * Created by ebabenko on 28.08.15.
 */
public class CAction extends BaseAction {
  private BaseLogic logic;
  private CArea area;
  private BaseGenerator generator;

  public CAction(Logic logic) {
    super(logic);
  }
}
