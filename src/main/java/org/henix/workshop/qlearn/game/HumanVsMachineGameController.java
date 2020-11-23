package org.henix.workshop.qlearn.game;

import org.henix.workshop.qlearn.concepts.*;
import org.henix.workshop.qlearn.impl.HumanPlayer;
import org.henix.workshop.qlearn.impl.MatrixGrid;
import org.henix.workshop.qlearn.impl.StupidAI;

import java.util.concurrent.ThreadLocalRandom;
import static org.henix.workshop.qlearn.tools.StaticFunctions.*;

public class HumanVsMachineGameController implements GameController {

    private Player human = new HumanPlayer(Token.X);
    private Player machine = new StupidAI(Token.O);

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
            currentPlayer = human;
        }
        else {
            currentPlayer = machine;
        }
    }

    private void nextTurn(){
        currentPlayer = (currentPlayer == human) ? machine : human;
    }



}
