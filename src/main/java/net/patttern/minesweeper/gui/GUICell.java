package net.patttern.minesweeper.gui;

import net.patttern.minesweeper.proto.bases.BaseCell;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by pattern on 30.08.15.
 */
class GUICell extends BaseCell<Graphics> {
  private int posX;
  private int posY;
  private final Font helvetica = new Font("Helvetica", Font.BOLD, 40);
  private static final Color[] colors = {
    Color.LIGHT_GRAY,        // 0
    new Color(0, 0, 255),    // 1
    new Color(0, 164, 0),    // 2
    new Color(255, 0, 0),    // 3
    new Color(0, 0, 128),    // 4
    new Color(128, 0, 0),    // 5
    new Color(0, 128, 128),  // 6
    new Color(0, 0, 0),      // 7
    new Color(128, 128, 128) // 8
  };

  public GUICell(int id) {
    super(id);
  }

  @Override
  public void draw(Graphics paint, int count) {
    BufferedImage image;
    if (flag) {
      image = GUIRun.flag;
    } else {
      if (selected) {
        if (mine) {
          image = GUIRun.mine;
        } else {
          image = GUIRun.transp;
        }
      } else {
        image = GUIRun.block;
      }
    }
    paint.setColor(Color.CYAN);
    paint.drawImage(image, posX, posY, image.getWidth(), image.getHeight(), null);
    if (image == GUIRun.transp && count > 0) {
      paint.setFont(helvetica);
      paint.setColor(colors[count]);
      paint.drawString(Integer.toString(count), posX + 5, posY + GUIArea.PADDING - 5);
    }
  }

  public void setCoord(int x, int y) {
    posX = x * GUIArea.PADDING + 1;
    posY = y * GUIArea.PADDING + 1;
  }

  public int getPosX() {
    return posX;
  }

  public int getPosY() {
    return posY;
  }
}
