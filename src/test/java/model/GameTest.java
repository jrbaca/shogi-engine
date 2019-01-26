package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}