package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.integers;

import model.GameState.Player;
import org.junit.jupiter.api.Test;

class GameTest {

  @Test
  void testStringRepresentation() {
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
  void testCannotMoveOffTurn() {
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
  void testPlayersCanOnlyMoveOwnPieces() {
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
  void testPawnMovement() {
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
}