package net.patttern.minesweeper.gui;

import net.patttern.minesweeper.proto.bases.BaseAction;
import net.patttern.minesweeper.proto.interfaces.Logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by pattern on 30.08.15.
 */
public class GUIAction extends BaseAction implements ActionListener, MouseListener {
  public GUIAction(Logic logic, GUIArea area) {
    super(logic, area);
    this.area = area;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.initGame();
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    ((GUIArea)area).repaint();
  }

  @Override
  public void mousePressed(MouseEvent e) {
    System.out.println("mousePressed");
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    System.out.println("mouseReleased");
    //TODO action for cell
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    System.out.println("mouseEntered");
  }

  @Override
  public void mouseExited(MouseEvent e) {
    System.out.println("mouseExited");
  }
}
