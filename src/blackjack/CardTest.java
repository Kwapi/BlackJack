//package blackjack;
//
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.Iterator;
//
//public class CardTest {
//
//    public static void main(String[] args) {
//
//        
//        //THE OUTPUT NEEDS TO BE FORMATTED
//        //ASK FOR INPUT
//        //CLEAN AND COMMENT OUT
//        
//        //1a
//        Deck deck = new Deck();
//
//        for (Object c : deck) {
//            System.out.println(((Card) c).toString());
//        }
//
//        //1b
//        Iterator it = deck.new SecondCardIterator();
//
//        while (it.hasNext()) {
//            System.out.println(((Card) it.next()).toString());
//        }
//
//        //2a
//        deck.shuffle();
//
//        Hand[] hands = new Hand[4];
//
//        for (int i = 0; i < 4; i++) {
//            hands[i] = new Hand();
//        }
//
//        int k = 0;
//        while (k < deck.size()) {
//            for (int j = 0; j < 4; j++) {
//                hands[j].add(deck.deal());
//            }
//
//        }
//
//        //2b
//        for (int i = 0; i < 4; i++) {
//            System.out.println("Hand " + i);
//            for (Card c : hands[i].getCardsInHand()) {
//                System.out.println(c.toString());
//            }
//        }
//
//        //3
//        String filename = "hands.ser";
//
//        try {
//            FileOutputStream fos = new FileOutputStream(filename);
//            ObjectOutputStream out = new ObjectOutputStream(fos);
//            out.writeObject(hands);
//            out.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        //4
//        for (int i = 0; i < 4; i++) { //loop through hands
//            System.out.println("\nHAND " + (i + 1) + " : ");
//            for (Card.Rank r : Card.Rank.values()) { //get the number of rank
//                System.out.println("\t Rank: " + r.name() + " Number: " + hands[0].countRank(r));
//            }
//
//            for (Card.Suit s : Card.Suit.values()) { //get the number of suit
//                System.out.println("\t Suit: " + s.name() + " Number: " + hands[0].countSuit(s));
//            }
//
//            System.out.println("Total Value{s): ");
//            int[] totalValues = hands[i].totalValue();
//
//            for (int j = 0; j < totalValues.length; j++) {
//                System.out.print(totalValues[j]);
//
//                if (j != totalValues.length - 1) { //seperate the values with a coma
//                    System.out.print(", ");
//                }
//
//            }
//
//            //5
//            System.out.println("\nOver 100: " + hands[i].isOver(100));
//
//        }
//
//        //6
//        hands[0].sortAscending();
//        hands[1].sortAscending();
//        hands[2].sortDescending();
//
//        Comparator sAsc = new Card.CompareSuit();
//        Collections.sort(hands[3].getCardsInHand(), sAsc);
//
//        //7
//        Hand[] handsLoaded = null;
//        try {
//            FileInputStream fis = new FileInputStream(filename);
//            ObjectInputStream in = new ObjectInputStream(fis);
//
//            handsLoaded = (Hand[]) in.readObject();
//            in.close();
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        //8
//        Iterator it2 = handsLoaded[0].iterator();
//
//        while (it2.hasNext()) {
//            Card c = (Card) it2.next();
//            it2.remove();
//            handsLoaded[0].remove(c);
//            handsLoaded[1].add(c);
//
//        }
//
//        //9
//        //initialise temporary ArrayLists later used to populate the hands with single suits
//        ArrayList<Card> clubs = new ArrayList<>();
//        ArrayList<Card> diamonds = new ArrayList<>();
//        ArrayList<Card> hearts = new ArrayList<>();
//        ArrayList<Card> spades = new ArrayList<>();
//        
//        for (int i = 0; i < handsLoaded.length; i++) {
//            ArrayList<Card> cardsInHand = handsLoaded[i].getCardsInHand();
//            for (Card c : cardsInHand) {
//                switch (c.getSuit()) {
//                    case CLUBS:
//                        clubs.add(c);
//                        break;
//                    case DIAMONDS:
//                        diamonds.add(c);
//                        break;
//                    case HEARTS:
//
//                        hearts.add(c);
//                        break;
//                    case SPADES:
//
//                        spades.add(c);
//                        break;
//                }
//            }
//            
//            
//            handsLoaded[i].remove(handsLoaded[i]); //clear the cards in hand 
//
//        }
//
//        //populate with single suit
//        handsLoaded[0].add(clubs);
//        handsLoaded[1].add(diamonds);
//        handsLoaded[2].add(hearts);
//        handsLoaded[3].add(spades);
//
//        //sort in ascending order
//        handsLoaded[0].sortAscending();
//        handsLoaded[1].sortAscending();
//        handsLoaded[2].sortAscending();
//        handsLoaded[3].sortAscending();
//
//    }
//}
//
////       
////        // Testing toString
////        Card c = new Card(Card.Rank.TEN, Card.Suit.DIAMONDS);
////        System.out.println("To String: " + c.toString());
////       Card c2 = new Card(Card.Rank.TEN, Card.Suit.SPADES);
////       Card c3 = new Card(Card.Rank.TWO, Card.Suit.CLUBS);
////       Card c4 = new Card(Card.Rank.SIX, Card.Suit.HEARTS);
////       Card c5 = new Card(Card.Rank.SEVEN, Card.Suit.HEARTS);
////       Card c6 = new Card(Card.Rank.ACE, Card.Suit.CLUBS);
////       
////       //Testing isBlackJack
////       System.out.println("Is BlackJack: " + Card.isBlackJack(c, c2));
////       
////       //Testing Comparable
////       System.out.println("\nComparable - Ascending");
////       List ts1 = new ArrayList();
////        ts1.add(c);
////        ts1.add(c2);
////        ts1.add(c3);
////        ts1.add(c4);
////      
////        Collections.sort(ts1);
////        Iterator itr = ts1.iterator();
////
////        while(itr.hasNext()){
////            Object element = itr.next();
////            System.out.println(element);
////                    
////        }
////        
////        
////        //Testing Comparator Descending
////        System.out.println("Comparator - Descending");
////        List ts2 = new ArrayList();
////        ts2.add(c);
////        ts2.add(c2);
////        ts2.add(c3);
////        ts2.add(c4);
////       
////        Comparator desc = new Card.CompareDescending();
////        Collections.sort(ts2,desc);
////        Iterator itr2 = ts2.iterator();
////        while(itr2.hasNext()){
////            Object element = itr2.next();
////            System.out.println(element);
////                    
////        }
////        
////        
////        //Testing Comparator Suit Ascending
////        System.out.println("Comparator Suit - Ascending");
////        ArrayList ts3 = new ArrayList();
////        ts3.add(c);
////        ts3.add(c2);
////        ts3.add(c3);
////        ts3.add(c4);
////        ts3.add(c5);
////        ts3.add(c6);
////        Comparator sAsc = new Card.CompareSuit();
////        Collections.sort(ts3,sAsc);
////        Iterator itr3 = ts3.iterator();
////        while(itr3.hasNext()){
////            Object element = itr3.next();
////            System.out.println(element);
////                    
////        }
////        
////        
////        
////        //Testing building the Deck of 52 Cards
////        System.out.println("Deck : ");
////        Deck deck = new Deck();
////        
////        List cardList = deck.getCardList();
////     
////        Iterator itr4 = deck.new SecondCardIterator();
////        
////        while(itr4.hasNext()){
////            System.out.println(itr4.next().toString());
////            
////        }
////        
////        System.out.println(cardList.size());
////        
////        //Testing shuffle()
////        System.out.println("\nShuffled deck: ");
////        deck.shuffle();
////        
////        ArrayList cardListshuffled = deck.getCardList();
////        Iterator itr5 = cardList.iterator();
////        
////        while(itr5.hasNext()){
////            
////            System.out.println( itr5.next().toString());
////            
////        }
////        
////        //Testing deal()
////        for (int i = 0; i<5; i++){
////            System.out.println("Dealing: " +deck.deal().toString());
////        }
////        
////        
////        //Testing size()
////        
////        System.out.println("Deck size: " + deck.size());
////        
////        //Testing newDeck()
////        
////        
////        deck.newDeck();
////        System.out.println(" New Deck size: " + deck.size());
////        
////        
////        
////        //Testing Hand
////        
////        Hand hand = new Hand(ts3);
////        System.out.println("Total value: " + Arrays.toString(hand.totalValue()));
////        
////        System.out.println(hand.toString());
////        System.out.println(hand.countSuit(Card.Suit.CLUBS));
////        hand.sortAscending();
////        System.out.println("Sorted asc: \n" + hand.toString());
////        hand.sortDescending();
////        System.out.println("Sorted dsc: \n" + hand.toString());
////        System.out.println(hand.countRank(Card.Rank.SIX));
////        
////        
////        
////        //Iterator deck
////        
////        Deck deck2 = new Deck();
////        deck2.shuffle();
////        
////        for (Object d: deck2){
////            System.out.println(((Card)d).toString());
////        }
////        
////     
////        
////    
////        
////         HashMap rankCountMap = new HashMap();
////        
////         rankCountMap.put(Card.Rank.EIGHT, 5);
////         
////        System.out.println(rankCountMap.get(Card.Rank.EIGHT));
//
