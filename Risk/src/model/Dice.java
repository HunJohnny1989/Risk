/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Sajti Tamás
 */
public class Dice {
    
    private static Random random = new Random();
    
    public static int roll() {
        return random.nextInt( 6 ) + 1;
    }

    static List<Integer> roll( int times ) {
        List<Integer> rolls = new ArrayList<>( times );
        for( int i = 0; i < times; i++ ) {
            rolls.add( roll() );
        }
        return rolls;
    }
}
