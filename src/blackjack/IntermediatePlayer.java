/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

/**
 *
 * @author User
 */
public class IntermediatePlayer extends BasicPlayer {

    /**
     * Default constructor
     */
    public IntermediatePlayer() {
        super();
    }

    @Override
    public boolean hit() {
        boolean result = true;

        //dealer's hand is 7 or higher(including ace (it's counted as 1 by default hence additional check)
        Card.Rank dealerFirstCardRank = dealerFirstCard.getRank();
        if (dealerFirstCardRank.value >= 7 || dealerFirstCardRank.equals(Card.Rank.ACE)) {

            if (isBust()) //the hand is bust
            {
                return false;
            }
            if (blackjack()) { //the hand is a blackjack
                return false;
            }

        } else {

        }

      

        return result;

    }

}
