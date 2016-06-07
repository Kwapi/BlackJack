/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class BlackjackTable {

    public static void main(String[] args) {

        // basicGame();
        humanGame();

    }

    public static void basicGame() {
        //1.Create a Dealer
        //2. Create players
        //3. Assign players to the Dealer

        //4. Take the bets
        //5. Dealer.dealFirstCards()
        //6. keep dealing 
        //7. Play dealer's hand
        //8. Settle the bet
        int counter = 0; //counts the elapsed number of rounds

        //SETTING UP THE GAME
        System.out.println("Setting up the game...");

        //Create a dealer
        BlackjackDealer dealer = new BlackjackDealer();
        System.out.println("Dealer created");

        //Create players
        ArrayList<Player> players = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            BasicPlayer p = new BasicPlayer();
            p.setPlayerNumber(i + 1);
            players.add(p);

        }
        System.out.println("Players created");

        //Assign the players to the dealer
        dealer.assignPlayers(players);
        System.out.println("Players assigned");

        int handsNo = 1; //number of hands to be played

        //PLAYING THE GAME 
        while (handsNo > 0) {

            ArrayList<Player> aPlayers = dealer.getPlayers(); //getting the assigned players (might be different to the ones created before)

            //Take the bets (and possibly remove the players that have no funds left)
            dealer.takeBets();

            //IF THERE ARE NO PLAYERS LEFT QUIT THE GAME
            if (aPlayers.isEmpty()) {
                System.out.println("No players left. Quitting the game");
                return;
            }
            //ELSE CONTINUE PLAYING
            counter++; //new round

            System.out.println("\nSTART OF ROUND: " + counter);
            System.out.println("Bets taken");

            //Deal the first cards
            dealer.dealFirstCards();
            System.out.println("First cards dealt");

            //The players play their hands
            for (Player p : aPlayers) {
                System.out.println("Dealing for player " + ((BasicPlayer) p).getPlayerNumber());
                int finalHandScore = dealer.play(p); //play until bust or a satfisfying score is achieved

                if (finalHandScore == 0) {
                    System.out.println("\t\tThe hand is bust");
                } else {
                    System.out.println("\t\tFinal hand score: " + finalHandScore);
                }

            }
            System.out.println("Dealing for players finished");

            //Dealer plays their hand
            System.out.println("Dealer is playing their hand");
            int dealersScore = dealer.playDealer();
            if (dealersScore == 0) {
                System.out.println("\t\tThe dealer's hand is bust");
            } else {
                System.out.println("\t\tDealer's score: " + dealersScore);
            }

            //Settle the bets
            dealer.settleBets();

            //PRINT OUT THE FINAL BALANCE
            for (Player p : aPlayers) {
                System.out.println("Player " + ((BasicPlayer) p).getPlayerNumber() + "'s balance: " + p.getBalance());
            }

            System.out.println("END OF ROUND " + (counter + 1));
            System.out.println();

            handsNo--;  //

            if (handsNo == 0) {
                handsNo = continuePlaying();
            }

        }

    }

    public static void humanGame() {
        //1.Create a Dealer
        //2. Create players
        //3. Assign players to the Dealer

        //4. Take the bets
        //5. Dealer.dealFirstCards()
        //6. keep dealing 
        //7. Play dealer's hand
        //8. Settle the bet
        boolean play = true; //variable responsible for playing the game
        int counter = 0; //counts the elapsed number of rounds

        //SETTING UP THE GAME
        System.out.println("Setting up the game...");

        //Create a dealer
        BlackjackDealer dealer = new BlackjackDealer();
        System.out.println("Dealer created");

        //Create players
        ArrayList<Player> players = new ArrayList<>();

        BasicPlayer bPlayer = new BasicPlayer();
        bPlayer.setPlayerNumber(1);

        HumanPlayer hPlayer = new HumanPlayer();
        hPlayer.setPlayerNumber(2);

        players.add(bPlayer);
        players.add(hPlayer);

        System.out.println("Players created");

        //Assign the players to the dealer
        dealer.assignPlayers(players);
        System.out.println("Players assigned");

        //PLAYING THE GAME 
        while (play) {

            ArrayList<Player> aPlayers = dealer.getPlayers(); //getting the assigned players (might be different to the ones created before)

            //Take the bets (and possibly remove the players that have no funds left)
            dealer.takeBets();

            int initialBalance = aPlayers.get(1).getBalance(); //initial balance of the human player (will be used to determine the outcome of the round which will be displayed to the player)

            //IF THERE ARE NO PLAYERS LEFT QUIT THE GAME
            if (aPlayers.isEmpty()) {
                System.out.println("No players left. Quitting the game");
                return;
            }

            //ELSE CONTINUE PLAYING
            counter++; //new round

            System.out.println("\nSTART OF ROUND: " + counter);
            System.out.println("Bets taken");

            //Deal the first cards
            dealer.dealFirstCards();
            dealer.showFirstCard(); //let the players know dealer's first card
            System.out.println("First cards dealt");

            //The players play their hands
            for (Player p : aPlayers) {
                System.out.println("Dealing for player " + ((BasicPlayer) p).getPlayerNumber());
                int finalHandScore = dealer.play(p); //play until bust or a satfisfying score is achieved

                if (finalHandScore == 0) {
                    System.out.println("\t\tThe hand is bust");
                } else {
                    System.out.println("\t\tFinal hand score: " + finalHandScore);
                }

            }
            System.out.println("Dealing for players finished");

            //Dealer plays their hand
            System.out.println("Dealer is playing their hand");
            int dealersScore = dealer.playDealer();
            if (dealersScore == 0) {
                System.out.println("\t\tThe dealer's hand is bust");
            } else {
                System.out.println("\t\tDealer's score: " + dealersScore);
            }

            //Settle the bets
            dealer.settleBets();

            //PRINT OUT THE FINAL BALANCE
            for (Player p : aPlayers) {
                System.out.println("Player " + ((BasicPlayer) p).getPlayerNumber() + "'s balance: " + p.getBalance());
            }

            //get the final balance of the human player
            int finalBalance = aPlayers.get(1).getBalance();

            System.out.println(); //keep the output tidy

            //PRINT OUT THE HUMAN PLAYER'S RESULT
            if (finalBalance > initialBalance) {
                System.out.println("You win!");
            } else if (finalBalance == initialBalance) {
                System.out.println("You neither won or lost (push)");
            } else {
                System.out.println("You lose!");
            }

            System.out.println("END OF ROUND " + (counter));
            System.out.println();

            if (finalBalance == 0) {
                System.out.println("You've got no funds left. Game over.");
                play = false;
            } else {
                play = continuePlayingBoolean(); //check if the player wants to keep playing
            }

        }

    }

    /**
     * How many hands do you want to play? 0 - none, quit the game
     *
     * @return
     */
    public static int continuePlaying() {
        int result = 0;

        Scanner scan = new Scanner(System.in);

        System.out.println("Do you want to continue? [Y/N]");
        String answer = scan.nextLine();

        if (answer.equals("Y")) {
            System.out.println("How many hands do you want to play?");
            result = scan.nextInt();
        }

        return result;
    }

    /**
     * Asks the player if they want to continue playing
     *
     * @return player's answer (boolean)
     */
    public static boolean continuePlayingBoolean() {
        boolean result = false;
        Scanner scan = new Scanner(System.in);
        System.out.println("Do you want to continue? [Y/N]");
        String answer = scan.nextLine();

        if (answer.equals("Y")) {
            result = true;
        }

        return result;
    }

}
