import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== WELCOME TO EUCHRE ===\n");
        System.out.println("Enter player names for the game.");
        System.out.println("Teams will be: Player 1 & Player 3 vs Player 2 & Player 4\n");

        // Get player names
        System.out.print("Player 1 name: ");
        String name1 = scanner.nextLine().trim();
        if (name1.isEmpty()) name1 = "Player 1";

        System.out.print("Player 2 name: ");
        String name2 = scanner.nextLine().trim();
        if (name2.isEmpty()) name2 = "Player 2";

        System.out.print("Player 3 name: ");
        String name3 = scanner.nextLine().trim();
        if (name3.isEmpty()) name3 = "Player 3";

        System.out.print("Player 4 name: ");
        String name4 = scanner.nextLine().trim();
        if (name4.isEmpty()) name4 = "Player 4";

        // Create players
        Player player1 = new Player(name1);
        Player player2 = new Player(name2);
        Player player3 = new Player(name3);
        Player player4 = new Player(name4);

        // Create game (Player 1 & 3 vs Player 2 & 4)
        Game game = new Game(player1, player2, player3, player4);

        // Create UI and start the game
        UI ui = new UI(game);
        ui.play();

        scanner.close();
    }
}