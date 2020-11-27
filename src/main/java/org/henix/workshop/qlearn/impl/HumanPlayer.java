package org.henix.workshop.qlearn.impl;

import org.henix.workshop.qlearn.concepts.CellState;
import org.henix.workshop.qlearn.concepts.Move;
import org.henix.workshop.qlearn.concepts.Player;
import org.henix.workshop.qlearn.concepts.Token;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HumanPlayer implements Player {

    private static final Pattern p = Pattern.compile("(\\d),(\\d)");

    private Token token;

    public HumanPlayer(Token token) {
        this.token = token;
    }

    @Override
    public Token getToken() {
        return token;
    }

    @Override
    public Move nextMove(CellState[][] gridState, List<Move> availableMoves) {
        Scanner scanner = new Scanner(System.in);
        Move move = null;

        while(move == null){
            printMoves(availableMoves);
            System.out.print("Next move ? ");
            Move parsed = parseMove(scanner.nextLine());
            if (parsed == null){
                System.out.println("Hey, your move should be entered like this : x,y");
                continue;
            }
            if (! availableMoves.contains(parsed)){
                System.out.println("Hey, the move you entered is illegal !");
                continue;
            }
            move = parsed;
        }

        return move;
    }


    private void printMoves(Collection<Move> moves){
        String strMoves = moves.stream().map(Move::shortString).collect(Collectors.joining(", "));
        System.out.println("Available moves : " + strMoves);
    }

    private Move parseMove(String str){
        Matcher m = p.matcher(str.trim());
        if (!m.matches()) return null;
        int x = Integer.valueOf(m.group(1));
        int y = Integer.valueOf(m.group(2));
        return new Move(x, y, token);
    }

    @Override
    public String toString(){
        return "human "+token;
    }
}
