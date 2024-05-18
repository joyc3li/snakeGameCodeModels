import java.util.Random;
import java.util.Scanner;

public class SnakeGameChatGPT {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private static final char SNAKE_BODY = 'O';
    private static final char SNAKE_HEAD = '@';
    private static final char APPLE = '*';
    private static final char EMPTY_SPACE = ' ';
    private static final char BORDER = '#';
    
    private int[][] board = new int[HEIGHT][WIDTH];
    private int[] snakeX = new int[WIDTH * HEIGHT];
    private int[] snakeY = new int[WIDTH * HEIGHT];
    private int snakeLength;
    private int appleX, appleY;
    private char direction;
    private boolean gameOver;
    private int score;
    
    public SnakeGameChatGPT() {
        initializeGame();
        runGame();
    }
    
    private void initializeGame() {
        snakeLength = 3;
        for (int i = 0; i < snakeLength; i++) {
            snakeX[i] = WIDTH / 2 + i;
            snakeY[i] = HEIGHT / 2;
        }
        direction = 'L';
        spawnApple();
        score = 0;
        gameOver = false;
    }
    
    private void runGame() {
        Scanner scanner = new Scanner(System.in);
        while (!gameOver) {
            printBoard();
            System.out.print("Enter direction (WASD): ");
            char input = scanner.next().charAt(0);
            changeDirection(input);
            updateGame();
        }
        System.out.println("Game Over! Your score is: " + score);
        scanner.close();
    }
    
    private void changeDirection(char input) {
        switch (input) {
            case 'W':
            case 'w':
                if (direction != 'D') direction = 'U';
                break;
            case 'A':
            case 'a':
                if (direction != 'R') direction = 'L';
                break;
            case 'S':
            case 's':
                if (direction != 'U') direction = 'D';
                break;
            case 'D':
            case 'd':
                if (direction != 'L') direction = 'R';
                break;
        }
    }
    
    private void updateGame() {
        int newX = snakeX[0];
        int newY = snakeY[0];
        
        switch (direction) {
            case 'U': newY--; break;
            case 'D': newY++; break;
            case 'L': newX--; break;
            case 'R': newX++; break;
        }
        
        if (newX < 0 || newX >= WIDTH || newY < 0 || newY >= HEIGHT || isSnake(newX, newY)) {
            gameOver = true;
            return;
        }
        
        if (newX == appleX && newY == appleY) {
            snakeLength++;
            score += 1;
            spawnApple();
        }
        
        System.arraycopy(snakeX, 0, snakeX, 1, snakeLength);
        System.arraycopy(snakeY, 0, snakeY, 1, snakeLength);
        
        snakeX[0] = newX;
        snakeY[0] = newY;
    }
    
    private void spawnApple() {
        Random rand = new Random();
        do {
            appleX = rand.nextInt(WIDTH);
            appleY = rand.nextInt(HEIGHT);
        } while (isSnake(appleX, appleY));
    }
    
    private boolean isSnake(int x, int y) {
        for (int i = 0; i < snakeLength; i++) {
            if (snakeX[i] == x && snakeY[i] == y) {
                return true;
            }
        }
        return false;
    }
    
    private void printBoard() {
        clearConsole();
        for (int y = 0; y <= HEIGHT; y++) {
            for (int x = 0; x <= WIDTH; x++) {
                if (y == 0 || y == HEIGHT || x == 0 || x == WIDTH) {
                    System.out.print(BORDER);
                } else if (x == appleX + 1 && y == appleY + 1) {
                    System.out.print(APPLE);
                } else if (x == snakeX[0] + 1 && y == snakeY[0] + 1) {
                    System.out.print(SNAKE_HEAD);
                } else if (isSnake(x - 1, y - 1)) {
                    System.out.print(SNAKE_BODY);
                } else {
                    System.out.print(EMPTY_SPACE);
                }
            }
            System.out.println();
        }
        System.out.println("Score: " + score);
    }
    
    private void clearConsole() {
        // This is an attempt to clear the console screen. It might not work in all environments.
        // You might need to adjust it according to your operating system and console.
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new SnakeGameChatGPT();
    }
}
