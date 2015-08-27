package net.patttern.console.minesweeper.proto;

/**
 * Created by pattern on 26.08.15.
 */
public interface ILogic {
  public Cell[][] cells = new Cell[0][];
  public int boardWidth = 0;
  public int boardHeight = 0;

  void loadBoard(Cell[][] cells);

  default boolean shouldBang(int x, int  y) {
    final Cell selected = this.cells[x][y];
    return selected.isBomb() && !selected.isSuggestBomb();
  }

  default boolean finish() {
    boolean finish = false;
    for (Cell[] row: cells) {
      for (Cell cell: row) {
        finish = (
          (cell.isSuggestBomb() && cell.isBomb()) ||
            (cell.isSuggestEmpty() && !cell.isBomb())
        );
      }
    }
    return finish;
  }

  default void suggest(int x, int y, boolean bomb) {
    if (bomb) {
      this.cells[x][y].suggestBomb();
    } else {
      this.cells[x][y].suggestEmpty();
    }
  }

  default void setBoardWidth(int w) {
//    boardWidth = w;
  }

  default int getBoardWidth() {
    return boardWidth;
  }

  default void setBoardHeight(int h) {
//    boardHeight = h;
  }

  default int getBoardHeight() {
    return boardHeight;
  }
}
