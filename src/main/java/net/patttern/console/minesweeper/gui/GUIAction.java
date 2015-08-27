package net.patttern.console.minesweeper.gui;

import net.patttern.console.minesweeper.proto.BaseAction;
import net.patttern.console.minesweeper.proto.BaseLogic;
import net.patttern.console.minesweeper.proto.Board;
import net.patttern.console.minesweeper.proto.GeneratorBoard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by pattern on 26.08.15.
 */
public class GUIAction extends BaseAction implements ActionListener, MouseListener {
  private GUIBoard board;

  public GUIAction(BaseLogic logic, GUIBoard board, GeneratorBoard generator) {
    super(logic, board, generator);
    this.board = board;
    this.board.addMouseListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.initGame();
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    board.repaint();
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
