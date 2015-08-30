package net.patttern.console.minesweeper.con;

import net.patttern.console.minesweeper.proto.bases.BaseArea;
import net.patttern.console.minesweeper.proto.interfaces.Cell;

/**
 * Created by pattern on 29.08.15.
 */
public class ConArea extends BaseArea {
  @Override
  public void prepare(Cell[][] cells) {
    this.cells = cells;
    linesOnArea = this.cells.length;
    placesInLine = this.cells[0].length;
  }

  @Override
  public void drawBoard() {
    // Заголовок
    // 1 строка
    String str = "   |";
    for (int s = 0; s < placesInLine; s++) {
      str += String.format("%2s ", s);
    }
    System.out.println(str);
    // 2 строка
    str = "-";
    for (int s = 0; s < (placesInLine + 1) * 3; s++) {
      str += "-";
    }
    System.out.println(str);
    // Рабочая область.
    for (int line = 0; line < linesOnArea; line++) {
      System.out.print(String.format("%3s|", line));
      for (int place = 0; place < placesInLine; place++) {
        drawCell(cells[line][place], nearMinesCount(line, place));
      }
      System.out.println();
    }
    System.out.println();
  }

  @Override
  public void drawCell(Cell cell, int count) {
    cell.draw(System.out, count);
  }

  @Override
  public void drawBang() {

  }

  @Override
  public void drawCongratulate() {

  }
}
