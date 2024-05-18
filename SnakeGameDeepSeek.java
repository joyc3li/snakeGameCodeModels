import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SnakeGameDeepSeek {
    private static final int SCREEN_WIDTH = 20;
    private static final int SCREEN_HEIGHT = 20;
    private static final char SNAKE_BODY = 'O';
    private static final char APPLE = 'A';
    private static final char EMPTY = ' ';
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    private static char[][] board;
    private static Snake snake;
    private static Apple apple;
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        initializeGame();
        gameLoop();
    }

    private static void initializeGame() {
        board = new char[SCREEN_HEIGHT][SCREEN_WIDTH];
        for (int i = 0; i < SCREEN_HEIGHT; i++) {
            for (int j = 0; j < SCREEN_WIDTH; j++) {
                board[i][j] = EMPTY;
            }
        }
        snake = new Snake();
        apple = new Apple();
        apple.respawn(board);
    }

    private static void gameLoop() {
        while (true) {
            drawBoard();
            String input = scanner.nextLine();
            switch (input) {
                case "w":
                    snake.changeDirection(DIRECTIONS[2]);
                    break;
                case "s":
                    snake.changeDirection(DIRECTIONS[3]);
                    break;
                case "a":
                    snake.changeDirection(DIRECTIONS[0]);
                    break;
                case "d":
                    snake.changeDirection(DIRECTIONS[1]);
                    break;
            }
            if (!snake.move(board)) {
                System.out.println("Game Over! Score: " + snake.getScore());
                break;
            }
            if (snake.getHead()[0] == apple.getPosition()[0] && snake.getHead()[1] == apple.getPosition()[1]) {
                snake.grow();
                apple.respawn(board);
            }
        }
    }

    private static void drawBoard() {
        for (int i = 0; i < SCREEN_HEIGHT; i++) {
            for (int j = 0; j < SCREEN_WIDTH; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    static class Snake {
        private List<int[]> body;
        private int[] direction;
        private int score;

        public Snake() {
            body = new ArrayList<>();
            body.add(new int[]{SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2});
            direction = DIRECTIONS[new Random().nextInt(DIRECTIONS.length)];
            score = 0;
        }

        public boolean move(char[][] board) {
            int[] head = getHead();
            int newX = (head[0] + direction[0] + SCREEN_WIDTH) % SCREEN_WIDTH;
            int newY = (head[1] + direction[1] + SCREEN_HEIGHT) % SCREEN_HEIGHT;
            if (board[newY][newX] == SNAKE_BODY) {
                return false;
            }
            body.add(0, new int[]{newX, newY});
            if (board[newY][newX] != APPLE) {
                int[] tail = body.remove(body.size() - 1);
                board[tail[1]][tail[0]] = EMPTY;
            }
            board[newY][newX] = SNAKE_BODY;
            return true;
        }

        public void changeDirection(int[] newDirection) {
            if (newDirection[0] + direction[0] != 0 && newDirection[1] + direction[1] != 0) {
                direction = newDirection;
            }
        }

        public void grow() {
            score++;
        }

        public int getScore() {
            return score;
        }

        public int[] getHead() {
            return body.get(0);
        }
    }

    static class Apple {
        private int[] position;

        public void respawn(char[][] board) {
            Random random = new Random();
            position = new int[]{random.nextInt(SCREEN_WIDTH), random.nextInt(SCREEN_HEIGHT)};
            board[position[1]][position[0]] = APPLE;
        }

        public int[] getPosition() {
            return position;
        }
    }
}
