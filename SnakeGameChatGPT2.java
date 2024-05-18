import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class SnakeGameChatGPT2 {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private static final char BORDER_CHAR = '#';
    private static final char EMPTY_CHAR = ' ';
    private static final char SNAKE_CHAR = 'O';
    private static final char APPLE_CHAR = '@';

    private static int score = 0;
    private static Queue<int[]> snake = new LinkedList<>();
    private static int[] apple = new int[2];
    private static char direction = 'D'; // Initial direction to the right
    private static boolean gameOver = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        initializeGame();
        printBoard();

        while (!gameOver) {
            if (scanner.hasNext()) {
                char input = scanner.next().toUpperCase().charAt(0);
                if (input == 'W' || input == 'A' || input == 'S' || input == 'D') {
                    direction = input;
                }
            }

            moveSnake();
            printBoard();

            try {
                Thread.sleep(500); // Adjust the speed of the game
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Game Over! Your score is: " + score);
    }

    private static void initializeGame() {
        int startX = WIDTH / 2;
        int startY = HEIGHT / 2;
        snake.add(new int[]{startX, startY});
        placeApple();
    }

    private static void placeApple() {
        Random random = new Random();
        boolean validPosition = false;

        while (!validPosition) {
            apple[0] = random.nextInt(HEIGHT - 2) + 1;
            apple[1] = random.nextInt(WIDTH - 2) + 1;
            validPosition = true;

            for (int[] segment : snake) {
                if (segment[0] == apple[0] && segment[1] == apple[1]) {
                    validPosition = false;
                    break;
                }
            }
        }
    }

    private static void moveSnake() {
        int[] head = snake.peek();
        int[] newHead = new int[2];
        newHead[0] = head[0];
        newHead[1] = head[1];

        switch (direction) {
            case 'W': newHead[0]--; break;
            case 'A': newHead[1]--; break;
            case 'S': newHead[0]++; break;
            case 'D': newHead[1]++; break;
        }

        if (newHead[0] <= 0 || newHead[0] >= HEIGHT - 1 || newHead[1] <= 0 || newHead[1] >= WIDTH - 1 || checkCollision(newHead)) {
            gameOver = true;
            return;
        }

        snake.add(newHead);

        if (newHead[0] == apple[0] && newHead[1] == apple[1]) {
            score++;
            placeApple();
        } else {
            snake.poll();
        }
    }

    private static boolean checkCollision(int[] head) {
        for (int[] segment : snake) {
            if (segment[0] == head[0] && segment[1] == head[1]) {
                return true;
            }
        }
        return false;
    }

    private static void printBoard() {
        char[][] board = new char[HEIGHT][WIDTH];

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (i == 0 || i == HEIGHT - 1 || j == 0 || j == WIDTH - 1) {
                    board[i][j] = BORDER_CHAR;
                } else {
                    board[i][j] = EMPTY_CHAR;
                }
            }
        }

        for (int[] segment : snake) {
            board[segment[0]][segment[1]] = SNAKE_CHAR;
        }

        board[apple[0]][apple[1]] = APPLE_CHAR;

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }

        System.out.println("Score: " + score);
    }
}
