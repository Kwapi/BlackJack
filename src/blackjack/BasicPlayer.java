package blackjack;

import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author User
 */
public class BasicPlayer implements Player {

    protected int balance;
    protected  int bet;
    protected  Hand hand;
    protected  int playerNumber;
    protected Card dealerFirstCard;

    public BasicPlayer() {
        balance = 200;
        bet = 10;
        hand = new Hand();
        
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * newHand: this method should clear the previous hand ready for new cards
     * and return the old hand
*
     * @return 
     */
    @Override
    public Hand newHand() {
        Hand oldHand = hand;
        hand.remove(hand); //clear the previous hand
        
        return oldHand;
    }

    @Override
    public int makeBet() {
       
        return bet;
    }

    @Override
    public int getBet() {
        return bet; 
    }

    @Override
    public int getBalance() {
       return balance;
    }

    @Override
    public boolean hit() {
        boolean result = true;

        if (isBust()) //the hand is bust
        {
            return false;
        }
        if (blackjack()) { //the hand is a blackjack
            return false;
        }

        int[] values = hand.totalValue();

        for (int i = values.length - 1; i >= 0; i--) {

            if (values[i] >= 17 && values[i] <= 21) {
                return false;
            }

        }

        return result;

    }

    @Override
    public void takeCard(Card c) {
        hand.add(c);
    }

    /**
     * settleBet: The value passed is positive if the player won, negative
     * otherwise.
     *
     * @return true if the player has funds remaining, false otherwise.
     */
    @Override
    public boolean settleBet(int p) {
        balance = balance + p;

        return balance > 0;
    }

    @Override
    public int getHandTotal() {
        int[] values = hand.totalValue();
        for (int i = values.length - 1; i >= 0; i--) {

            if (values[i] <= 21) {   //<=  or <?????
                return values[i];
            }

        }
        return 0; //placating netbeans
    }

    /**
     *
     * blackjack: @return true if the current hand is a black jack (ACE, TEN or
     * PICTURE CARD)
     */
    @Override
    public boolean blackjack() {

        ArrayList<Card> cards = hand.getCardsInHand();

        boolean twoCards = cards.size() == 2; //there have to be only 2 cards
        boolean oneAce = hand.countRank(Card.Rank.ACE) == 1; //one of them has to be an ace
        boolean softEleven = hand.totalValue()[0] == 11; //with aces counted as low the value has to be 11 (ace(1) + TEN/PICTURE CARD(10))

        return twoCards && oneAce && softEleven;

    }

    @Override
    public boolean isBust() {
        return hand.isOver(21);
    }

    @Override
    public Hand getHand() {
        return hand;
    }

    @Override
    public void viewDealerCard(Card c) {
       dealerFirstCard = c;
    }

    @Override
    public void viewCards(ArrayList<Card> cards) {
        for (Card c: cards){
            System.out.println(c.toString());
        }
    }

    @Override
    public void newDeck() {
        // new deck was created by the dealer
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

}
