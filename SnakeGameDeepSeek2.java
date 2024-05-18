import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SnakeGameDeepSeek2 {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private static final char SNAKE_BODY = '#';
    private static final char APPLE = '@';
    private static final char BORDER = '*';

    private static final int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // Right, Down, Left, Up
    private static int direction = 0; // Initial direction is right

    private static List<int[]> snake = new ArrayList<>();
    private static int[] apple;
    private static int score = 0;

    public static void main(String[] args) {
        initializeGame();
        playGame();
    }

    private static void initializeGame() {
        snake.add(new int[]{HEIGHT / 2, WIDTH / 2}); // Initial snake position
        snake.add(new int[]{HEIGHT / 2, WIDTH / 2 - 1}); // Initial snake body
        generateApple();
    }

    private static void playGame() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printBoard();
            System.out.println("Score: " + score);
            System.out.println("W - Up, A - Left, S - Down, D - Right");

            char input = scanner.nextLine().toLowerCase().charAt(0);
            switch (input) {
                case 'w':
                    direction = 3; // Up
                    break;
                case 'a':
                    direction = 2; // Left
                    break;
                case 's':
                    direction = 1; // Down
                    break;
                case 'd':
                    direction = 0; // Right
                    break;
            }

            int[] head = snake.get(0);
            int[] newHead = new int[]{head[0] + directions[direction][0], head[1] + directions[direction][1]};

            // Check if the snake hit the border or itself
            if (newHead[0] < 0 || newHead[0] >= HEIGHT || newHead[1] < 0 || newHead[1] >= WIDTH || snake.contains(newHead)) {
                System.out.println("Game Over!");
                break;
            }

            // Check if the snake ate the apple
            if (newHead[0] == apple[0] && newHead[1] == apple[1]) {
                snake.add(0, newHead); // Grow the snake
                generateApple();
                score++;
            } else {
                snake.add(0, newHead); // Move the snake
                snake.remove(snake.size() - 1); // Remove the tail
            }
        }
        scanner.close();
    }

    private static void printBoard() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (i == 0 || i == HEIGHT - 1 || j == 0 || j == WIDTH - 1) {
                    System.out.print(BORDER);
                } else if (i == apple[0] && j == apple[1]) {
                    System.out.print(APPLE);
                } else if (snake.contains(new int[]{i, j})) {
                    System.out.print(SNAKE_BODY);
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    private static void generateApple() {
        Random random = new Random();
        do {
            apple = new int[]{random.nextInt(HEIGHT - 2) + 1, random.nextInt(WIDTH - 2) + 1};
        } while (snake.contains(apple));
    }
}
