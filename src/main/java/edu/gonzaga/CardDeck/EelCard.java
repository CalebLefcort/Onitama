package edu.gonzaga.CardDeck;

import edu.gonzaga.Coordinate;

public class EelCard extends Card {

  public EelCard(String name) {
    super(name);
    color = "blue";
    Coordinate move1 = new Coordinate(1, 1);
    Coordinate move2 = new Coordinate(1, -1);
    Coordinate move3 = new Coordinate(-1, 0);
    Coordinate invMove1 = new Coordinate(-1, -1);
    Coordinate invMove2 = new Coordinate(-1, 1);
    Coordinate invMove3 = new Coordinate(1, 0);

    moves.add(move1);
    moves.add(move2);
    moves.add(move3);
    invMoves.add(invMove1);
    invMoves.add(invMove2);
    invMoves.add(invMove3);
  }
}
