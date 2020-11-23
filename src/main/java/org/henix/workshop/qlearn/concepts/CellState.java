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
}
