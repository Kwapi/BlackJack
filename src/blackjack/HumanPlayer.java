/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package blackjack;

import java.util.Scanner;

/**
 *
 * @author User
 */
public class HumanPlayer extends BasicPlayer{
    
    
    
    public HumanPlayer() {
        super();
    }
    
    @Override
    public int makeBet() {
       System.out.println("\nYour turn!");
       System.out.println("Balance: " + balance);
       
       Scanner scan = new Scanner(System.in);
       System.out.println("How much do you want to bet?");
       int answer = scan.nextInt();
       
       while(answer>balance){
           System.out.println("Not enough funds. Pick a smaller bet");
           answer = scan.nextInt();
       }
       bet = answer;
       return answer;
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
        
        
        //USER FEEDBACK
        Scanner scan = new Scanner(System.in);
        System.out.println("\nYour turn!");
        System.out.println("Balance: " + balance);
        System.out.println("Bet: " + bet);
        System.out.println("Dealer's hand: \n" + dealerFirstCard.toString()) ;
        System.out.println("Your hand: ");
        displayHand();
        System.out.println("Do you want to hit? [Y/N]");
        String answer = scan.nextLine();
        
        if (answer.equals("Y")){
            result = true;
        }
        else{
            result = false;
        }

        return result;

    }
    
    /**
     * Displays the cards in the hand and the total values of the hand
     */
    public void displayHand(){
        
        //display the cards
        for (Card c: hand.getCardsInHand()){
            System.out.println(c.toString());
        }
        
        //display the total values
         System.out.print("Total[s]: ");
         int[] totalValues = hand.totalValue();

          for (int j = 0; j < totalValues.length; j++) {
               System.out.print(totalValues[j]);

               if (j != totalValues.length - 1) { //seperate the values with a coma
                   System.out.print(", ");
               }
               
           }
          System.out.println(); //blank space to keep the output tidy
    }
    
    
    
    
    
}
