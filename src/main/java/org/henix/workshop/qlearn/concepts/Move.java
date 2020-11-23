package org.henix.workshop.qlearn.concepts;

import java.util.Objects;

public class Move {
    public final int x;
    public final int y;
    public final Token token;

    public Move(int x, int y, Token token) {
        this.x = x;
        this.y = y;
        this.token = token;
    }

    private void sanityCheck(){
        if (x < 0 || x > 2) throw new RuntimeException("x should be in [0,2]");
        if (y < 0 || y > 2) throw new RuntimeException("y should be in [0,2]");
        if (token == null) throw new RuntimeException("token cannot be empty");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return x == move.x &&
                y == move.y &&
                token == move.token;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, token);
    }

    public String shortString(){
        return String.format("(%d,%d)", x, y);
    }
}
