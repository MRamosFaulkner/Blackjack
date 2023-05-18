package com.blackjack;

import com.sun.security.jgss.GSSUtil;

import java.util.Scanner;

public class Blackjack {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to Blackjack!");

        // Create the playing deck
        Deck playingDeck = new Deck();
        playingDeck.createFullDeck();
        playingDeck.shuffleDeck();

        // Create hands for the player and the dealer - hands are created from methods that are made in the deck class
        Deck playerHand = new Deck();
        Deck dealerHand = new Deck();

        double playerMoney = 100.00;

//        // Game loops
        while(playerMoney > 0) {
            System.out.println("You have $" + playerMoney + ". How much would you like to bet?");
            double playerBet = input.nextDouble();

            if(playerBet > playerMoney) {
                System.out.println("You need more money for this bet.");
                break;

            }
            boolean endRound = false;


            playerHand.draw(playingDeck);
            playerHand.draw(playingDeck);

            dealerHand.draw(playingDeck);
            dealerHand.draw(playingDeck);

            while(true) {
                System.out.println("Your hand: ");
                System.out.println(playerHand.toString());
                System.out.println("Your hand value: " + playerHand.cardsValue());

                System.out.println("\nDealer Hand: " + dealerHand.getCard(0) + " and [Hidden]");

                System.out.println("\nWould you like to (1)Hit or (2)Stay?");
                int response = input.nextInt();
                input.nextLine();

                if(response == 1) {
                    playerHand.draw(playingDeck);
                    System.out.println("\n50You drew a: " + playerHand.getCard(playerHand.deckSize() - 1).toString());

                    if (playerHand.cardsValue() > 21) {
                        System.out.println("\nBust. Current value: " + playerHand.cardsValue());
                        playerMoney -= playerBet;
                        endRound = true;
                        break;
                    }
                }


                if(response == 2) {
                    break;
                }

            }

            System.out.println("Dealer hand: " + dealerHand.toString());

            if((dealerHand.cardsValue() > playerHand.cardsValue()) && endRound == false) {
                System.out.println("Dealer Wins!");
                playerMoney -= playerBet;
                endRound = true;
            }

            while((dealerHand.cardsValue() < 17) && endRound == false) {
                dealerHand.draw(playingDeck);
                System.out.println("Dealer Drew: " + dealerHand.getCard(dealerHand.deckSize()-1).toString());
            }

            System.out.println("Dealer's hand is: " + dealerHand.cardsValue());

            if((dealerHand.cardsValue()) > 21 && endRound == false) {
                System.out.println("Dealer Bust. You WIN!");
                playerMoney += playerBet;
                endRound = true;
            }

            if(playerHand.cardsValue() == dealerHand.cardsValue() && endRound == false) {
                System.out.println("Push");
                endRound = true;
            }

            if((playerHand.cardsValue() > dealerHand.cardsValue()) && endRound == false) {
                System.out.println("\nYou WIN!");
                playerMoney += playerBet;
                endRound =true;
            }else if(endRound == false) {
                System.out.println("You lose.");
                playerMoney -= playerBet;
                endRound = true;

            }

            playerHand.moveAllToDeck(playingDeck);
            dealerHand.moveAllToDeck(playingDeck);
            System.out.println("End of Hand");

        }

        System.out.println("Sorry, you have lost all of your money.");

    }
}

