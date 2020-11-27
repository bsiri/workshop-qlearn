package org.henix.workshop.qlearn.concepts;

import java.util.List;

public interface Player {

    Token getToken();

    Move nextMove(CellState[][] gridState, List<Move> availableMoves);
}
