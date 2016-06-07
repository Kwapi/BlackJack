/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author User
 */
public class BlackjackDealer implements Dealer {

    private ArrayList<Player> players;

    private Deck deck;
    private Hand hand;

    /**
     * Default constructor
     */
    public BlackjackDealer() {
        deck = new Deck();
        deck.shuffle();
        hand = new Hand(); //dealer's hand
    }

    /**
     *
     * @param p
     */
    @Override
    /**
     * Links the players to the dealer
     */
    public void assignPlayers(ArrayList<Player> p) {
        players = p;
    }

    @Override
    public void takeBets() {
        checkTheDeck();
        hand.remove(hand); //clear the dealer's hand (for the second and every next run)
        int playersBet; //initialising a variable

        Iterator it = players.iterator();
        while (it.hasNext()) {
            Player p = (Player) it.next();
            if (p.getBalance() >= p.getBet()) { //the player has to have enough money to make a bet
                playersBet = p.makeBet(); //the player makes a bet 
            } else {
                it.remove(); //the player is removed (has no funds)
            }
         
        }
    }

    /**
     * Checks the amount of the cards left If there are fewer than 1/4 of the
     * total left: create a new deck, shuffle and notify all players
     */
    public void checkTheDeck() {
        if ((deck.sizeFullDeck() / 4) > deck.size()) {
            deck.newDeck();
            deck.shuffle();
            for (Player p : players) {
                p.newDeck();
            }
        }
    }

    @Override
    /**
     * Deals two cards to each player and one to themselves
     */
    public void dealFirstCards() {
        
        for (Player p : players) //two cards to each player
        {
            checkTheDeck();
            p.takeCard(deck.deal());
            p.takeCard(deck.deal());
        }

        hand.add(deck.deal()); //one to the dealer

    }

    @Override
    /**
     * play: play the hand of player p. Keep asking if the player wants a card
     * until they stick or they bust
     *
     * @return final score of the hand
     */
    public int play(Player p) {
        
        while (p.hit()) { //check if the player wants a card
            checkTheDeck(); 
            Card dealtCard = deck.deal();
            p.takeCard(dealtCard);
            System.out.println("\tDealing a card ("+ dealtCard.toString()+")");
        }

        return p.getHandTotal();
    }

    /**
     * playDealer: Play the dealer hand The dealer must take cards until their
     * total is 17 or higher.
     *
     * @return dealer score (0 means bust)
     */
    @Override
    public int playDealer() {
        checkTheDeck();
        boolean keepPlaying = true;

        //DEALER PLAYING THEIR HAND
        while (keepPlaying) {

            //stop playing if
            if (hand.isOver(21)) //the hand is bust
            {
                keepPlaying = false;
            }
            //or
            if (blackjack()) { //the hand is a blackjack
                keepPlaying = false;
            }
            //or
            if (hand.totalValue()[0] >= 17) { //the soft total is 17 or more
                keepPlaying = false;
            }

            if (keepPlaying) { //the hand needs cards
                Card dealtCard = deck.deal();
                hand.add(dealtCard); //add a card to the hand
                System.out.println("\tDealing for dealer (" + dealtCard.toString()+")");
            }

        }

        return scoreHand(hand); //returning dealer's score
    }

    /**
     * scoreHand: The dealer should score the player hands, not rely on the
     * player to do it.
     *
     * @param h
     * @return score. Note if there are aces, this should be the highest
     * possible value that is less than 21 ACE, THREE should return 14. ACE,
     * THREE, TEN should return 14. ACE, ACE, TWO, THREE should return 17. ACE,
     * ACE, TEN should return 12.
     *
     * 0 = bust
     */
    @Override
    public int scoreHand(Hand h) {

        int[] values = h.totalValue();
        for (int i = values.length - 1; i >= 0; i--) {

            if (values[i] <= 21) {
                return values[i];
            }

        }

        return 0; //bust
    }

    /**
     *
     * blackjack: @return true if the current hand is a black jack (ACE, TEN or
     * PICTURE CARD)
     *
     * @return
     */
    public boolean blackjack() {

        ArrayList<Card> cards = hand.getCardsInHand();

        boolean twoCards = cards.size() == 2; //there have to be only 2 cards
        boolean oneAce = hand.countRank(Card.Rank.ACE) == 1; //one of them has to be an ace
        boolean softEleven = hand.totalValue()[0] == 11; //with aces counted as low the value has to be 11 (ace + TEN/PICTURE CARD)

        return twoCards && oneAce && softEleven;

    }

    @Override

    /**
     * Players lose their bet if they go bust, irrespective of what happens to
     * the dealer
     */
    public void settleBets() {
        int playerScore;
        int dealerScore;
        int result;
        String resultStr;
        int playerBet;
        boolean hasFunds;
        for (Player p : players) { //loop through all players
            playerScore = p.getHandTotal();
            dealerScore = scoreHand(hand);
            playerBet = p.getBet();

            if (playerScore == 0) { // players busts
                result = -playerBet; //the players loses the bet (-bet)
                resultStr = "loses";
            } else {
                //dealer busts or player has greater score than the dealer
                if (dealerScore == 0 || playerScore > dealerScore) {

                    result = playerBet; //the player wins their bet
                    resultStr = "wins";
                } else if (dealerScore == playerScore) { //push
                    result = 0; //the player retains the stake
                    resultStr = "retains the stake (push)";
                } else { //the dealer has a greater score than the player
                    result = -playerBet; //the player loses the bet
                    resultStr = "loses";
                }
            }

            //BLACKJACK CASES   
            if (p.blackjack() && blackjack()) { //both parties have blackjack = push
                result = 0;
                resultStr = "retains the stake (blackjack push)";
            } else if (p.blackjack()) { //player has blackjack
                result = 2 * playerBet;
                resultStr = "wins double (blackjack)";
            } else if (blackjack()) { //dealer has blackjack
                result = -playerBet;//player loses
                resultStr = "loses";
            }

            hasFunds = p.settleBet(result); //settling the bet and checking if the player has any funds left

            System.out.println("Player " +((BasicPlayer) p).getPlayerNumber() + " " + resultStr);

            p.newHand(); //clear the hand
        }

    }
    
    public void showFirstCard(){
        Card firstCard = hand.getCardsInHand().get(0);
        for (Player p: players ){
            p.viewDealerCard(firstCard);
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

}
