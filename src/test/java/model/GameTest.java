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
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

class GameTest {

  @Test
  void gameStringRepresentation() {
    Game game = GameBuilder.fromStandardRules().build();
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
    Game game = GameBuilder.fromStandardRules().build();

    // Get initial
    GameState gs1 = game.getCurrentGameState();

    // Attempt to move off turn for white
    game.movePiece(Player.gote, Position.of(1, 3), Position.of(1, 4), false);

    // Should be the same
    assertEquals(gs1, game.getCurrentGameState());

    // Try again
    game.movePiece(Player.gote, Position.of(1, 3), Position.of(1, 4), false);

    // Should still be the same
    assertEquals(gs1, game.getCurrentGameState());

    // Now lets move correctly with black
    game.movePiece(Player.sente, Position.of(1, 7), Position.of(1, 6), false);

    // Save new game state
    GameState gs2 = game.getCurrentGameState();

    // Sanity check, these shouldn't be the same
    assertNotEquals(gs1, gs2);

    // Now have black try to move again
    game.movePiece(Player.sente, Position.of(1, 6), Position.of(1, 5), false);

    // This shouldn't work
    assertEquals(gs2, game.getCurrentGameState());

    // Now have black try to move once more
    game.movePiece(Player.sente, Position.of(1, 6), Position.of(1, 5), false);

    // This still shouldn't work
    assertEquals(gs2, game.getCurrentGameState());
  }

  @Test
  void playersCanOnlyMoveOwnPieces() {
    Game game = GameBuilder.fromStandardRules().build();

    // Get initial
    GameState gs1 = game.getCurrentGameState();

    // Black tries to move white
    game.movePiece(Player.sente, Position.of(1, 3), Position.of(1, 4), false);

    // Shouldn't work
    assertEquals(gs1, game.getCurrentGameState());

    // Lets go to whites turn
    game.movePiece(Player.sente, Position.of(1, 7), Position.of(1, 6), false);

    // Get initial
    GameState gs2 = game.getCurrentGameState();

    // Sanity check, should've moved
    assertNotEquals(gs1, gs2);

    // White tries to move black
    game.movePiece(Player.gote, Position.of(1, 6), Position.of(1, 5), false);

    // Shouldn't work
    assertEquals(gs2, game.getCurrentGameState());
  }

  @Test
  void pawnMovement() {
    Game game = GameBuilder.fromStandardRules().build();

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
                  Game game = GameBuilder.fromGameState(initialGameState).build();
                  game.movePiece(game.getCurrentGameState().currentPlayer,
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
                  Game game = GameBuilder.fromGameState(initialGameState).build();
                  game.movePiece(game.getCurrentGameState().currentPlayer,
                      startPos.get(pieceToPos._1).get(),
                      pieceToAllowedPos,
                      false);
                  // We expect the game state to change for valid moves
                  assertNotEquals(initialGameState, game.getCurrentGameState());
                })));
  }

  @TestFactory
  Stream<DynamicTest> blackPieceMovementFromInitialPositions() {

    // Want to test from initial pos
    GameState initialGameState = GameStateBuilder
        .fromStandardInitialPositions()
        .build();

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
  Stream<DynamicTest> blackPieceMovementFromCenterOnOpenBoard() {

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

    Map<String, Set<Position>> allowedMovesFromStartingPosition =
        HashMap.of(
            "King", HashSet.of(
                Position.of(6, 5),
                Position.of(5, 5),
                Position.of(4, 5),
                Position.of(6, 4),
                Position.of(5, 4),
                Position.of(4, 4),
                Position.of(6, 6),
                Position.of(5, 6),
                Position.of(4, 6)),
            "Rook", HashSet.of(
                Position.of(1, 5),
                Position.of(2, 5),
                Position.of(3, 5),
                Position.of(4, 5),
                Position.of(6, 5),
                Position.of(7, 5),
                Position.of(8, 5),
                Position.of(9, 5),
                Position.of(5, 1),
                Position.of(5, 2),
                Position.of(5, 3),
                Position.of(5, 4),
                Position.of(5, 6),
                Position.of(5, 7),
                Position.of(5, 8),
                Position.of(5, 9)),
            "Bishop", HashSet.of(
                Position.of(1, 1),
                Position.of(2, 2),
                Position.of(3, 3),
                Position.of(4, 4),
                Position.of(6, 6),
                Position.of(7, 7),
                Position.of(8, 8),
                Position.of(9, 9),
                Position.of(9, 1),
                Position.of(8, 2),
                Position.of(7, 3),
                Position.of(6, 4),
                Position.of(4, 6),
                Position.of(3, 7),
                Position.of(2, 8),
                Position.of(1, 9)),
            "Gold", HashSet.of(
                Position.of(6, 4),
                Position.of(5, 4),
                Position.of(4, 4),
                Position.of(6, 5),
                Position.of(4, 5),
                Position.of(5, 6)),
            "Silver", HashSet.of(
                Position.of(6, 4),
                Position.of(5, 4),
                Position.of(4, 4),
                Position.of(4, 6),
                Position.of(6, 6)),
            "Knight", HashSet.of(
                Position.of(4, 3),
                Position.of(6, 3)),
            "Lance", HashSet.of(
                Position.of(5, 1),
                Position.of(5, 2),
                Position.of(5, 3),
                Position.of(5, 4)),
            "Pawn", HashSet.of(
                Position.of(5, 4)));

    return startingPosition.toJavaStream()
        .flatMap((pieceToStartingPos) -> {

          GameState initialGameState = GameStateBuilder.fromEmptyState()
              .setPiece(pieceToStartingPos._2,
                  getPieceFromString(pieceToStartingPos._1, Player.sente)).build();

          return getAllowedAndDissallowedMovementTests(
              initialGameState,
              HashMap.of(pieceToStartingPos._1, pieceToStartingPos._2),
              allowedMovesFromStartingPosition);
        });
  }

  private Piece getPieceFromString(String pieceName, Player owner) {
    switch (pieceName) {
      case "King":
        return new King(owner);
      case "Rook":
        return new Rook(owner);
      case "Bishop":
        return new Bishop(owner);
      case "Gold":
        return new Gold(owner);
      case "Silver":
        return new Silver(owner);
      case "Knight":
        return new Knight(owner);
      case "Lance":
        return new Lance(owner);
      case "Pawn":
        return new Pawn(owner);
      default:
        throw new RuntimeException("not a piece");
    }
  }

  @TestFactory
  Stream<DynamicTest> whitePieceMovementFromInitialPositions() {

    // Want to test from initial pos with white going first
    GameState initialGameState = GameStateBuilder
        .fromStandardInitialPositions()
        .setCurrentPlayer(Player.gote)
        .build();

    Map<String, Position> startingPosition =
        HashMap.of(
            "King", Position.of(5, 1),
            "Rook", Position.of(2, 2),
            "Bishop", Position.of(8, 2),
            "Gold", Position.of(4, 1),
            "Silver", Position.of(3, 1),
            "Knight", Position.of(2, 1),
            "Lance", Position.of(1, 1),
            "Pawn", Position.of(2, 3));

    Map<String, Set<Position>> allowedMovesFromStartingPosition =
        HashMap.of(
            "King", HashSet.of(
                Position.of(6, 2),
                Position.of(5, 2),
                Position.of(4, 2)),
            "Rook", HashSet.of(
                Position.of(1, 2),
                Position.of(3, 2),
                Position.of(4, 2),
                Position.of(5, 2),
                Position.of(6, 2),
                Position.of(7, 2)),
            "Bishop", HashSet.of(),
            "Gold", HashSet.of(
                Position.of(3, 2),
                Position.of(4, 2),
                Position.of(5, 2)),
            "Silver", HashSet.of(
                Position.of(2, 2),
                Position.of(3, 2),
                Position.of(4, 2)
            ),
            "Knight", HashSet.of(),
            "Lance", HashSet.of(
                Position.of(1, 2)
            ),
            "Pawn", HashSet.of(
                Position.of(2, 4)
            ));

    return getAllowedAndDissallowedMovementTests(
        initialGameState,
        startingPosition,
        allowedMovesFromStartingPosition);
  }

  // TODO test can move to enemy positions

  // TODO test cant move to friendly positions

  // TODO test cant move off board

  // TODO test promotion

  // TODO test check

  // TODO test checkmate

  // TODO test piece capture board to hand transfer

  // TODO test piece drop hand to board transfer

  // TODO test movement in place doesn't work / doesn't change turns

  // TODO test king movement regarding check

  // TODO test game states are completely independent and past states are properly preserved

  // TODO test draw?
}