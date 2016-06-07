/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author Kwapi
 */
public class Deck implements Serializable, Iterable{
    static final long serialVersionUID = 101;
    private ArrayList<Card> cardList;
    
    /**
     *
     */
    public Deck(){
        cardList = new ArrayList<>();
        
        for (Card.Suit s : Card.Suit.values()){
            for (Card.Rank r : Card.Rank.values()){
                cardList.add(new Card(r,s));
            }
        }
        
    }
    
    /**
     * Shuffles the deck
     */
    public void shuffle(){
        //making sure each shuffle is unique
        long seed = System.nanoTime();
        
        Collections.shuffle(cardList, new Random(seed));
    }
    
    /**
     * Removes the top card from the deck and returns it
     * @return 
     */
    public Card deal(){
        int lastCard = cardList.size()-1;
        Card result = cardList.get(lastCard);
        cardList.remove(lastCard);
        
        return result;
    }
    public ArrayList<Card> getCardList() {
        return cardList;
    }

    public void setCardList(ArrayList<Card> cardList) {
        this.cardList = cardList;
    }
    /**
     * Returns number of cards remaining in the deck
     * @return 
     */
    public int size(){
        return cardList.size();
    }
    public int sizeFullDeck(){
        return 52;
    }
    
    /**
     * Creates a new Deck
     */
    public final void newDeck(){
         cardList = new ArrayList<>();
        
        for (Card.Suit s : Card.Suit.values()){
            for (Card.Rank r : Card.Rank.values()){
                cardList.add(new Card(r,s));
            }
        }
        
    }

    @Override
    public Iterator iterator() {
        return new DealingIterator();
    }
    
    /**
     * Traverses every second card from the bottom
     */
    public class SecondCardIterator implements Iterator{
        
        int index = 0;
        
        
        @Override
        public boolean hasNext() {
            
            return index<=cardList.size()-1;
            
        }

        @Override
        public Object next() {
           Card result = cardList.get(index);
            index+=2;
            return result;
        }

        @Override
        public void remove() {
            cardList.remove(index);
        }
    
    }
    
    /**
     * Default iterator - traverses in the order they will be dealt
     */
    public class DealingIterator implements Iterator{
        
        int index = cardList.size()-1;
        
        
        @Override
        public boolean hasNext() {
            
            return index>=0;
            
        }

        @Override
        public Object next() {
            Card result = cardList.get(index);
            index--;
            return result;
        }

        @Override
        public void remove() {
            cardList.remove(index);
        }
    
    }

}
