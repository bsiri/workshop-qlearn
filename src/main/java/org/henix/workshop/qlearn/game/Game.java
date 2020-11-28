package org.henix.workshop.qlearn.game;

import org.henix.workshop.qlearn.concepts.*;
import org.henix.workshop.qlearn.impl.MatrixGrid;

import java.util.concurrent.ThreadLocalRandom;

import static org.henix.workshop.qlearn.tools.StaticFunctions.print;

public class Game implements GameController {


    private final Player playerX;
    private final Player playerO;

    public Game(Player playerX, Player playerO) {
        this.playerX = playerX;
        this.playerO = playerO;
    }

    private Player currentPlayer;

    @Override
    public void run() {

        flipCoin();

        Grid grid = new MatrixGrid();
        int turncount = 1;

        while (! grid.isGameOver()){

            print("Turn %d !", turncount++);
            print(grid.toString()+"\n");

            print("%s move :", currentPlayer.toString());
            Move move = currentPlayer.nextMove(grid.getState(), grid.getAvailableMoveFor(currentPlayer));
            print("%s plays : %s", currentPlayer, move.shortString());
            grid.play(move);

            nextTurn();

        }

        print("Game over !");
        print(grid.toString());

        print(grid.getOutcome().toString());


    }

    private void flipCoin(){
        boolean humanGoesFirst = ThreadLocalRandom.current().nextBoolean();
        if (humanGoesFirst){
            currentPlayer = playerX;
        }
        else {
            currentPlayer = playerO;
        }
    }

    private void nextTurn(){
        currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
    }



}
