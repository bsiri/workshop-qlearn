package org.henix.workshop.qlearn.tools;

public class StaticFunctions {
    private StaticFunctions(){}

    static public void print(String msg, Object ...prms){
        System.out.println(String.format(msg, prms));
    }
}
