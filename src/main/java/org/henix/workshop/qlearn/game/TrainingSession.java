package org.henix.workshop.qlearn.game;

import org.henix.workshop.qlearn.ai.QLearningPlayer;
import org.henix.workshop.qlearn.ai.QLearningPlayerBuilder;
import org.henix.workshop.qlearn.concepts.Grid;
import org.henix.workshop.qlearn.concepts.Move;
import org.henix.workshop.qlearn.concepts.Player;
import org.henix.workshop.qlearn.concepts.Token;
import org.henix.workshop.qlearn.impl.MatrixGrid;

import java.util.concurrent.ThreadLocalRandom;

import static org.henix.workshop.qlearn.tools.StaticFunctions.print;

public class TrainingSession {

    public static final float WIN_REWARD = 1.0f;
    public static final float LOSS_REWARD = -2.0f;
    public static final float NO_REWARD = 0.0f;
    public static final float GREEDINESS = 0.5f;    // greed is good... or is it ?


    // ?? Ten, really ?
    public static final int MAX_TRAINING_ROUNDS = 10;

    int round = 0;

    QLearningPlayer playerX;
    QLearningPlayer playerO;

    Player currentPlayer;

    public void run(){

        initPlayers();

        while(round < MAX_TRAINING_ROUNDS){
            initPlayers();

            play();
            round++;

            // Print the situation every 10%
            if (round % (MAX_TRAINING_ROUNDS/10) == 0){
                double progress = ((double)round / MAX_TRAINING_ROUNDS)*100;
                print("completed round : %3.0f%%",progress);
            }
        }

    }



    private void play(){

        Grid grid = new MatrixGrid();

        flipCoin();
        
        while(! grid.isGameOver()){

            Move move = currentPlayer.nextMove(grid.getState(), grid.getAvailableMoveFor(currentPlayer));
            grid.play(move);

            /*
                Hmmm, those AI are supposed to train about here.
                Is the game over ?
                - Is there a winner ?
                - Which one gets which reward ?
                Or maybe the game is not over yet ?
                - Is there a reward at all then ?
             */

            nextTurn();
        }

    }



    private void initPlayers(){
        playerX = new QLearningPlayerBuilder()
                        .newLearningPlayer(Token.X)
                        .withTable1()
                        .withDefaultLearningParameters()
                        .withGreediness(GREEDINESS)
                        .build();

        playerO = new QLearningPlayerBuilder()
                        .newLearningPlayer(Token.O)
                        .withTable2()
                        .withDefaultLearningParameters()
                        .withGreediness(GREEDINESS)
                        .build();
    }

    private void flipCoin(){
        boolean playerXGoesFirst = ThreadLocalRandom.current().nextBoolean();
        if (playerXGoesFirst){
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
