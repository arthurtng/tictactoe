package edu.uob;

import java.util.*;

class OXOModel {
  private final ArrayList<ArrayList<OXOPlayer>> cells;
  private final ArrayList<OXOPlayer> players;
  private int currentPlayerNumber;
  private OXOPlayer winner;
  private boolean gameDrawn;
  private int winThreshold;
  private final int maximumBoardSize;
  private final int minimumBoardSize;
  private final ArrayList<OXOPlayer> winners;

  public OXOModel(int numberOfRows, int numberOfColumns, int winThresh) {
    winThreshold = winThresh;
    cells = new ArrayList<>();
    for (int y=0; y < numberOfRows; y++){
      cells.add(new ArrayList<>(numberOfColumns));
      for (int x =0; x < numberOfColumns; x++){
        cells.get(y).add(new OXOPlayer(' '));
      }
    }
    players = new ArrayList<>();
    winners = new ArrayList<>();
    maximumBoardSize = 9;
    minimumBoardSize = 0;
  }

  public int getNumberOfWinners(){
    return winners.size();
  }

  public void addWinner(OXOPlayer player){
    if (player == null){
      return;
    }
    for (OXOPlayer oxoPlayer : winners) {
      if (player == oxoPlayer) {
        return;
      }
    }
    winners.add(player);
  }

  public int getNumberOfPlayers() {
    return players.size();
  }

  public void addPlayer(OXOPlayer player) {
    players.add(player);
  }

  public void addRow() {
    // Maximum board size 9x9
    if (cells.size() == maximumBoardSize){
      return;
    }
    cells.add(new ArrayList<>());
    for (int i =0; i < this.getNumberOfColumns(); i++){
      cells.get(cells.size()-1).add(new OXOPlayer(' '));
    }
  }

  public void addColumn() {
    // Maximum board size 9x9
    if (cells.size() == 0 || cells.get(0).size() == maximumBoardSize){
      return;
    }
    for (ArrayList<OXOPlayer> cell : cells) {
      cell.add(new OXOPlayer(' '));
    }
  }

  public void removeRow() {
    // Minimum board size 0x0
    if (cells.size() == minimumBoardSize){
      return;
    }
    cells.remove(cells.size()-1);
  }

  public void removeColumn() {
    // Minimum board size 0x0
    if (cells.get(0).size() == minimumBoardSize){
      return;
    }
    for (ArrayList<OXOPlayer> cell : cells) {
      cell.remove(cell.size() - 1);
    }
  }

  public OXOPlayer getPlayerByNumber(int number) {
    if (number < 0 || number > players.size()){
      return null;
    }
    return players.get(number);
  }

  public int getPlayerNumber(OXOPlayer player) {
    for (int i=0; i<getNumberOfPlayers(); i++){
      if (this.getPlayerByNumber(i) == player){
        return i;
      }
    }
    return -1;
  }

  public OXOPlayer getWinner() {
    return winner;
  }

  public void setWinner(OXOPlayer player) {
    winner = player;
  }

  public int getCurrentPlayerNumber() {
    return currentPlayerNumber;
  }

  public void setCurrentPlayerNumber(int playerNumber) {
    currentPlayerNumber = playerNumber;
  }

  public int getNumberOfRows() {
    if(cells == null){
      return 0;
    }
    return cells.size();
  }

  public int getNumberOfColumns() {
    if(getNumberOfRows() == 0){
      return 0;
    }
    return cells.get(0).size();
  }

  public OXOPlayer getCellOwner(int rowNumber, int colNumber) {
    return cells.get(rowNumber).get(colNumber);
  }

  public void setCellOwner(int rowNumber, int colNumber, OXOPlayer player) {
    cells.get(rowNumber).set(colNumber, player);
  }

  public void setWinThreshold(int winThresh) {
    winThreshold = winThresh;
  }

  public int getWinThreshold() {
    return winThreshold;
  }

  public void setGameDrawn() {
    gameDrawn = true;
  }

  public boolean isGameDrawn() {
    return gameDrawn;
  }

  public boolean isEmpty(int row, int col){
    boolean existingPlayer = true;
    for (int i=0; i < getNumberOfPlayers(); i++){
      if (getCellOwner(row, col) == players.get(i)){
        existingPlayer = false;
      }
    }
    return existingPlayer;
  }

}
