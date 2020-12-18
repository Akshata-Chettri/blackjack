package com.java.blackjack;

import java.util.ArrayList;

public class BlackJackCalculation {
	
	private String nameOfPlayer;
	private ArrayList<Card> cardContents;
	public int netValue;
	
	public BlackJackCalculation(String player) {
		nameOfPlayer = player;
		cardContents = new ArrayList<Card>();
		netValue = 0;
	}
	
	public void displayPlayedCard() {
		System.out.println("\n" +nameOfPlayer+ ":");
		for (Card card: cardContents) {
			card.displayCardDetails();
		}
		System.out.println("\n" +nameOfPlayer+ "'s total: " + netValue);
	}
	
	public void displayInitialCard() {
		System.out.println(nameOfPlayer + ":");
		System.out.println("<Face Down Card>");
		for (int i = 1; i<cardContents.size(); i++) {
			cardContents.get(i).displayCardDetails();
		}
	}
	
	public void takeCard(Card card, boolean showCard) {
		cardContents.add(card);
		getNetValue(card);
		if (showCard) {
			System.out.print(nameOfPlayer + " takes a card: ");
			card.displayCardDetails();
			System.out.println();
		}
	}

	public void getNetValue(Card dealtCard) {
		
		if (dealtCard.rank.equals("A")){
			if (netValue <= 10) {
				netValue += 11;
			}
			else {
				netValue += 1;
			}
		}
		else {
			netValue += dealtCard.value;
		}

	}
}
