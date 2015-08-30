package net.patttern.console.minesweeper.gui;

import net.patttern.console.minesweeper.guiold.GUIBoard;
import net.patttern.console.minesweeper.proto.bases.BaseCell;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by pattern on 30.08.15.
 */
public class GUICell extends BaseCell<Graphics> {
  private int posX;
  private int posY;

  public GUICell(int id) {
    super(id);
  }

  @Override
  public void draw(Graphics paint) {
    draw(paint, 0);
  }

  @Override
  public void draw(Graphics paint, int count) {
    BufferedImage image;
    if (flag) {
      image = GUIRun.flag;
    } else {
//      if (selected) {
        if (mine) {
          image = GUIRun.mine;
        } else {
          image = GUIRun.transp;
        }
//      } else {
//        image = GUIRun.block;
//      }
    }
    paint.drawImage(image, posX * GUIBoard.PADDING + 1, posY * GUIBoard.PADDING + 1, image.getWidth(), image.getHeight(), null);
  }

  public void setCoord(int x, int y) {
    posX = x;
    posY = y;
  }
}
