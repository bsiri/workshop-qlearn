package org.henix.workshop.qlearn.concepts;

import java.util.Collection;

public interface Player {

    Token getToken();

    Move nextMove(CellState[][] gridState, Collection<Move> availableMoves);
}
