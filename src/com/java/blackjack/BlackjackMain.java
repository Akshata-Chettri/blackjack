package com.java.blackjack;

import java.util.Scanner;

public class BlackjackMain {

	static String playerName, nextMove;
	static Deck deck = new Deck();
	static boolean gameOver = false;
	static BlackJackCalculation dealerPlays;
	static BlackJackCalculation playerPlays;
	static int dealerWinCount = 0, playerWinCount = 0;
	static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {

		System.out.println("Welcome to Blackjack!\n");

		String response = "Y";
		System.out.print("Enter player's name: ");
		playerName = input.next().toUpperCase();
		System.out.println("\n" +playerName+ " VS Dealer");
		System.out.println("Lets Start!");

		while (response.equalsIgnoreCase("Y")) {

			System.out.println("\nYour Choice");
			System.out.println("1: NEW DECK");
			System.out.println("2: PLAY BLACKJACK");
			System.out.println("3: RESET GAME");
			System.out.println("4: QUIT GAME");
			int line = input.nextInt();

			switch (line) {

			case 1: {
				System.out.println("New Deck.\n**************\n");
				deck = new Deck();
				System.out.println("Shuffling Deck\n**************");
				deck.shuffle();
				System.out.println("Deck Shuffled\n\n--------------\nDisplaying Deck");
				deck.displayDeck();
				System.out.println("--------------");
				break;
			}
			case 2: {
				try {
					startPlay();
					gameOver = false;
					System.out.println(
							"\n" + playerName + ", Do you want to play again? \nPress 'Y' for Yes or 'N' for No");
					response = input.next();
					if (!response.equalsIgnoreCase("Y")) {
						System.out.println("\nThankyou for playing BlackJack!");
						return;
					}
				} catch (IndexOutOfBoundsException e) {
					System.out.println("Exception thrown:" + e);
					System.out.println("No more cards! Go back to menu for new deck.");
				}
				break;
			}
			case 3: {
				System.out.println("Game is Now Reset.\nNew Deck.\n**************\n");
				deck = new Deck();
				System.out.println("Shuffling Deck\n**************");
				deck.shuffle();
				System.out.println("Deck Shuffled\n--------------\nDisplaying Deck");
				deck.displayDeck();
				System.out.println("--------------");
				break;
			}
			case 4: {
				System.out.println("\nThankyou for playing BlackJack!");
				response = "N";
				break;
			}
			default: {
				System.out.println("That is not an option!");
				System.out.println("Choose from Menu");
				break;
			}
			}
		}
	}

	public static void startPlay() {
		dealerPlays = new BlackJackCalculation("Dealer");
		playerPlays = new BlackJackCalculation(playerName);

		deck.deal(dealerPlays, false);
		deck.deal(playerPlays, false);
		deck.deal(dealerPlays, false);
		deck.deal(playerPlays, false);
		System.out.println("\n" + playerName + "'s Turn");
		revealCard();
		while (!gameOver) {
			askPlayer();
		}
	}

	public static void askPlayer() {
		System.out.println("\nPress H(Hit) or S(Stay):");
		nextMove = input.next();
		if (nextMove.equalsIgnoreCase("H")) {
			deck.deal(playerPlays, true);
			if (playerPlays.netValue > 21) {
				System.out.println("\n" + playerName + "'s total : over 21!");
				dealerWins();
			} else {
				revealCard();
			}
		} else if (nextMove.equalsIgnoreCase("S")) {
			System.out.println("\n" + playerName + " Stands");
			while (!gameOver) {
				System.out.println("\nDealer's Turn");
				dealerPlays.displayPlayedCard();
				deck.deal(dealerPlays, true);
				dealerPlays();
			}
		} else {
			System.out.println("Pick either H or S.");
		}
	}

	public static void dealerPlays() {
		if (dealerPlays.netValue > 21) {
			System.out.println("Dealer's total : over 21!");
			playerWins();
		} else if (dealerPlays.netValue <= 16) {
			deck.deal(dealerPlays, true);
		} else {
			System.out.println("\nDealer Stands.");
			if (playerPlays.netValue < dealerPlays.netValue) {
				dealerWins();
			} else if (playerPlays.netValue > dealerPlays.netValue) {
				playerWins();
			} else {
				System.out.println("\nIt's a tie!\nNo Points Added");
				endResult();
				gameOver = true;
			}
		}
	}

	public static void revealCard() {
		dealerPlays.displayInitialCard();
		playerPlays.displayPlayedCard();
	}

	public static void dealerWins() {

		dealerWinCount += 1;
		endResult();
		System.out.println("Dealer wins!");
		gameOver = true;
	}

	public static void playerWins() {

		playerWinCount += 1;
		endResult();
		System.out.println(playerName + "wins!");
		gameOver = true;
	}

	public static void endResult() {
		System.out.println("\n********************");
		System.out.println("RESULTS:");
		dealerPlays.displayPlayedCard();
		playerPlays.displayPlayedCard();
		System.out.println(
				"\nDealer Win Count: " + dealerWinCount + "\n" + playerName + " Win Count: " + playerWinCount + "\n");
	}

}
