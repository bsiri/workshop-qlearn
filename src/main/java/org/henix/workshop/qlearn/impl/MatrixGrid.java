package org.henix.workshop.qlearn.impl;

import org.henix.workshop.qlearn.concepts.*;

import java.util.*;

/*
    Matrix-based implementation of Grid
 */
public class MatrixGrid implements Grid {

    public static final int MAX_MOVE_CNT = Grid.SIZE_X * Grid.SIZE_Y;

    private final CellState state[][];

    int _movecount = 0;

    MatrixGrid(CellState state[][]){
        this.state = state;
    }

    public MatrixGrid(){
        state = new CellState[Grid.SIZE_X][Grid.SIZE_Y];
        for (int x=0; x<Grid.SIZE_X; x++) for (int y=0; y<Grid.SIZE_Y; y++) state[x][y] = CellState.EMPTY;
    }

    @Override
    public void play(int x, int y, Token token) {
        play(new Move(x, y, token));
    }

    @Override
    public void play(Move move) {
        CellState moveState = fromToken(move.token);
        CellState current = state[move.x][move.y];
        if (current != CellState.EMPTY) throw new RuntimeException("Hey, you can't play that cell because there's already a "+current + " here !");
        state[move.x][move.y] = moveState;

        // update internal state variables
        _movecount++;
    }

    @Override
    public CellState[][] getState() {
        CellState copy[][] = new CellState[Grid.SIZE_X][Grid.SIZE_Y];
        for (int x=0; x<Grid.SIZE_X; x++) for (int y=0; y<Grid.SIZE_Y; y++) copy[x][y] = state[x][y];
        return copy;
    }

    @Override
    public List<Move> getAvailableMoveFor(Player player) {
        List<Move> moves = new ArrayList<>();
        for (int x=0; x<Grid.SIZE_X; x++) for (int y=0; y<Grid.SIZE_Y; y++){
            if (state[x][y] == CellState.EMPTY) moves.add(new Move(x, y, player.getToken()));
        }
        return moves;
    }

    @Override
    public boolean isGameOver() {
       // Hey, that code is incomplete !
       return getOutcome() != GameOutcome.NOT_OVER_YET;
    }

    @Override
    public GameOutcome getOutcome() {
        // What ? this doesn't work !
        if (_movecount >= MAX_MOVE_CNT) return GameOutcome.DRAW;
        return GameOutcome.NOT_OVER_YET;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
            .append(" |0|1|2\n")
            .append("-+-+-+-\n")
            .append(String.format("0|%s|%s|%s\n", state[0][0], state[0][1], state[0][2]))
            .append("-+-+-+-\n")
            .append(String.format("1|%s|%s|%s\n", state[1][0], state[1][1], state[1][2]))
            .append("-+-+-+-\n")
            .append(String.format("2|%s|%s|%s\n", state[2][0], state[2][1], state[2][2]))
            .append("-+-+-+-\n");
        return builder.toString();

    }


    private CellState fromToken(Token token){
        return (token == Token.X) ? CellState.X : CellState.O;
    }


}
