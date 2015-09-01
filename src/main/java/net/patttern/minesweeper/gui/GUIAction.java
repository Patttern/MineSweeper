package net.patttern.minesweeper.gui;

import net.patttern.minesweeper.proto.bases.BaseAction;
import net.patttern.minesweeper.proto.interfaces.Cell;
import net.patttern.minesweeper.proto.interfaces.Logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by pattern on 30.08.15.
 */
class GUIAction extends BaseAction implements ActionListener, MouseListener {
  public GUIAction(Logic logic) {
    super(logic, GUIRun.area);
    this.area = GUIRun.area;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.initGame();
    GUIRun.generate.setEnabled(!logic.isStarted());
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    GUIRun.generate.setEnabled(!logic.isStarted());
    ((GUIArea)area).repaint();
  }

  @Override
  public void mousePressed(MouseEvent e) {
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    int line = 0;
    int place = 0;
    int posX;
    int posY;
    Cell[][] cells = logic.getCells();
    for (int l = 0; l < cells.length; l++) {
      line = l;
      posY = ((GUICell)cells[l][0]).getPosY();
      if (e.getY() < posY) {
        line = l - 1;
        break;
      }
    }
    for (int p = 0; p < cells[0].length; p++) {
      place = p;
      posX = ((GUICell)cells[line][p]).getPosX();
      if (e.getX() < posX) {
        place = p - 1;
        break;
      }
    }
    if (e.isMetaDown()) {
      markCell(line, place);
    } else {
      selectCell(line, place);
    }
    if (!logic.isStarted()) {
      mouseClicked(e);
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }
}
