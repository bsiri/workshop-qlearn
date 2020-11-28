package org.henix.workshop.qlearn.game;

import org.henix.workshop.qlearn.ai.QLearningPlayer;
import org.henix.workshop.qlearn.ai.QLearningPlayerBuilder;
import org.henix.workshop.qlearn.concepts.*;
import org.henix.workshop.qlearn.impl.MatrixGrid;
import static org.henix.workshop.qlearn.tools.StaticFunctions.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.henix.workshop.qlearn.tools.StaticFunctions.print;

public class TrainingSession {

    public static final float WIN_REWARD = 1.0f;
    public static final float LOSS_REWARD = -2.0f;
    public static final float NO_REWARD = 0.0f;
    public static final float GREEDINESS = 0.1f;


    public static final int MAX_TRAINING_ROUNDS = 150000;

    int round = 0;

    QLearningPlayer playerX;
    QLearningPlayer playerO;

    QLearningPlayer currentPlayer;

    public void run(){


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

        print("\nI know Kung-Fu.\n");

    }



    private void play(){

        Grid grid = new MatrixGrid();

        flipCoin();

        GameOutcome outcome = GameOutcome.NOT_OVER_YET;
        while(! outcome.isGameOver()){

            CellState[][] gridState = grid.getState();
            List<Move> moves = grid.getAvailableMoveFor(currentPlayer);

            currentPlayer.train(gridState, moves, NO_REWARD, false);

            Move move = currentPlayer.nextMove(gridState, moves);
            grid.play(move);

            outcome = grid.getOutcome();

            nextTurn();
        }

        finalTrain(grid, outcome);

    }


    private void finalTrain(Grid grid, GameOutcome outcome){

        CellState[][] cellStates = grid.getState();

        Float x_reward;
        Float o_reward;
        switch(outcome){
            case DRAW: x_reward = NO_REWARD; o_reward = NO_REWARD; break;
            case PLAYER_X_WINS: x_reward = WIN_REWARD; o_reward = LOSS_REWARD; break;
            case PLAYER_O_WINS: x_reward = LOSS_REWARD; o_reward = WIN_REWARD; break;
            default: throw new IllegalStateException("How did we get there, the game is not over yet !");
        }
        playerX.train(cellStates, grid.getAvailableMoveFor(playerX), x_reward, true);
        playerO.train(cellStates, grid.getAvailableMoveFor(playerO), o_reward, true);
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
