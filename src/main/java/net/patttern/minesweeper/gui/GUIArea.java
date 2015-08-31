package net.patttern.minesweeper.gui;

import net.patttern.minesweeper.proto.interfaces.Area;
import net.patttern.minesweeper.proto.interfaces.Cell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by pattern on 30.08.15.
 */
public class GUIArea extends JPanel implements Area, MouseListener {
  public static final int PADDING = 41;
  private Cell[][] cells;
  private int linesOnArea;
  private int placesInLine;

  @Override
  public void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);
    if (this.cells != null) {
      for (int line = 0; line < cells.length; line++) {
        for (int place = 0; place < cells[0].length; place++) {
          graphics.setColor(Color.CYAN);
          cells[line][place].draw(graphics, nearMinesCount(line, place));
          graphics.drawRect(line * PADDING, place * PADDING, PADDING, PADDING);
        }
      }
    }
  }

  @Override
  public void prepare(Cell[][] cells) {
    this.cells = cells;
    linesOnArea = this.cells.length;
    placesInLine = this.cells[0].length;
  }

  @Override
  public void drawBoard() {
    this.repaint();
  }

  @Override
  public void drawBang() {
    System.out.println("BANG!!! :(");
  }

  @Override
  public void drawCongratulate() {
    System.out.println("Congratulation!!! =)");
  }

  /**
   * Проверка, установлена ли в ячейке мина.
   * @param line Ряд.
   * @param place Место.
   * @return TRUE, если мина установлена в ячейке, FALSE в ином случае.
   */
  protected boolean cellMined(int line, int place) {
    return cellInRange(line, place) ? cells[line][place].isMine() : false;
  }

  /**
   * Проверка, существует ли ячейка.
   * @param line Ряд.
   * @param place Место.
   * @return TRUE, если ячейка существует, FALSE в ином случае.
   */
  protected boolean cellInRange(int line, int place) {
    return line >=0 && line < linesOnArea && place >=0 && place < placesInLine;
  }

  /**
   * Количество заминированных ячеек, находящихся рядом.
   * [*][*][*]
   * [*][?][*]
   * [*][*][*]
   * @param line Ряд.
   * @param place Место.
   * @return Количество заминированных ячеек.
   */
  protected int nearMinesCount(int line, int place) {
    return (cellMined(line-1, place) ? 1 : 0)
      + (cellMined(line-1, place+1) ? 1 : 0)
      + (cellMined(line, place+1) ? 1 : 0)
      + (cellMined(line+1, place+1) ? 1 : 0)
      + (cellMined(line+1, place) ? 1 : 0)
      + (cellMined(line+1, place-1) ? 1 : 0)
      + (cellMined(line, place-1) ? 1 : 0)
      + (cellMined(line-1, place-1) ? 1 : 0);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    System.out.println("mouseClicked");
  }

  @Override
  public void mousePressed(MouseEvent e) {
    System.out.println("mousePressed");
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    System.out.println("mouseReleased");
  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }
}
