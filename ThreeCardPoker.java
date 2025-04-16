import java.util.*;

public class ThreeCardPoker {
    private Stack<String> playerHistory; // Keeps track of hands or bets
    private List<String> deck; // Represents the deck of cards
    private int playerMoney; // Player's current money
    private LinkedList<String> playerHand; // Player's hand (LinkedList for easy manipulation)

    private static Scanner scanner = new Scanner(System.in); // Single Scanner instance
// Ln(1-10) I wrote, this is the main class where the player history is tracked using a stack, so if you want to see player history it will be here
//tracks how much money player has, also stores players hand with linked list
    public ThreeCardPoker() {
        this.playerHistory = new Stack<>();
        this.deck = initializeDeck();
        this.playerMoney = 1000; // Starting money
        this.playerHand = new LinkedList<>();
    }

    // Initialize deck with all cards
    private List<String> initializeDeck() {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        List<String> deck = new ArrayList<>();
        for (String suit : suits) {
            for (String rank : ranks) {
                deck.add(rank + " of " + suit);
            }
        }
        Collections.shuffle(deck); // Shuffle the deck
        return deck;
    } //Ln(20-31) I also wrote but with some google help, it is the deck initialization
      // Arrays store the suits and ranks of the cards, it shuffles as well, randomizing the deck
    // Recursive method to calculate hand value
    private int recursiveHandValue(List<String> hand, int index) {
        if (index == hand.size()) return 0;
        String rank = hand.get(index).split(" ")[0];
        int value = switch (rank) {
            case "Jack", "Queen", "King" -> 10;
            case "Ace" -> 11;
            default -> Integer.parseInt(rank);
        };
        return value + recursiveHandValue(hand, index + 1);
    }//Ln(34-42) I used ChatGPT to help me with line 40 and 42, other wise I wrote it
     // This uses recursion to calculate the total value of a hand, then it stops recursion when all cards are processed
// Deal cards to the player
    private void dealCards() {
        playerHand.clear();
        for (int i = 0; i < 3; i++) {
            playerHand.add(deck.remove(0));
        }
    } //Ln(46-50) I wrote this it deals the cards, ensures no leftovers, I was proud I new how to do this quickly

    // Simulate a round of the game
    private void playRound() {
        if (playerMoney <= 0) {
            System.out.println("You're out of money! Game over.");
            return;
        } 

        System.out.println("\nYou have $" + playerMoney);
        System.out.print("Enter your bet (minimum $50): ");
        int bet = scanner.nextInt();
        if (bet < 50 || bet > playerMoney) {
            System.out.println("Invalid bet. Try again.");
            return;
        }

        // Deal cards to the player
        dealCards();
        System.out.println("Your hand: " + playerHand);

        // Fold or Continue Option
        System.out.println("Do you want to:\n1. Fold (Lose your bet and end the round)\n2. Continue (Play the round)");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        if (choice == 1) {
            playerMoney -= bet;
            System.out.println("You folded! You lost $" + bet + ". Remaining balance: $" + playerMoney);
            playerHistory.push("Folded, lost bet: $" + bet);
            return;
        } else if (choice != 2) {
            System.out.println("Invalid choice. Folding by default.");
            playerMoney -= bet;
            playerHistory.push("Folded, lost bet: $" + bet);
            return;
        } 
          // Simulate dealer's hand
        List<String> dealerHand = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            dealerHand.add(deck.remove(0));
        }
        int handValue = recursiveHandValue(playerHand, 0);
        int dealerValue = recursiveHandValue(dealerHand, 0);

        System.out.println("Dealer's hand: " + dealerHand);
        System.out.println("Your hand value: " + handValue);
        System.out.println("Dealer's hand value: " + dealerValue);

        // Determine winner
        if (handValue > dealerValue) {
            playerMoney += bet;
            System.out.println("You win! New balance: $" + playerMoney);
        } else if (handValue < dealerValue) {
            playerMoney -= bet;
            System.out.println("You lose! New balance: $" + playerMoney);
        } else {
            System.out.println("It's a tie! No money exchanged.");
        }

        // Record this round in the history
        playerHistory.push("Bet: $" + bet + ", Hand: " + playerHand + ", Dealer: " + dealerHand);
    }
     //Ln(54-113)I did not write all of these lines, I used google and ChatGPT to help me with this.
    // these lines ensure the bet is in bounds and I did write the line that allows the player to fold or play the hand
   //It also contains the hand values to determine winner, which I found online
  //I would say I wrote about half of these lines like the determining winner and fold option, the rest is from online
          
    // Display the history of bets
    private void displayHistory() {
        if (playerHistory.isEmpty()) {
            System.out.println("No history yet.");
        } else {
            System.out.println("Game History:");
            for (String record : playerHistory) {
                System.out.println(record);
            }
        }
    } //Ln(120-127) I wrote this with a little google help, it gives the option to display the previous rounds
      //It is a stack interation, where it prints each record from the stack
    public static void main(String[] args) {
        ThreeCardPoker game = new ThreeCardPoker();

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Play a round");
            System.out.println("2. View history");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> game.playRound();
                case 2 -> game.displayHistory();
                case 3 -> {
                    System.out.println("Thanks for playing! Goodbye!");
                    scanner.close(); // Close the scanner before exiting
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
//Ln(131-154) I WROTE THIS! i wrote the main method by myself, I know it is simple but it was like the icing on the cake when I finished
//It lets the user play, view history, or exit (1,2,3) and it has a game loop, letting the player choose when to leave
//I struggled to figure this out, but I finally closed the scanner in the main method as well 
//Overall, I wrote most the program, but I did get alot of help from google and ChatGPT as well