package net.patttern.console.minesweeper.proto;

import net.patttern.console.minesweeper.proto.interfaces.Area;

/**
 * Created by pattern on 26.08.15.
 */
public class BaseAction implements UserAction {
  private final GeneratorBoard generator;
  private final Area board;
  private final ILogic logic;

  public BaseAction(final ILogic logic, final Area board, final GeneratorBoard generator) {
    this.logic = logic;
    this.board = board;
    this.generator = generator;
  }

  public void initGame() {
    final Cell[][] cells = generator.generate();
    this.board.drawBoard(cells);
    this.logic.loadBoard(cells);
  }

  public void select(int x, int y, boolean bomb) {
    this.logic.suggest(x, y, bomb);
    this.board.drawCell(x, y);
    if (this.logic.shouldBang(x, y)) {
      this.board.drawBang();
    }
    if (this.logic.finish()) {
      this.board.drawCongratulate();
    }
  }
}
