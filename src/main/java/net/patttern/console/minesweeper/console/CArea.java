package net.patttern.console.minesweeper.console;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by ebabenko on 27.08.15.
 */
public class CArea {
  private int mineCount;
  private int placesInLine;
  private int linesOnArea;
  private CCell[][] cells;

  /**
   * Конструктор класса.
   * @param placesInLine Количество мест в ряду.
   * @param linesOnArea Количество рядов.
   * @param mineCount Количество мин.
   */
  public CArea(int placesInLine, int linesOnArea, int mineCount) {
    this.placesInLine = placesInLine;
    this.linesOnArea = linesOnArea;
    this.mineCount = mineCount;
    cells = new CCell[linesOnArea][placesInLine];
    generate();
    makeMine();
    drawBoard();
  }

  /**
   * Генерация поля.
   */
  private void generate() {
    for (int line = 0; line < linesOnArea; line++) {
      for (int place = 0; place < placesInLine; place++) {
        int cellId = line * placesInLine + place + 1;
        cells[line][place] = new CCell(cellId);
      }
    }
//    cells[2][3].mark();
//    cells[3][5].mark();
//    cells[0][6].mark();
//    cells[7][2].mark();
  }

  /**
   * Распределение мин.
   */
  private void makeMine() {
    int[] possible = IntStream.rangeClosed(1, placesInLine * linesOnArea).toArray();
    Random rand = new Random();
    int mined = 0;
    while (possible.length > 0 && mined < mineCount) {
      int cellId = possible[rand.nextInt(possible.length)];
      possible = Arrays.stream(possible).filter(x -> x != cellId).toArray();
      int line = (int)Math.ceil((cellId - 1) / linesOnArea);
      int place = cellId - 1 - line * placesInLine;
      if (canMined(line, place)) {
        cells[line][place].setMine();
        mined++;
        System.out.println("Mined: cells[" + line + "][" + place + "]");
      } else {
        System.out.println("Remined: cells[" + line + "][" + place + "]");
      }
    }
  }

  /**
   * Проверка, можно ли установить мину в ячейку.
   * Установить мину можно, если ближняя ячейка не мина или дальняя ячейка не мина.
   * [1][2][2][1]
   * [2][*][*][2]
   * [2][*][*][2]
   * [1][2][2][1]
   * @param line Ряд.
   * @param place Место.
   * @return TRUE, если можно установить мину в ячейку, FALSE в ином случае.
   */
  private boolean canMined(int line, int place) {
    return
      // Ячейка еще не заминирована?
      !cellMined(line, place) &&
      // Top
      // [*]
      // [*]
      // [?]
      (!cellMined(line-1, place) || !cellMined(line-2, place))
      // Right
      // [?][*][*]
      && (!cellMined(line, place+1) || !cellMined(line, place+2))
      // Bottom
      // [?]
      // [*]
      // [*]
      && (!cellMined(line+1, place) || !cellMined(line+2, place))
      // Left
      // [*][*][?]
      && (!cellMined(line, place-1) || !cellMined(line, place-2))
      // Left and Right
      // [*][?][*]
      && (!cellMined(line, place-1) || !cellMined(line, place+1))
      // Top and Bottom
      // [*]
      // [?]
      // [*]
      && (!cellMined(line-1, place) || !cellMined(line+1, place))
      ;
  }

  /**
   * Проверка, установлена ли в ячейке мина.
   * @param line Ряд.
   * @param place Место.
   * @return TRUE, если мина установлена в ячейке, FALSE в ином случае.
   */
  private boolean cellMined(int line, int place) {
    return cellInRange(line, place) ? cells[line][place].isMine() : false;
  }

  /**
   * Проверка, существует ли ячейка.
   * @param line Ряд.
   * @param place Место.
   * @return TRUE, если ячейка существует, FALSE в ином случае.
   */
  private boolean cellInRange(int line, int place) {
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
  private int nearMinesCount(int line, int place) {
    return (cellMined(line-1, place) ? 1 : 0)
      + (cellMined(line-1, place+1) ? 1 : 0)
      + (cellMined(line, place+1) ? 1 : 0)
      + (cellMined(line+1, place+1) ? 1 : 0)
      + (cellMined(line+1, place) ? 1 : 0)
      + (cellMined(line+1, place-1) ? 1 : 0)
      + (cellMined(line, place-1) ? 1 : 0)
      + (cellMined(line-1, place-1) ? 1 : 0);
  }

  /**
   * Рисование поля.
   */
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

  /**
   * Рисование ячейки.
   * @param cell Ссылка на объект ячейки.
   * @param count Количество рядом находящихся заминированных ячеек.
   */
  public void drawCell(CCell cell, int count) {
    cell.draw(System.out, count);
  }

  /**
   * Оповещение взрыва.
   */
  public void drawBang() {
  }

  /**
   * Оповещение победы.
   */
  public void drawCongratulation() {
  }
}
