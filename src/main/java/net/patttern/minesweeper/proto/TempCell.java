package net.patttern.minesweeper.proto;

import java.util.Objects;

/**
 * Created by pattern on 01.09.15.
 */
public class TempCell {
  private int id;
  private int line;
  private int place;
  private int nearMines;

  public TempCell(int id, int line, int place) {
    this.id = id;
    this.line = line;
    this.place = place;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getLine() {
    return line;
  }

  public void setLine(int line) {
    this.line = line;
  }

  public int getPlace() {
    return place;
  }

  public void setPlace(int place) {
    this.place = place;
  }

  public int getNearMines() {
    return nearMines;
  }

  public void setNearMines(int nearMines) {
    this.nearMines = nearMines;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TempCell tempCell = (TempCell) o;
    return id == tempCell.id &&
      line == tempCell.line &&
      place == tempCell.place;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, line, place);
  }
}
