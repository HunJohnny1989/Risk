package model;

import java.util.List;

/**
 *
 * @author Sajti Tamás
 */
public class Battle {
    private Territory attackingTerritory;
    private int attackingTroopCount;
    private Territory defendingTerritory;
    private int defendingTroopCount;
    private BattleResult battleResult;
    private List< Integer > attackerDiceRolls;
    private List< Integer > defenderDiceRolls;
    private int attackerTroopLossCount = 0;
    private int defenderTroopLossCount = 0;
    
    public Battle( Territory attackingTerritory, Territory defendingTerritory, int attackingTroopCount ) {
        this.attackingTerritory = attackingTerritory;
        this.attackingTroopCount = attackingTroopCount;
        this.defendingTerritory = defendingTerritory;
        defendingTroopCount = Math.min( 3, defendingTerritory.getTroopCount() );
    }
   
    public BattleResult execute() {
        rollDice();
        computeLosses();
        return new BattleResult( attackerTroopLossCount, defenderTroopLossCount, defendingTroopCount == defenderTroopLossCount );
    }

    private void rollDice() {
        /*
        Decide how many armies you are going to use in your attack
        1 army = 1 die
        2 armies = 2 dice
        3 armies = 3 dice
        
        orsi*/
        attackerDiceRolls = Dice.roll( attackingTroopCount );
        if( defendingTroopCount < 3 )
            defenderDiceRolls = Dice.roll( 1 );
        else
            defenderDiceRolls = Dice.roll( defendingTroopCount );
    }
    
    public BattleResult getBattleResult() {
        return battleResult;
    }

    private void computeLosses() {
        for( int i = 0; i < Math.max( attackerDiceRolls.size(), defenderDiceRolls.size() ); i++ ) {
            if( hasAttackerWon( i ) ) 
                attackerTroopLossCount++;
            else
                defenderTroopLossCount++;
        }
    }

    private boolean hasAttackerWon( int i ) {
        return defenderDiceRolls.size() < i + 1 || 
                ( attackerDiceRolls.size() >= i + 1 && attackerDiceRolls.get( i ) > defenderDiceRolls.get( i ) );
    }
}
