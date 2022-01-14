/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eltonb.datatier.jpa.world.app;

/**
 *
 * @author elton.ballhysa
 */
public class Pair<F,S> {
    private F first;
    private S second;

    public static <F,S> Pair<F,S> of (F f, S s) {
        return new Pair<F,S>(f, s);
    }
    
    public Pair(F f, S s) {
        this.first = f;
        this.second = s;
    }
    
    public F first() {return first;}
    public S second() {return second;}
    
    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
