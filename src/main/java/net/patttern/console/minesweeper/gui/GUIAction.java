package net.patttern.console.minesweeper.gui;

import net.patttern.console.minesweeper.proto.bases.BaseAction;
import net.patttern.console.minesweeper.proto.interfaces.Logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by pattern on 30.08.15.
 */
public class GUIAction extends BaseAction implements ActionListener, MouseListener {
  private GUIArea area;

  public GUIAction(Logic logic) {
    super(logic);
  }

  public GUIAction(Logic logic, GUIArea area) {
    super(logic);
    this.area = area;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.initGame();
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    area.repaint();
  }

  @Override
  public void mousePressed(MouseEvent e) {
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }
}
