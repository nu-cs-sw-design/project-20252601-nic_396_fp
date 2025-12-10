import java.util.Scanner;
import java.util.List;

public class UI {
    private final Scanner scanner;
    private final Game game;

    public UI(Game game) {
        this.scanner = new Scanner(System.in);
        this.game = game;
    }

    public void play() {
        System.out.println("=== WELCOME TO EUCHRE ===");
        System.out.println("Team 1: " + game.getTeamOne().getPOne().getName() + " & " + game.getTeamOne().getPTwo().getName());
        System.out.println("Team 2: " + game.getTeamTwo().getPOne().getName() + " & " + game.getTeamTwo().getPTwo().getName());
        System.out.println("First to 10 points wins!\n");

        while (!game.isGameOver()) {
            playRound();
            game.nextDealer();
        }

        announceWinner();
    }

    private void playRound() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("NEW ROUND");
        System.out.println(game.getDealer().getName() + " is dealing");
        displayScore();
        System.out.println("=".repeat(60));

        game.startNewRound();
        Trump trump = conductBidding();
        if (trump == null) {
            System.out.println("All players passed! Redealing...");
            return;
        }

        System.out.println("\n>>> " + trump.toString() + "\n");

        int leadPlayerIndex = (game.getDealerIndex() + 1) % 4;
        for (int trickNum = 0; trickNum < 5; trickNum++) {
            System.out.println("\n--- Trick " + (trickNum + 1) + " of 5 ---");
            Player winner = playTrick(leadPlayerIndex, trump);
            System.out.println(">>> " + winner.getName() + " wins the trick!");
            leadPlayerIndex = game.getPlayers().indexOf(winner);

            waitForEnter();
        }

        game.updateScore();
        displayRoundSummary();
        waitForEnter();
    }

    private Trump conductBidding() {
        Card turnUpCard = game.drawTurnUpCard();
        System.out.println("\n--- BIDDING PHASE ---");
        System.out.println("Turn-up card: " + formatCard(turnUpCard));

        System.out.println("\n=== Round 1: Order Up? ===");
        int startPlayer = (game.getDealerIndex() + 1) % 4;

        for (int i = 0; i < 4; i++) {
            int playerIndex = (startPlayer + i) % 4;
            Player player = game.getPlayers().get(playerIndex);
            boolean isDealer = (playerIndex == game.getDealerIndex());

            displayPlayerHand(player);

            if (promptOrderUp(player, turnUpCard, isDealer)) {
                if (isDealer) {
                    Card discard = promptDiscardCard(player);
                    player.getHand().removeCard(discard);
                    System.out.println(player.getName() + " discards " + formatCard(discard));
                    player.getHand().addCard(turnUpCard);
                }
                boolean goingAlone = promptGoAlone(player);
                game.setTrump(turnUpCard.getSuit(), player, goingAlone);
                return game.getCurrentTrump();
            }
        }

        System.out.println("\n=== Round 2: Call Trump? ===");
        System.out.println("Cannot call: " + turnUpCard.getSuit());

        for (int i = 0; i < 4; i++) {
            int playerIndex = (startPlayer + i) % 4;
            Player player = game.getPlayers().get(playerIndex);

            displayPlayerHand(player);

            CardSuit chosenSuit = promptCallTrump(player, turnUpCard.getSuit());
            if (chosenSuit != null) {
                boolean goingAlone = promptGoAlone(player);
                game.setTrump(chosenSuit, player, goingAlone);
                return game.getCurrentTrump();
            }
        }

        return null;
    }

    private Player playTrick(int startPlayerIndex, Trump trump) {
        Trick trick = new Trick(trump);

        int playersInTrick = trump.isGoingAlone() ? 3 : 4;
        int playersAdded = 0;

        for (int i = 0; i < 4 && playersAdded < playersInTrick; i++) {
            int playerIndex = (startPlayerIndex + i) % 4;
            Player player = game.getPlayers().get(playerIndex);

            // Skip if partner going alone
            if (trump.isGoingAlone() && isPartner(player, trump.getCaller())
                    && !player.equals(trump.getCaller())) {
                System.out.println(player.getName() + " sits out (partner going alone)");
                continue;
            }

            displayTrickState(trick, trump);
            Card card = promptPlayCard(player, trick);
            player.getHand().removeCard(card);
            trick.addPlay(card, player);

            System.out.println(">>> " + player.getName() + " plays " + formatCard(card));
            playersAdded++;
        }

        Player winner = trick.getWinner();
        winner.addTrick();
        return winner;
    }

    private boolean promptOrderUp(Player player, Card turnUpCard, boolean isDealer) {
        String action = isDealer ? "pick it up" : "order it up";
        System.out.print(player.getName() + ", " + action + "? (y/n): ");
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.equals("y") || response.equals("yes")) {
            System.out.println(player.getName() + " orders up " + turnUpCard.getSuit());
            return true;
        }

        System.out.println(player.getName() + " passes");
        return false;
    }

    private CardSuit promptCallTrump(Player player, CardSuit turnDownSuit) {
        System.out.println(player.getName() + ", call trump?");
        System.out.println("1. " + CardSuit.HEART);
        System.out.println("2. " + CardSuit.DIAMOND);
        System.out.println("3. " + CardSuit.CLUB);
        System.out.println("4. " + CardSuit.SPADE);
        System.out.println("5. Pass");
        System.out.print("Enter choice (1-5): ");

        String input = scanner.nextLine().trim();

        try {
            int choice = Integer.parseInt(input);
            CardSuit[] suits = CardSuit.values();

            if (choice >= 1 && choice <= 4) {
                CardSuit chosen = suits[choice - 1];
                if (chosen == turnDownSuit) {
                    System.out.println("Cannot call the turn-down suit!");
                    return promptCallTrump(player, turnDownSuit);
                }
                System.out.println(player.getName() + " calls " + chosen);
                return chosen;
            } else if (choice == 5) {
                System.out.println(player.getName() + " passes");
                return null;
            }
        } catch (NumberFormatException e) {
        }

        System.out.println("Invalid choice. Try again.");
        return promptCallTrump(player, turnDownSuit);
    }

    private boolean promptGoAlone(Player player) {
        System.out.print(player.getName() + ", go alone? (y/n): ");
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.equals("y") || response.equals("yes")) {
            System.out.println(player.getName() + " goes alone!");
            return true;
        }
        return false;
    }

    private Card promptDiscardCard(Player player) {
        System.out.println("\n" + player.getName() + ", choose a card to discard:");
        displayPlayerHand(player);
        System.out.print("Enter card number (0-" + (player.getHand().size() - 1) + "): ");

        try {
            int index = Integer.parseInt(scanner.nextLine().trim());
            if (index >= 0 && index < player.getHand().size()) {
                return player.getHand().getCard(index);
            }
        } catch (NumberFormatException e) {
        }

        System.out.println("Invalid choice. Try again.");
        return promptDiscardCard(player);
    }

    private Card promptPlayCard(Player player, Trick trick) {
        System.out.println("\n" + player.getName() + "'s turn:");
        displayPlayerHand(player);

        if (trick.size() > 0) {
            System.out.println("Led suit: " + trick.getLedSuit());
        }

        System.out.print("Enter card number to play (0-" + (player.getHand().size() - 1) + "): ");

        try {
            int index = Integer.parseInt(scanner.nextLine().trim());
            if (index >= 0 && index < player.getHand().size()) {
                Card card = player.getHand().getCard(index);

                if (trick.isLegalPlay(card, player.getHand())) {
                    return card;
                } else {
                    System.out.println("Illegal play! You must follow suit if possible.");
                    return promptPlayCard(player, trick);
                }
            }
        } catch (NumberFormatException e) {
        }

        System.out.println("Invalid choice. Try again.");
        return promptPlayCard(player, trick);
    }

    private void displayPlayerHand(Player player) {
        System.out.println("\n" + player.getName() + "'s hand:");
        Hand hand = player.getHand();
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.getCard(i);
            System.out.println("  " + i + ": " + formatCard(card));
        }
    }

    private void displayTrickState(Trick trick, Trump trump) {
        if (trick.size() > 0) {
            System.out.println("\nCurrent trick:");
            for (Trick.CardPlay play : trick.getPlays()) {
                System.out.println("  " + play.getPlayer().getName() + ": " + formatCard(play.getCard()));
            }
            System.out.println("Led suit: " + trick.getLedSuit());
        }
    }

    private void displayScore() {
        System.out.println("Score - Team 1: " + game.getTeamOneScore() + " | Team 2: " + game.getTeamTwoScore());
    }

    private void displayRoundSummary() {
        System.out.println("\n=== ROUND COMPLETE ===");
        Team t1 = game.getTeamOne();
        Team t2 = game.getTeamTwo();
        System.out.println("Team 1 tricks: " + t1.tricksWon());
        System.out.println("Team 2 tricks: " + t2.tricksWon());
        displayScore();
    }

    private void announceWinner() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("GAME OVER!");
        displayScore();

        Team winner = game.checkWin();
        if (winner == game.getTeamOne()) {
            System.out.println("*** TEAM 1 WINS! ***");
            System.out.println(winner.getPOne().getName() + " & " + winner.getPTwo().getName());
        } else {
            System.out.println("*** TEAM 2 WINS! ***");
            System.out.println(winner.getPOne().getName() + " & " + winner.getPTwo().getName());
        }
        System.out.println("=".repeat(60));
    }

    private String formatCard(Card card) {
        return card.getValue() + " of " + card.getSuit();
    }

    private void waitForEnter() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private boolean isPartner(Player p1, Player p2) {
        Team t1 = game.getTeamOne();
        Team t2 = game.getTeamTwo();
        return (t1.getPOne().equals(p1) && t1.getPTwo().equals(p2)) ||
                (t1.getPTwo().equals(p1) && t1.getPOne().equals(p2)) ||
                (t2.getPOne().equals(p1) && t2.getPTwo().equals(p2)) ||
                (t2.getPTwo().equals(p1) && t2.getPOne().equals(p2));
    }
}