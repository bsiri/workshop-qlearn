package org.henix.workshop.qlearn.impl;

import org.henix.workshop.qlearn.concepts.CellState;
import org.henix.workshop.qlearn.concepts.Move;
import org.henix.workshop.qlearn.concepts.Player;
import org.henix.workshop.qlearn.concepts.Token;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class StupidAI implements Player {

    private Token token;

    private ThreadLocalRandom random = ThreadLocalRandom.current();

    public StupidAI(Token token){
        this.token = token;
    }

    @Override
    public Token getToken() {
        return token;
    }

    @Override
    public Move nextMove(CellState[][] gridState, Collection<Move> availableMove) {
        int moveIdx = random.nextInt(availableMove.size());
        Iterator<Move> iter = availableMove.iterator();
        while(moveIdx-- > 0) iter.next();
        return iter.next();
    }

    @Override
    public String toString(){
        return "StupidAI "+token;
    }
}
