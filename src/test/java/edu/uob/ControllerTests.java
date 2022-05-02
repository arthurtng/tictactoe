package edu.uob;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// PLEASE READ:
// The tests in this file will fail by default for a template skeleton, your job is to pass them
// and maybe write some more, read up on how to write tests at
// https://junit.org/junit5/docs/current/user-guide/#writing-tests
final class ControllerTests {
  OXOModel model;
  OXOController controller;

  // create your standard 3*3 OXO board (where three of the same symbol in a line wins) with the X
  // and O player
  private static OXOModel createStandardModel() {
    OXOModel model = new OXOModel(3, 3, 3);
    model.addPlayer(new OXOPlayer('X'));
    model.addPlayer(new OXOPlayer('O'));
    return model;
  }

  // we make a new board for every @Test (i.e. this method runs before every @Test test case)
  @BeforeEach
  void setup() {
    model = createStandardModel();
    controller = new OXOController(model);
  }

  // here's a basic test for the `controller.handleIncomingCommand` method
  @Test
  void testHandleIncomingCommand() throws OXOMoveException {
    // take note of whose gonna made the first move
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a1");

    // A move has been made for A1 (i.e. the [0,0] cell on the board), let's see if that cell is
    // indeed owned by the player
    assertEquals(firstMovingPlayer, controller.gameModel.getCellOwner(0, 0));
  }

  // here's a complete game where we find out if someone won
  @Test
  void testBasicWinWithA1A2A3() throws OXOMoveException {
    // take note of whose gonna made the first move
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a1");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("a2");
    controller.handleIncomingCommand("b2");
    controller.handleIncomingCommand("a3");

    // OK, so A1, A2, A3 is a win and that last A3 move is made by the first player (players
    // alternative between moves) let's make an assertion to see whether the first moving player is
    // the winner here
    assertEquals(
        firstMovingPlayer,
        model.getWinner(),
        "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  // Column Wins
  @Test
  void testColumnWin() throws OXOMoveException {
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a2");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("b2");
    controller.handleIncomingCommand("b3");
    controller.handleIncomingCommand("c2");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testColumn2Win() throws OXOMoveException {
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a1");
    controller.handleIncomingCommand("b2");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("b3");
    controller.handleIncomingCommand("c1");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testColumn3Win() throws OXOMoveException {
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a3");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("b3");
    controller.handleIncomingCommand("b2");
    controller.handleIncomingCommand("c3");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  // Row Wins
  @Test
  void testRowWin() throws OXOMoveException {
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a1");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("a2");
    controller.handleIncomingCommand("b3");
    controller.handleIncomingCommand("a3");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testRow2Win() throws OXOMoveException {
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("b2");
    controller.handleIncomingCommand("a1");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("a3");
    controller.handleIncomingCommand("b3");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testRow3Win() throws OXOMoveException {
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("c3");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("c2");
    controller.handleIncomingCommand("b3");
    controller.handleIncomingCommand("c1");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  // Diagonal Win
  @Test
  void testDiagWin() throws OXOMoveException {
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a1");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("b2");
    controller.handleIncomingCommand("b3");
    controller.handleIncomingCommand("c3");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  // AntiDiagonal Win
  @Test
  void testAntiDiagWin() throws OXOMoveException {
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a3");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("b2");
    controller.handleIncomingCommand("b3");
    controller.handleIncomingCommand("c1");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  // Draw
  @Test
  void testBasicDraw() throws OXOMoveException {

    controller.handleIncomingCommand("a1");
    controller.handleIncomingCommand("a2");
    controller.handleIncomingCommand("a3");
    controller.handleIncomingCommand("b2");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("c1");
    controller.handleIncomingCommand("c2");
    controller.handleIncomingCommand("b3");
    controller.handleIncomingCommand("c3");

    System.out.println(model.getNumberOfWinners());
    assertTrue(model.isGameDrawn(), "Expected draw but wasn't.");
  }

  // Win threshold = 2 on 3x3 board
  @Test
  void testDown2ThresholdWin() throws OXOMoveException {
    controller.decreaseWinThreshold();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a1");
    controller.handleIncomingCommand("b2");
    controller.handleIncomingCommand("b1");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testDown2Threshold2Win() throws OXOMoveException {
    controller.decreaseWinThreshold();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("c1");
    controller.handleIncomingCommand("a1");
    controller.handleIncomingCommand("b1");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testDown2Threshold3Win() throws OXOMoveException {
    controller.decreaseWinThreshold();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("b2");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("a2");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testDown2Threshold4Win() throws OXOMoveException {
    controller.decreaseWinThreshold();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("b2");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("c2");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testDown2Threshold5Win() throws OXOMoveException {
    controller.decreaseWinThreshold();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("b3");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("a3");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testDown2Threshold6Win() throws OXOMoveException {
    controller.decreaseWinThreshold();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("b3");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("c3");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testRow2ThresholdWin() throws OXOMoveException {
    controller.decreaseWinThreshold();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a1");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("a2");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testRow2Threshold2Win() throws OXOMoveException {
    controller.decreaseWinThreshold();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a3");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("a2");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testRow2Threshold3Win() throws OXOMoveException {
    controller.decreaseWinThreshold();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("c1");
    controller.handleIncomingCommand("b2");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testRow2Threshold4Win() throws OXOMoveException {
    controller.decreaseWinThreshold();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("b3");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("b2");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testRow2Threshold5Win() throws OXOMoveException {
    controller.decreaseWinThreshold();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("c3");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("c2");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testRow2Threshold6Win() throws OXOMoveException {
    controller.decreaseWinThreshold();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("c1");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("c2");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testDiag2ThresholdWin() throws OXOMoveException {
    controller.decreaseWinThreshold();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a1");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("b2");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testDiag2Threshold2Win() throws OXOMoveException {
    controller.decreaseWinThreshold();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a2");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("b3");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testDiag2Threshold3Win() throws OXOMoveException {
    controller.decreaseWinThreshold();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("b3");
    controller.handleIncomingCommand("c2");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testDiag2Threshold4Win() throws OXOMoveException {
    controller.decreaseWinThreshold();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("c3");
    controller.handleIncomingCommand("b3");
    controller.handleIncomingCommand("b2");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testAntiDiag2ThresholdWin() throws OXOMoveException {
    controller.decreaseWinThreshold();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a3");
    controller.handleIncomingCommand("b3");
    controller.handleIncomingCommand("b2");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testAntiDiag2Threshold2Win() throws OXOMoveException {
    controller.decreaseWinThreshold();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("c1");
    controller.handleIncomingCommand("b3");
    controller.handleIncomingCommand("b2");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testAntiDiag2Threshold3Win() throws OXOMoveException {
    controller.decreaseWinThreshold();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a2");
    controller.handleIncomingCommand("b3");
    controller.handleIncomingCommand("b1");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testAntiDiag2Threshold4Win() throws OXOMoveException {
    controller.decreaseWinThreshold();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("b3");
    controller.handleIncomingCommand("b2");
    controller.handleIncomingCommand("c2");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }


  // 4 X 4 Win
  @Test
  void testFourWin() throws OXOMoveException {

    controller.addColumn();
    controller.addRow();
    controller.increaseWinThreshold();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a1");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("a2");
    controller.handleIncomingCommand("b2");
    controller.handleIncomingCommand("a3");

    assertNotEquals(firstMovingPlayer,
            model.getWinner(),
            "Winner not expected but %s won already".formatted(firstMovingPlayer.getPlayingLetter()));

    controller.handleIncomingCommand("b3");
    controller.handleIncomingCommand("a4");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  // 9 X 9 Win
  @Test
  void testNineWin() throws OXOMoveException {

    for (int i=0; i<6; i++) {
      controller.addColumn();
      controller.addRow();
      controller.increaseWinThreshold();
    }
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a1");
    controller.handleIncomingCommand("a2");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("b2");
    controller.handleIncomingCommand("c1");

    assertNotEquals(firstMovingPlayer,
            model.getWinner(),
            "Winner not expected but %s won already".formatted(firstMovingPlayer.getPlayingLetter()));

    controller.handleIncomingCommand("c2");
    controller.handleIncomingCommand("d1");
    controller.handleIncomingCommand("d2");
    controller.handleIncomingCommand("e1");
    controller.handleIncomingCommand("e2");
    controller.handleIncomingCommand("f1");
    controller.handleIncomingCommand("f2");
    controller.handleIncomingCommand("g1");
    controller.handleIncomingCommand("g2");
    controller.handleIncomingCommand("h1");
    controller.handleIncomingCommand("h2");
    controller.handleIncomingCommand("i1");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  // 4 x 3 Win Diagonal
  @Test
  void testFourByThreeDiagWin() throws OXOMoveException {

    controller.addColumn();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a1");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("b2");
    controller.handleIncomingCommand("b3");
    controller.handleIncomingCommand("c3");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  // 4 x 3 Win AntiDiagonal
  @Test
  void testFourByThreeAntiDiagWin() throws OXOMoveException {

    controller.addColumn();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a4");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("b3");
    controller.handleIncomingCommand("b2");
    controller.handleIncomingCommand("c2");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  // 4 x 3 Win AntiDiagonal 2
  @Test
  void testFourByThreeAntiDiagTwoWin() throws OXOMoveException {

    controller.addColumn();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a3");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("b2");
    controller.handleIncomingCommand("b3");
    controller.handleIncomingCommand("c1");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }


  // Unaligned WinThreshold and Board size: Win threshold of 3 on 8x9 board
  @Test
  void testUnaligned() throws OXOMoveException {

    for (int i=0; i<8; i++) {
      controller.addColumn();
      controller.addRow();
    }
    controller.addRow();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a1");
    controller.handleIncomingCommand("a2");
    controller.handleIncomingCommand("b2");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("c3");

    assertEquals(firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));

  }

  // Unaligned WinThreshold and Board size: Win threshold of 9 on 3x3 board
  @Test
  void testUnaligned2() throws OXOMoveException {

    for (int i=0; i<9; i++) {
      controller.increaseWinThreshold();
    }

    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a1");
    controller.handleIncomingCommand("a2");
    controller.handleIncomingCommand("b2");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("c3");

    assertNotEquals(firstMovingPlayer,
            model.getWinner(),
            "Winner not expected but %s won".formatted(firstMovingPlayer.getPlayingLetter()));

  }

  // Three players
  @Test
  void testThreePlayers() throws OXOMoveException {
    model.addPlayer(new OXOPlayer('$'));
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a1");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("c1");
    controller.handleIncomingCommand("a2");
    controller.handleIncomingCommand("b2");
    controller.handleIncomingCommand("c2");
    controller.handleIncomingCommand("a3");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  // Five players
  @Test
  void testFivePlayers() throws OXOMoveException {
    model.addPlayer(new OXOPlayer('$'));
    model.addPlayer(new OXOPlayer('!'));
    model.addPlayer(new OXOPlayer('%'));
    for (int i = 0; i < 6; i++){
      model.addRow();
      model.addColumn();
    }
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a1");
    controller.handleIncomingCommand("a2");
    controller.handleIncomingCommand("a3");
    controller.handleIncomingCommand("a4");
    controller.handleIncomingCommand("a5");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("b2");
    controller.handleIncomingCommand("b3");
    controller.handleIncomingCommand("b4");
    controller.handleIncomingCommand("b5");
    controller.handleIncomingCommand("c1");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  // Five players 2
  @Test
  void testFivePlayers2() throws OXOMoveException {
    model.addPlayer(new OXOPlayer('$'));
    model.addPlayer(new OXOPlayer('!'));
    model.addPlayer(new OXOPlayer('%'));
    for (int i = 0; i < 6; i++){
      model.addRow();
      model.addColumn();
    }
    controller.handleIncomingCommand("a1");
    OXOPlayer secondMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a2");
    controller.handleIncomingCommand("a3");
    controller.handleIncomingCommand("a4");
    controller.handleIncomingCommand("a5");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("b2");
    controller.handleIncomingCommand("b3");
    controller.handleIncomingCommand("b4");
    controller.handleIncomingCommand("b5");
    controller.handleIncomingCommand("d1");
    controller.handleIncomingCommand("c2");

    assertEquals(
            secondMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(secondMovingPlayer.getPlayingLetter()));
  }

  // Adding players after game commenced
  @Test
  void testAddPlayers() throws OXOMoveException {

    for (int i = 0; i < 6; i++){
      model.addRow();
      model.addColumn();
    }
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a1");
    controller.handleIncomingCommand("a2");

    model.addPlayer(new OXOPlayer('$'));
    model.addPlayer(new OXOPlayer('!'));

    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("b2");
    controller.handleIncomingCommand("a3");
    controller.handleIncomingCommand("a4");
    controller.handleIncomingCommand("c1");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  // Players with same playing chars still work
  @Test
  void testSamePlayers() throws OXOMoveException {
    model.addPlayer(new OXOPlayer('X'));
    model.addPlayer(new OXOPlayer('O'));
    model.addRow();
    model.addColumn();

    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a1");
    controller.handleIncomingCommand("a2");
    controller.handleIncomingCommand("a3");
    controller.handleIncomingCommand("a4");
    controller.handleIncomingCommand("b2");
    controller.handleIncomingCommand("b1");
    controller.handleIncomingCommand("b3");
    controller.handleIncomingCommand("b4");
    controller.handleIncomingCommand("c3");


    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  // Win Threshold of 0
  @Test
  void testZeroWinThreshold(){
    controller.decreaseWinThreshold();
    controller.decreaseWinThreshold();
    controller.decreaseWinThreshold();

    assertTrue(model.isGameDrawn(), "Expected draw but wasn't.");
  }

  // Win Threshold of 1
  @Test
  void testOneWinThreshold() throws OXOMoveException {
    controller.decreaseWinThreshold();
    controller.decreaseWinThreshold();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a1");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testOneWinThreshold2() throws OXOMoveException {
    controller.decreaseWinThreshold();
    controller.decreaseWinThreshold();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a2");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testOneWinThreshold3() throws OXOMoveException {
    controller.decreaseWinThreshold();
    controller.decreaseWinThreshold();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a3");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testOneWinThreshold4() throws OXOMoveException {
    controller.decreaseWinThreshold();
    controller.decreaseWinThreshold();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("b1");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testOneWinThreshold5() throws OXOMoveException {
    controller.decreaseWinThreshold();
    controller.decreaseWinThreshold();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("b2");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testOneWinThreshold6() throws OXOMoveException {
    controller.decreaseWinThreshold();
    controller.decreaseWinThreshold();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("b3");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testOneWinThreshold7() throws OXOMoveException {
    controller.decreaseWinThreshold();
    controller.decreaseWinThreshold();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("c1");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testOneWinThreshold8() throws OXOMoveException {
    controller.decreaseWinThreshold();
    controller.decreaseWinThreshold();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("c2");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  @Test
  void testOneWinThreshold9() throws OXOMoveException {
    controller.decreaseWinThreshold();
    controller.decreaseWinThreshold();
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("c3");

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  // Decrease to Win threshold of 1
  @Test
  void testDecOneWinThreshold() throws OXOMoveException {
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    controller.handleIncomingCommand("a1");
    controller.decreaseWinThreshold();
    controller.decreaseWinThreshold();

    assertEquals(
            firstMovingPlayer,
            model.getWinner(),
            "Winner was expected to be %s but wasn't".formatted(firstMovingPlayer.getPlayingLetter()));
  }

  // 0x0 board
  @Test
  void testZeroBoard(){

    for (int i=0; i<3; i++) {
      controller.removeColumn();
      controller.removeRow();
    }
    assertTrue(model.isGameDrawn(), "Expected draw but wasn't.");

  }

  // Exception Testing
  @Test
  void testExceptions() throws OXOMoveException {

    assertThrows(OXOMoveException.InvalidIdentifierLengthException.class, ()-> controller.handleIncomingCommand("aa1"));
    assertThrows(OXOMoveException.InvalidIdentifierLengthException.class, ()-> controller.handleIncomingCommand(""));

    assertThrows(OXOMoveException.InvalidIdentifierCharacterException.class, ()-> controller.handleIncomingCommand("aa"));
    assertThrows(OXOMoveException.InvalidIdentifierCharacterException.class, ()-> controller.handleIncomingCommand("1a"));
    assertThrows(OXOMoveException.InvalidIdentifierCharacterException.class, ()-> controller.handleIncomingCommand("11"));

    assertThrows(OXOMoveException.OutsideCellRangeException.class, ()-> controller.handleIncomingCommand("a9"));
    assertThrows(OXOMoveException.OutsideCellRangeException.class, ()-> controller.handleIncomingCommand("a0"));
    assertThrows(OXOMoveException.OutsideCellRangeException.class, ()-> controller.handleIncomingCommand("d1"));
    assertThrows(OXOMoveException.OutsideCellRangeException.class, ()-> controller.handleIncomingCommand("d9"));
    for (int i = 0; i < 6; i++){
      model.addRow();
      model.addColumn();
    }
    assertThrows(OXOMoveException.OutsideCellRangeException.class, ()-> controller.handleIncomingCommand("j1"));

    controller.handleIncomingCommand("a1");
    assertThrows(OXOMoveException.CellAlreadyTakenException.class, ()-> controller.handleIncomingCommand("a1"));
    controller.handleIncomingCommand("c3");
    assertThrows(OXOMoveException.CellAlreadyTakenException.class, ()-> controller.handleIncomingCommand("c3"));
  }



}
