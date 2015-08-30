package net.patttern.console.minesweeper.guiold;

import net.patttern.console.minesweeper.Main;
import net.patttern.console.minesweeper.proto.Cell;

import java.awt.*;

/**
 * Created by pattern on 26.08.15.
 */
public class GUICell implements Cell<Graphics> {
  private int posX;
  private int posY;

  public GUICell(int posX, int posY) {
    this.posX = posX;
    this.posY = posY;
  }

  @Override
  public boolean isBomb() {
    return false;
  }

  @Override
  public boolean isSuggestBomb() {
    return false;
  }

  @Override
  public boolean isSuggestEmpty() {
    return false;
  }

  @Override
  public void suggestEmpty() {

  }

  @Override
  public void suggestBomb() {

  }

  @Override
  public void draw(Graphics paint, boolean real) {
    paint.drawImage(real ? Main.block : Main.mine, this.posX * GUIBoard.PADDING + 1, this.posY * GUIBoard.PADDING + 1, Main.block.getWidth(), Main.block.getHeight(), null);
  }
}
