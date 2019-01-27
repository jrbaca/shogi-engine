package model;

import io.vavr.control.Option;

/**
 * Main input processing class for the {@link ShogiEngine}.
 */
class Input {

  private Player player;
  private String verb;
  private Position posFrom;
  private Position posTo;
  private Boolean promotes;

  /**
   * Processes some input and returns a runnable instance.
   *
   * @param command to be processed
   * @return Runnable {@link Input}
   */
  static Input processCommand(String command) {
    Input input = new Input();

    String[] args = getArgsFromCommand(command);
    input.player = getPlayerFromArgs(args).get();
    input.verb = getVerbFromArgs(args).get();
    input.posFrom = getInitialPosFromArgs(args).get();
    input.posTo = getFinalPosFromArgs(args).get();
    input.promotes = getPromotesFromArgs(args).get();

    return input;
  }

  private static String[] getArgsFromCommand(String command) {
    return command.split(" ");
  }

  private static Option<Player> getPlayerFromArgs(String[] args) {
    if (args[0].equals("white")) {
      return Option.of(Player.gote);
    } else if (args[0].equals("black")) {
      return Option.of(Player.sente);
    } else {
      return Option.none();
    }
  }

  private static Option<String> getVerbFromArgs(String[] args) {
    return Option.of(args[1]);
  }

  private static Option<Position> getInitialPosFromArgs(String[] args) {
    return Option.of(
        Position.of(
            Integer.valueOf(args[2].substring(0, 1)),
            Integer.valueOf(args[2].substring(1, 2))));
  }

  private static Option<Position> getFinalPosFromArgs(String[] args) {
    return Option.of(
        Position.of(
            Integer.valueOf(args[3].substring(0, 1)),
            Integer.valueOf(args[3].substring(1, 2))));
  }

  private static Option<Boolean> getPromotesFromArgs(String[] args) {
    return Option.of(args.length == 5 && args[4].equals("promotes"));
  }

  String run(Game game) {
    if (verb.equals("moves")) {
      GameState previousGameState = game.getCurrentGameState();
      game.movePiece(player, posFrom, posTo, promotes);
      if (previousGameState != game.getCurrentGameState()) {
        return "Moved piece";
      } else {
        return "Couldn't move piece";
      }
    } else if (verb.equals("drops")) {
      // TODO this
      return "Gonna drop";
    } else {
      return "Unrecognised command";
    }
  }
}
