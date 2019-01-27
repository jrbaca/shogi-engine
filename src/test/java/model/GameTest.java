package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.integers;

import io.vavr.collection.HashMap;
import io.vavr.collection.HashSet;
import io.vavr.collection.Map;
import io.vavr.collection.Set;
import java.util.stream.Stream;
import model.GameState.Player;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

class GameTest {

  @Test
  void gameStringRepresentation() {
    Game game = new Game();
    String gameStringRepresentation = game.getStringRepresentation();
    System.out.println(gameStringRepresentation);
    assertEquals(
        "[香][桂][銀][金][王][金][銀][桂][香]\n"
            + "[・][飛][・][・][・][・][・][角][・]\n"
            + "[歩][歩][歩][歩][歩][歩][歩][歩][歩]\n"
            + "[・][・][・][・][・][・][・][・][・]\n"
            + "[・][・][・][・][・][・][・][・][・]\n"
            + "[・][・][・][・][・][・][・][・][・]\n"
            + "[歩][歩][歩][歩][歩][歩][歩][歩][歩]\n"
            + "[・][角][・][・][・][・][・][飛][・]\n"
            + "[香][桂][銀][金][玉][金][銀][桂][香]",
        gameStringRepresentation);

  }

  @Test
  void cannotMoveOffTurn() {
    Game game = new Game();

    // Get initial
    GameState gs1 = game.getCurrentGameState();

    // Attempt to move off turn for white
    game.movePiece(Player.gote, Position.of(1, 1), Position.of(1, 2), false);

    // Should be the same
    assertEquals(gs1, game.getCurrentGameState());

    // Try again
    game.movePiece(Player.gote, Position.of(1, 1), Position.of(1, 2), false);

    // Should still be the same
    assertEquals(gs1, game.getCurrentGameState());

    // Now lets move correctly with black
    game.movePiece(Player.sente, Position.of(1, 9), Position.of(1, 8), false);

    // Save new game state
    GameState gs2 = game.getCurrentGameState();

    // Sanity check, these shouldn't be the same
    assertNotEquals(gs1, gs2);

    // Now have black try to move again
    game.movePiece(Player.sente, Position.of(1, 7), Position.of(1, 6), false);

    // This shouldn't work
    assertEquals(gs2, game.getCurrentGameState());

    // Now have black try to move once more
    game.movePiece(Player.sente, Position.of(1, 7), Position.of(1, 6), false);

    // This still shouldn't work
    assertEquals(gs2, game.getCurrentGameState());
  }

  @Test
  void playersCanOnlyMoveOwnPieces() {
    Game game = new Game();

    // Get initial
    GameState gs1 = game.getCurrentGameState();

    // Black tries to move white
    game.movePiece(Player.sente, Position.of(1, 1), Position.of(1, 2), false);

    // Shouldn't work
    assertEquals(gs1, game.getCurrentGameState());

    // Lets go to whites turn
    game.movePiece(Player.sente, Position.of(1, 9), Position.of(1, 8), false);

    // Get initial
    GameState gs2 = game.getCurrentGameState();

    // Sanity check, should've moved
    assertNotEquals(gs1, gs2);

    // White tries to move black
    game.movePiece(Player.gote, Position.of(1, 7), Position.of(1, 6), false);

    // Shouldn't work
    assertEquals(gs2, game.getCurrentGameState());
  }

  @Test
  void pawnMovement() {
    Game game = new Game();

    // Get initial
    final GameState gs1 = game.getCurrentGameState();

    // Try to move to positions that shouldn't work
    qt()
        .forAll(
            integers().from(1).upToAndIncluding(9),
            integers().from(1).upToAndIncluding(9))
        .assuming((i, j) -> i != 2 && j != 6) // This should work
        .check((i, j) -> {
          game.movePiece(Player.sente, Position.of(2, 7), Position.of(i, j), false);
          return game.getCurrentGameState().equals(gs1);
        });

    // Should still be the same
    assertEquals(gs1, game.getCurrentGameState());

    // Now legal movement allowed
    game.movePiece(Player.sente, Position.of(2, 7), Position.of(2, 6), false);

    // Should be different
    assertNotEquals(gs1, game.getCurrentGameState());
  }

  private Stream<DynamicTest> getAllowedAndDissallowedMovementTests(
      GameState initialGameState,
      Map<String, Position> startPos,
      Map<String, Set<Position>> allowedEndPos) {
    return Stream.concat(
        getDisallowedMovementTests(
            initialGameState,
            startPos,
            allowedEndPos),
        getAllowedMovementTests(
            initialGameState,
            startPos,
            allowedEndPos)
    );
  }

  private Stream<DynamicTest> getDisallowedMovementTests(
      GameState initialGameState,
      Map<String, Position> startPos,
      Map<String, Set<Position>> allowedEndPos) {
    return startPos.toJavaStream()
        .map(pieceToPos -> dynamicTest("Disallowed movement: " + pieceToPos._1,
            () -> qt()
                .forAll(
                    integers().from(1).upToAndIncluding(9),
                    integers().from(1).upToAndIncluding(9))
                .assuming((i, j) -> // Ensure we don't try to move to an allowed place
                    !allowedEndPos.get(pieceToPos._1).get()
                        .contains(Position.of(i, j)))
                .check((i, j) -> {
                  Game game = Game.fromGameState(initialGameState);
                  game.movePiece(Player.sente,
                      startPos.get(pieceToPos._1).get(),
                      Position.of(i, j),
                      false);
                  // We expect the game state to stay the same if invalid move
                  return game.getCurrentGameState().equals(initialGameState);
                })));
  }

  private Stream<DynamicTest> getAllowedMovementTests(
      GameState initialGameState,
      Map<String, Position> startPos,
      Map<String, Set<Position>> allowedEndPos) {
    return startPos.toJavaStream()
        .map(pieceToPos -> dynamicTest("Allowed movement: " + pieceToPos._1,
            () -> allowedEndPos.get(pieceToPos._1).get().toJavaStream()
                .forEach(pieceToAllowedPos -> {
                  Game game = Game.fromGameState(initialGameState);
                  game.movePiece(Player.sente,
                      startPos.get(pieceToPos._1).get(),
                      pieceToAllowedPos,
                      false);
                  // We expect the game state to change for valid moves
                  assertNotEquals(initialGameState, game.getCurrentGameState());
                })));
  }

  @TestFactory
  Stream<DynamicTest> pieceMovementFromInitialPositions() {

    GameState initialGameState = new Game().getCurrentGameState(); // Want to test from initial pos

    Map<String, Position> startingPosition =
        HashMap.of(
            "King", Position.of(5, 9),
            "Rook", Position.of(2, 8),
            "Bishop", Position.of(8, 8),
            "Gold", Position.of(4, 9),
            "Silver", Position.of(3, 9),
            "Knight", Position.of(2, 9),
            "Lance", Position.of(1, 9),
            "Pawn", Position.of(2, 7));

    Map<String, Set<Position>> allowedMovesFromStartingPosition =
        HashMap.of(
            "King", HashSet.of(
                Position.of(6, 8),
                Position.of(5, 8),
                Position.of(4, 8)),
            "Rook", HashSet.of(
                Position.of(1, 8),
                Position.of(3, 8),
                Position.of(4, 8),
                Position.of(5, 8),
                Position.of(6, 8),
                Position.of(7, 8)),
            "Bishop", HashSet.of(),
            "Gold", HashSet.of(
                Position.of(3, 8),
                Position.of(4, 8),
                Position.of(5, 8)),
            "Silver", HashSet.of(
                Position.of(2, 8),
                Position.of(3, 8),
                Position.of(4, 8)
            ),
            "Knight", HashSet.of(),
            "Lance", HashSet.of(
                Position.of(1, 8)
            ),
            "Pawn", HashSet.of(
                Position.of(2, 6)
            ));

    return getAllowedAndDissallowedMovementTests(
            initialGameState,
            startingPosition,
            allowedMovesFromStartingPosition);
  }

  @TestFactory
  Stream<DynamicTest> pieceMovementFromCenterOnOpenBoard() {

    Map<String, Position> startingPosition =
        HashMap.of(
            "King", Position.of(5, 5),
            "Rook", Position.of(5, 5),
            "Bishop", Position.of(5, 5),
            "Gold", Position.of(5, 5),
            "Silver", Position.of(5, 5),
            "Knight", Position.of(5, 5),
            "Lance", Position.of(5, 5),
            "Pawn", Position.of(5, 5));

    // TODO set the correct allowed positions
    Map<String, Set<Position>> allowedMovesFromStartingPosition =
        HashMap.of(
            "King", HashSet.of(
                Position.of(6, 5),
                Position.of(5, 8),
                Position.of(4, 8)),
            "Rook", HashSet.of(
                Position.of(1, 8),
                Position.of(3, 8),
                Position.of(4, 8),
                Position.of(5, 8),
                Position.of(6, 8),
                Position.of(7, 8)),
            "Bishop", HashSet.of(),
            "Gold", HashSet.of(
                Position.of(3, 8),
                Position.of(4, 8),
                Position.of(5, 8)),
            "Silver", HashSet.of(
                Position.of(2, 8),
                Position.of(3, 8),
                Position.of(4, 8)
            ),
            "Knight", HashSet.of(),
            "Lance", HashSet.of(
                Position.of(1, 8)
            ),
            "Pawn", HashSet.of(
                Position.of(2, 6)
            ));

    // TODO generate initial game state for each piece
    throw new RuntimeException("test not complete");
    // Want to test with piece on center of board
    //    GameState initialGameState = new Game().getCurrentGameState();

    //    return getAllowedAndDissallowedMovementTests(
    //            initialGameState,
    //            startingPosition,
    //            allowedMovesFromStartingPosition);
  }
}