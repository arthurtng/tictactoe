package edu.uob;

import edu.uob.OXOMoveException.*;

class OXOController {
  OXOModel gameModel;

  public OXOController(OXOModel model) {
    gameModel = model;
    if (gameModel.getNumberOfRows() > 0 && gameModel.getNumberOfColumns() > 0){
      winCheck(gameModel);
    }
    drawCheck(gameModel);
  }

  public void handleIncomingCommand(String command) throws OXOMoveException {

    if (gameModel.isGameDrawn() || gameModel.getWinner() != null){
      return;
    }

    // Parse command
    validateIdentifier(command);
    int row = command.toLowerCase().charAt(0) - 'a';
    int col = command.charAt(1) - '1';
    validateCell(row, col);

    // Draw to board
    int playerNumber = gameModel.getCurrentPlayerNumber();
    OXOPlayer player = gameModel.getPlayerByNumber(playerNumber);
    gameModel.setCellOwner(row, col, player);

    // Win check
    winCheck(gameModel);
    drawCheck(gameModel);

    // Next player turn
    int next = (playerNumber + 1) % gameModel.getNumberOfPlayers();
    gameModel.setCurrentPlayerNumber(next);
  }

  public void addRow() {
    if (gameModel.getWinner() != null || gameModel.isGameDrawn()){
      return;
    }
    gameModel.addRow();
  }

  public void removeRow() {
    if (gameModel.getWinner() != null || gameModel.isGameDrawn()){
      return;
    }
    gameModel.removeRow();
    drawCheck(gameModel);
  }

  public void addColumn() {
    if (gameModel.getWinner() != null || gameModel.isGameDrawn()){
      return;
    }
    gameModel.addColumn();
  }

  public void removeColumn() {
    if (gameModel.getWinner() != null || gameModel.isGameDrawn()){
      return;
    }
    gameModel.removeColumn();
    drawCheck(gameModel);
  }

  public void increaseWinThreshold() {
    if (gameModel.getWinner() != null || gameModel.isGameDrawn()){
      return;
    }
    gameModel.setWinThreshold(gameModel.getWinThreshold()+1);
  }

  public void decreaseWinThreshold() {
    if (gameModel.getWinner() != null || gameModel.isGameDrawn()){
      return;
    }
    if(gameModel.getWinThreshold() > 0) {
      gameModel.setWinThreshold(gameModel.getWinThreshold() - 1);
      winCheck(gameModel);
      drawCheck(gameModel);
    }
  }

  private void validateIdentifier(String command) throws OXOMoveException{

    //Invalid length
    int len = command.length();
    if(len != 2){
      throw new InvalidIdentifierLengthException(len);
    }

    //Invalid character
    if(!Character.isLetter(command.charAt(0))){
      throw new InvalidIdentifierCharacterException(RowOrColumn.ROW, command.charAt(0));
    }
    if(!Character.isDigit(command.charAt(1))){
      throw new InvalidIdentifierCharacterException(RowOrColumn.COLUMN, command.charAt(1));
    }

  }

  private void validateCell(int row, int col) throws OXOMoveException {

    // Outside Cell Range
    if(row > gameModel.getNumberOfRows()-1){
      throw new OutsideCellRangeException(RowOrColumn.ROW, row);
    }
    else if(col > gameModel.getNumberOfColumns()-1 || col == -1){
      throw new OutsideCellRangeException(RowOrColumn.COLUMN, col+1);
    }

    // Cell Already Taken
    if (!gameModel.isEmpty(row, col)){
      throw new CellAlreadyTakenException(row, col);
    }

  }

 private void drawCheck(OXOModel gameModel){
    boolean full = true;
    for (int y=0; y < gameModel.getNumberOfRows(); y++){
      for (int x=0; x < gameModel.getNumberOfColumns(); x++){
        if (!ownerCheck(gameModel, y, x)){
          full = false;
        }
      }
    }
    if (full){
      gameModel.setGameDrawn();
      return;
    }
    if (gameModel.getNumberOfWinners() > 1 || gameModel.getWinThreshold() == 0){
      gameModel.setGameDrawn();
    }
 }

  private boolean ownerCheck(OXOModel gameModel, int row, int col){
    for (int i=0; i < gameModel.getNumberOfPlayers(); i++){
      if(gameModel.getCellOwner(row, col) == gameModel.getPlayerByNumber(i)){
        return true;
      }
    }
    return false;
  }


  private void winCheck(OXOModel gameModel){

    int winnerNumber = -1;
    int horizontalResult = horizontalCheck(gameModel);
    if(horizontalResult != -1){
      winnerNumber = horizontalResult;
    }
    int verticalResult = verticalCheck(gameModel);
    if(verticalResult != -1){
      winnerNumber = verticalResult;
    }
    int diagonalResult = diagonalCheck(gameModel);
    if(diagonalResult != -1){
      winnerNumber = diagonalResult;
    }
    int antiDiagonalResult = antiDiagonalCheck(gameModel);
    if(antiDiagonalResult != -1){
      winnerNumber = antiDiagonalResult;
    }
    if (gameModel.getNumberOfWinners() == 1){
      gameModel.setWinner(gameModel.getPlayerByNumber(winnerNumber));
    }
  }

  private int horizontalCheck(OXOModel gameModel){
    int winnerNumber = -1;
    for (int y=0; y < gameModel.getNumberOfRows(); y++){
      int streak = 0;
      OXOPlayer current = null;
      // Check whether cell belongs to one of the players
      if (ownerCheck(gameModel, y, 0)) {
        current = gameModel.getCellOwner(y, 0);
      }
      for (int x = 0; x < gameModel.getNumberOfColumns(); x++) {
        if (gameModel.getCellOwner(y, x) == current) {
          streak++;
        } else if (ownerCheck(gameModel, y, x)) {
            streak = 1;
            current = gameModel.getCellOwner(y, x);
        } else {
            streak = 0;
        }
        if (streak == gameModel.getWinThreshold()) {
          gameModel.addWinner(current);
          winnerNumber = gameModel.getPlayerNumber(current);
        }
      }
    }
    return winnerNumber;
  }

  private int verticalCheck(OXOModel gameModel){
    int winnerNumber = -1;
    for (int x=0; x < gameModel.getNumberOfColumns(); x++){
      int streak = 0;
      OXOPlayer current = null;
      if (ownerCheck(gameModel, 0, x)) {
        current = gameModel.getCellOwner(0, x);
      }
        for (int y = 0; y < gameModel.getNumberOfRows(); y++) {
          if (gameModel.getCellOwner(y, x) == current) {
            streak++;
          } else if (ownerCheck(gameModel, y, x)){
            streak = 1;
            current = gameModel.getCellOwner(y, x);
          } else {
            streak = 0;
          }
          if (streak == gameModel.getWinThreshold()) {
            gameModel.addWinner(current);
            winnerNumber = gameModel.getPlayerNumber(current);
          }
        }

    }
    return winnerNumber;
  }


  private int diagonalCheck(OXOModel gameModel){

    int winnerNumber = -1;

    for (int row = 0; row < gameModel.getNumberOfRows(); row++) {
      for (int col = 0; col < gameModel.getNumberOfColumns(); col++){
        OXOPlayer current = null;
        if (ownerCheck(gameModel, row, col)) {
          current = gameModel.getCellOwner(row, col);
        }
        int streak = 0;
        for (int x = col, y = row; x < gameModel.getNumberOfColumns() && y < gameModel.getNumberOfRows(); x++, y++) {
          if (gameModel.getCellOwner(y, x) == current) {
            streak++;
          } else if (ownerCheck(gameModel, y, x)) {
            streak = 1;
            current = gameModel.getCellOwner(y, x);
          } else {
            streak = 0;
          }
          if (streak == gameModel.getWinThreshold()) {
            gameModel.addWinner(current);
            winnerNumber = gameModel.getPlayerNumber(current);
          }
        }
      }
    }
    return winnerNumber;
  }

  private int antiDiagonalCheck(OXOModel gameModel) {

    int winnerNumber = -1;

    for (int row = 0; row < gameModel.getNumberOfRows(); row++) {
      for (int col = gameModel.getNumberOfColumns()-1; col >= 0; col--) {
        OXOPlayer current = null;
        if (ownerCheck(gameModel, row, col)) {
          current = gameModel.getCellOwner(row, col);
        }
        int streak = 0;
        for (int y = row, x = col; y < gameModel.getNumberOfRows() && x >= 0; y++, x--) {
          if (gameModel.getCellOwner(y, x) == current) {
            streak++;
          } else if (ownerCheck(gameModel, y, x)) {
            streak = 1;
            current = gameModel.getCellOwner(y, x);
          } else {
            streak = 0;
          }
          if (streak == gameModel.getWinThreshold()) {
            gameModel.addWinner(current);
            winnerNumber = gameModel.getPlayerNumber(current);
          }
        }
      }
    }
    return winnerNumber;
  }

}
