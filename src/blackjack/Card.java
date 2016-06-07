package blackjack;

import java.io.Serializable;
import java.util.Comparator;

public class Card implements Comparable, Serializable {

    /**
     * Models the rank of the card
     */
    public enum Rank {

        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10), ACE(1);

        int value;

        Rank(int x) {
            value = x;
        }

        /*
         * Returns the next enum value
         */
        public Rank getNext() {

            Rank result;
            switch (this) {
                case ACE:
                    result = TWO;
                    break;
                default:
                    result = Rank.values()[this.ordinal() + 1];
            }

            return result;

        }

        /*
         * Returns the integer value of the card
         * Ace's default value is 11
         */
        public int getValue() {
            return value;

        }
    }

    public enum Suit {

        CLUBS, DIAMONDS, HEARTS, SPADES;
    }

    /**
     * Fields
     */
    private Rank rank;
    private Suit suit;
    static final long serialVersionUID = 100;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    /**
     *
     * @param card1
     * @param card2
     * @return sum of the value of two cards
     */
    public static int sum(Card card1, Card card2) {

        return card1.getRank().getValue() + card2.getRank().getValue();
    }

    /**
     *
     * @param card1
     * @param card2
     * @return true if the sum of values of the cards is equal to 21 else
     * returns false
     */
    public static boolean isBlackJack(Card card1, Card card2) {
        boolean result = false;

        if (sum(card1, card2) == 21) {
            result = true;
        }
        return result;
    }

    /**
     * Sorts the cards into descending order by rank
     *
     * i.e. 10 Diamonds, 10 Spades, 6 Hearts, 2 Clubs
     */
    public static class CompareDescending implements Comparator {

        @Override
        public int compare(Object a, Object b) {

            Card c1 = (Card) a;
            Card c2 = (Card) b;
            Rank rank1 = c1.getRank();
            Rank rank2 = c2.getRank();
            if (rank1.equals(rank2)) {
                return c1.getSuit().name().compareTo(c2.getSuit().name());
            } else {
                return rank2.getValue() - rank1.getValue();
            }
        }
    }

    public static class CompareSuit implements Comparator {

        @Override
        public int compare(Object a, Object b) {

            Card c1 = (Card) a;
            Card c2 = (Card) b;
            Rank rank1 = c1.getRank();
            Rank rank2 = c2.getRank();
            Suit suit1 = c1.getSuit();
            Suit suit2 = c2.getSuit();
            if (suit1.equals(suit2)) {
                return rank1.getValue() - rank2.getValue();
            } else {
                return suit1.name().compareTo(suit2.name());
            }
        }
    }

    /**
     * Returns a String representation of this object
     *
     * @return
     */
    @Override
    public String toString() {
        return rank.toString() + " of " + suit.toString();
    }

    /**
     * Sorts the cards into ascending order First by Rank, then by Suit
     *
     * i.e. 2 Clubs, 6 Hears, 10 Diamonds, 10 Spades
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Object o) {

        Card c = (Card) o;
        Rank rank1 = this.getRank();
        Rank rank2 = c.getRank();
        if (rank1.equals(rank2)) {
            return this.getSuit().name().compareTo(c.getSuit().name());
        } else {
            return rank1.getValue() - rank2.getValue();
        }
    }

}
