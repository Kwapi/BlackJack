/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Iterator;

/**
 *
 * @author Kwapi
 */
public class Hand implements Serializable, Iterable {
    static final long serialVersionUID = 102;
    private ArrayList<Card> cardsInHand;
    
    
    // OLD SOLUTION
//    private int[] rankCount;
//    //Stores the count of the number of each rank that is currently in the hand
//    // [0] - Two
//    // [1] - Three
//    // [2] - Four etc
//    private int[] suitCount;
//    //Stores the count of the number of each rank that is currently in the hand
//    // [0] - CLUBS
//    // [1] - DIAMONDS
//    // [2] - HEARTS
//    // [3] - SPADES
    
    
    EnumMap rankCountMap; //stores the count of each rank that is currently in the hand 
    EnumMap suitCountMap; //stores the count of each suit that is currently in the hand 
            
    
    public Hand(){
        cardsInHand = new ArrayList<>();
        
        rankCountMap = new EnumMap<>(Card.Rank.class);
        suitCountMap = new EnumMap<>(Card.Suit.class);
    }
    
    public Hand(ArrayList<Card> cardList){
        cardsInHand = new ArrayList<>();
        
        
        rankCountMap = new EnumMap<>(Card.Rank.class);
        suitCountMap = new EnumMap<>(Card.Suit.class);
        
        for (Card c : cardList){
            cardsInHand.add(c);
            logRankAndSuit(c,"add");
            
       }
    }
    
    public void logRankAndSuit(Card c, String operation){
        
        //get the current rank count 
        Object count =  rankCountMap.get(c.getRank());
        //if there's nothing there - set the count to zero
        if (count==null){
            count =0;
        }
        
        int icount = (int) count;
        //increment the count
        switch(operation){
            case "add": icount++;
                    break;
            case "subtract": icount--;
                break;
                
        }
        rankCountMap.put(c.getRank(), icount);
        
        //get the currect suit count
        count = suitCountMap.get(c.getSuit());
        
        //if there's nothing there - set the count to zero
         if (count==null){
            count =0;
        }
        
        icount = (int) count;
        switch(operation){
            case "add": icount++;
                    break;
            case "subtract": icount--;
                break;
                
        }
        suitCountMap.put(c.getSuit(), icount);
        
        
    }
    
    
//    /** OLD METHOD
//     * Logs the rank and the suit of the given Card and adds it to a global count
//     */
//    public void logRankAndSuit(Card c){
//        
//               
//        rankCount[c.getRank().ordinal()] ++;
//        
//        suitCount[c.getSuit().ordinal()]++;
//
//        }
    
    
    /**
     * Returns total value of cards in the hand. The index of the array correspondons
     * to the number of aces counted as high. 
     * @return 
     */
    public int[] totalValue(){ 
        //get the number of aces in the hand
        int aceCount;
        if (rankCountMap.get(Card.Rank.ACE)==null){
            aceCount = 0;
        }
        else{
            aceCount = (int) rankCountMap.get(Card.Rank.ACE);
        }
        //create aceCount + 1 values
        int[] valuesArray = new int[aceCount+1];
        //initialise the base value (all aces low)
        int defaultValue = 0;
        //get the base value
        for (Card c : cardsInHand){
            defaultValue = defaultValue + c.getRank().getValue();
        }
        
        //Putting the values into an array
        //[0] - always default (aces low)
        //[1] - 1 ace counted as high
        //[2] - 2 aces countes as high
        //....
        //
        for (int i = 0; i<aceCount+1; i++){
            valuesArray[i] = defaultValue; //set the base value
            
            valuesArray[i] = valuesArray[i] + i*10; //adding 10 to get the ace high value
        }
        
        return valuesArray;
      
    }
    
    
    
    
    public void sortAscending(){
        Collections.sort(cardsInHand);
    }
    
    public void sortDescending(){
        Comparator desc = new Card.CompareDescending();
        Collections.sort(cardsInHand, desc);
    }
    
    /**
     * Counts and returns the number of cards of a given suit that are currently in the hand
     * @param s
     * @return 
     */
    public int countSuit(Card.Suit s){
        Object result = suitCountMap.get(s);
        
        if (result!=null){ //there is at least one card of that suit in the hand
            return (int) result;
        }
        return 0;
    }
    
    /**
     * Return the number of cards of a given rank currently in the hand
     * @param r
     * @return 
     */
    public int countRank(Card.Rank r){
        Object result = rankCountMap.get(r);
        
        if (result!=null){ //there is at least one card of that rank in the hand
            return (int) result;
        }
        return 0;
    }
    
    public String toString(){
        String str = "";
        
        for (Card c: cardsInHand){
           str = str + c.toString() + " \n";
        }
        
        return str;
    }
    
    public boolean isOver(int value){
        int lowestValueOfHand = this.totalValue()[0]; 
        return lowestValueOfHand > value;
    }
    public void add(Card c){
        cardsInHand.add(c);
        logRankAndSuit(c,"add");
    }

    public void add(ArrayList<Card> cardList){
        cardsInHand.addAll(cardList);
        
        for (Card c: cardList){
            logRankAndSuit(c,"add");
        }
    }
    
    public void add(Hand hand){
        ArrayList<Card> cardList = hand.cardsInHand;
        cardsInHand.addAll(cardList);
        
        for (Card c: cardList){
            logRankAndSuit(c,"add");
        }
        
    }
    
    public boolean remove(Card c){
        logRankAndSuit(c,"subtract");
        return cardsInHand.remove(c);
        
        
    }
    

    public boolean remove(Hand hand){
        for (Card c: hand.getCardsInHand()){
            hand.logRankAndSuit(c, "subtract");
        }
        return cardsInHand.removeAll(hand.getCardsInHand());
    }
    
    public Card remove(int index){
        Card removedCard = cardsInHand.get(index);
        logRankAndSuit(removedCard,"subtract");
        cardsInHand.remove(removedCard);
        
        return removedCard;
    }
        
    public ArrayList<Card> getCardsInHand() {
        return cardsInHand;
    }

    public void setCardsInHand(ArrayList<Card> cardsInHand) {
        this.cardsInHand = cardsInHand;
    }

    
    
    /**
     * Default iterator
     * Traverses the cards in order they were added
     * @return 
     */
    @Override
    public Iterator iterator() {
        return cardsInHand.iterator();
    }

    public EnumMap getRankCountMap() {
        return rankCountMap;
    }

    public void setRankCountMap(EnumMap rankCountMap) {
        this.rankCountMap = rankCountMap;
    }

    public EnumMap getSuitCountMap() {
        return suitCountMap;
    }

    public void setSuitCountMap(EnumMap suitCountMap) {
        this.suitCountMap = suitCountMap;
    }
    
    
    
    
}
