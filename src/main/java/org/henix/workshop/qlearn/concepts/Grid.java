package org.henix.workshop.qlearn.concepts;

import java.util.Collection;
import java.util.List;

public interface Grid {

    int SIZE_X = 3;
    int SIZE_Y = 3;

    /**
     * Sets the state of the grid at (x,y) to the given state.
     * If the move is illegal (wrong positions, position unavailable)
     * you will get a RuntimeException with a crude message.
     *
     * @param x
     * @param y
     * @param nexState
     */
    void play(int x, int y, Token token);


    /**
     * Same as play(in, int, CellState)
     * @param move
     */
    void play(Move move);


    /**
     * Returns a copy of the internal state of the grid.
     *
     * @return
     */
    CellState[][] getState();

    /**
     * Return all the possible moves for the given player.
     *
     * @param player
     * @return
     */
    List<Move> getAvailableMoveFor(Player player);


    /**
     * Says whether the game is over (ie, either there is a winner or the grid is complete)
     *
     * @return
     */
    boolean isGameOver();


    /**
     * Returns whether X or O wons, or if its a draw, of if it's not over yet.
     * @return
     */
    GameOutcome getOutcome();


    /**
     * Returns a pretty-printable view of the grid state.
     *
     * @return
     */
    String toString();


}
