package org.henix.workshop.qlearn.concepts;

public enum CellState {
    X(  "X"),
    O( "O"),
    EMPTY(  " ");

    String symbol;

    CellState(String symbol){
        this.symbol = symbol;
    }

    public String toString(){
        return symbol;
    }

    public boolean matches(Token playerToken){
        if (this == X && playerToken == Token.X) return true;
        if (this == O && playerToken == Token.O) return true;
        return false;
    }
}
