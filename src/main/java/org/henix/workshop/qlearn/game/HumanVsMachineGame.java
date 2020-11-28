package org.henix.workshop.qlearn.game;

import org.henix.workshop.qlearn.concepts.*;
import org.henix.workshop.qlearn.impl.HumanPlayer;
import org.henix.workshop.qlearn.impl.StupidAI;


public class HumanVsMachineGame extends Game {

    public HumanVsMachineGame() {
        super(new HumanPlayer(Token.X), new StupidAI(Token.O));
    }
}
