package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class Engine {
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;
    public String TXTNAME = "worldFile.txt";
    public long seed;
    public String parsedSeed;
    public World w;
    public TETile[][] worldFrame;
    public World dw;
    public TETile[][] dwFrame;

    public boolean gameOver = false;
    public TERenderer ter;
    public Avatar avatar;
    public int posX;
    public int posY;
    public Out worldFile;
    public boolean hasKey = false;
    public boolean quit = false;
    public boolean displayedMenu = false;
    public boolean promptedSeed = false;


    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        while (!displayedMenu) {
            showMenu();
        }
        while (!promptedSeed) {
            prompt();
        }
        while (!gameOver) {
            if (quit) {
                System.exit(0);
            }
            userInput();
            w.drawTypeTile();
            if (isGameOver()) {
                Font fontTitle = new Font("Monaco", Font.BOLD, 30);
                StdDraw.setPenColor(Color.WHITE);
                StdDraw.setFont(fontTitle);
                StdDraw.clear(Color.BLACK);
                StdDraw.text(WIDTH/2, HEIGHT - (HEIGHT/4), "CONGRATULATIONS YOU WON");
                StdDraw.show();
                StdDraw.pause(5000);
                System.exit(0);
            }
        }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, running both of these:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        return worldFrame;
    }

    public void showMenu() {

        Font fontTitle = new Font("Monaco", Font.BOLD, 30);

        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(fontTitle);
        StdDraw.text(WIDTH/2, HEIGHT - (HEIGHT/4), "Eric & Kaleen CS61B: THE GAME");

        Font fontSubTitle = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(fontSubTitle);
        StdDraw.text(WIDTH/2, HEIGHT/2, "New Game (N)");
        StdDraw.text(WIDTH/2, HEIGHT/2 - 2, "Load Game (L)");
        StdDraw.text(WIDTH/2, HEIGHT/2 - 4, "Replay (R)");
        StdDraw.text(WIDTH/2, HEIGHT/2 - 6, "Quit (Q)");
        StdDraw.show();

        displayedMenu = true;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void prompt() {
        if (StdDraw.hasNextKeyTyped()) {
            char c = StdDraw.nextKeyTyped();
            if (c == 'l' || c == 'L') {
                load();
            } else if (c == 'n' || c == 'N') {
                worldFile = new Out(TXTNAME);
                worldFile.print(c);
                loadNewWorld();
            } else if (c == 'r' || c == 'R') {
                replay();
            }else if (c == 'q' || c == 'Q') {
                quit = true;
            }
            promptedSeed = true;
        }
    }

    public void prompt(char s) {
        if (s == 'l' || s == 'L') {
            load();
        } else if (s == 'n' || s == 'N') {
            worldFile = new Out(TXTNAME);
            worldFile.print(s);
            loadNewWorld();
        } else if (s == 'q' || s == 'Q') {
            quit = true;
        }
        promptedSeed = true;
    }

    public void userInput() {
        if (StdDraw.hasNextKeyTyped()) {
            char c = StdDraw.nextKeyTyped();
            if (c == ':') {
                worldFile.print(c);
            } else if (c == 'w' || c == 'W') {
                worldFile.print(c);
                moveUp();
            } else if (c == 'a' || c == 'A') {
                worldFile.print(c);
                moveLeft();
            } else if (c == 's' || c == 'S') {
                worldFile.print(c);
                moveDown();
            } else if (c == 'd' || c == 'D') {
                worldFile.print(c);
                moveRight();
            } else if (c == 'q' || c == 'Q') {
                quit = quit();
            }
        }
    }

    public void userInput(char s) {
        if (s == 'w' || s == 'W') {
            moveUp();
        } else if (s == 'a' || s == 'A') {
            moveLeft();
        } else if (s == 's' || s == 'S') {
            moveDown();
        } else if (s == 'd' || s == 'D') {
            moveRight();
        }
    }

    private boolean isKey(int x, int y) {
        if (worldFrame[x][y] == Tileset.FLOWER) {
            return true;
        }
        return false;
    }

    public void moveUp() {
        if(worldFrame[posX][posY + 1] != Tileset.WALL) {
            if (isKey(posX, posY + 1)) {
                hasKey = true;
                worldFrame[posX][posY] = Tileset.FLOOR;
                posY += 1;
                worldFrame[posX][posY] = Tileset.AVATAR;
            } else if (worldFrame[posX][posY + 1] == Tileset.FLOOR) {
                worldFrame[posX][posY] = Tileset.FLOOR;
                posY += 1;
                worldFrame[posX][posY] = Tileset.AVATAR;
            } else if (worldFrame[posX][posY + 1] == Tileset.LOCKED_DOOR && hasKey) {
                worldFrame[posX][posY] = Tileset.FLOOR;
                posY += 1;
                worldFrame[posX][posY] = Tileset.UNLOCKED_DOOR;
                gameOver = true;
            }
        }
        ter.renderFrame(worldFrame);
    }

    public void moveDown() {
        if(worldFrame[posX][posY - 1] != Tileset.WALL) {
            if (isKey(posX, posY - 1)) {
                hasKey = true;
                worldFrame[posX][posY] = Tileset.FLOOR;
                posY -= 1;
                worldFrame[posX][posY] = Tileset.AVATAR;
            } else if (worldFrame[posX][posY - 1] == Tileset.FLOOR) {
                worldFrame[posX][posY] = Tileset.FLOOR;
                posY -= 1;
                worldFrame[posX][posY] = Tileset.AVATAR;
            } else if (worldFrame[posX][posY - 1] == Tileset.LOCKED_DOOR && hasKey) {
                worldFrame[posX][posY] = Tileset.FLOOR;
                posY -= 1;
                worldFrame[posX][posY] = Tileset.UNLOCKED_DOOR;
                gameOver = true;
            }
        }
        ter.renderFrame(worldFrame);
    }
    public void moveLeft() {
        if(worldFrame[posX - 1][posY] != Tileset.WALL) {
            if (isKey(posX - 1, posY)) {
                hasKey = true;
                worldFrame[posX][posY] = Tileset.FLOOR;
                posX -= 1;
                worldFrame[posX][posY] = Tileset.AVATAR;
            } else if (worldFrame[posX - 1][posY] == Tileset.FLOOR) {
                worldFrame[posX][posY] = Tileset.FLOOR;
                posX -= 1;
                worldFrame[posX][posY] = Tileset.AVATAR;
            } else if (worldFrame[posX - 1][posY] == Tileset.LOCKED_DOOR && hasKey) {
                worldFrame[posX][posY] = Tileset.FLOOR;
                posX -= 1;
                worldFrame[posX][posY] = Tileset.UNLOCKED_DOOR;
                gameOver = true;
            }
        }
        ter.renderFrame(worldFrame);
    }
    public void moveRight() {
        if(worldFrame[posX + 1][posY] != Tileset.WALL) {
            if (isKey(posX + 1, posY)) {
                hasKey = true;
                worldFrame[posX][posY] = Tileset.FLOOR;
                posX += 1;
                worldFrame[posX][posY] = Tileset.AVATAR;
            } else if (worldFrame[posX + 1][posY] == Tileset.FLOOR) {
                worldFrame[posX][posY] = Tileset.FLOOR;
                posX += 1;
                worldFrame[posX][posY] = Tileset.AVATAR;
            } else if (worldFrame[posX + 1][posY] == Tileset.LOCKED_DOOR && hasKey) {
                worldFrame[posX][posY] = Tileset.FLOOR;
                posX += 1;
                worldFrame[posX][posY] = Tileset.UNLOCKED_DOOR;
                gameOver = true;
            }
        }
        ter.renderFrame(worldFrame);
    }

    public void load() {
        In in = new In(TXTNAME);
        String s = in.readAll();
        int counter = 1;
        String a = "";
        if (s.charAt(0) == 'n' || s.charAt(0) == 'N') {
            while (s.charAt(counter) != 's' && s.charAt(counter) != 'S') {
                a = a + s.charAt(counter);
                counter += 1;
            }
        }
        Long seedL = Long.parseLong(a);
        w = new World(seedL, WIDTH, HEIGHT);
        worldFrame = w.getWorld();
        avatar = w.getAvatar();
        posX = avatar.getPosX();
        posY = avatar.getPosY();
        for (int i = counter + 1; i < s.length(); i++) {
            userInput(s.charAt(i));
        }
        worldFile = new Out(TXTNAME);
        worldFile.print(s);
    }

    public boolean quit() {
        In in = new In(TXTNAME);
        String s = in.readAll();
        if (s.charAt(s.length() - 1) == ':') {
            return true;
        }
        return false;
    }

    public void loadNewWorld() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);

        Font fontSubTitle = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(fontSubTitle);
        StdDraw.text(WIDTH/2, HEIGHT - (HEIGHT/3),"Please enter a random number followed by the letter 'S'!");
        StdDraw.show();

        parsedSeed = "";
        int counter = 0;
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                worldFile.print(c);
                parsedSeed = parsedSeed + c;
                if (c == 's' || c == 'S') {
                    seed = Long.parseLong(parsedSeed.substring(0, counter));
                    break;
                }
                StdDraw.text(WIDTH/2, HEIGHT - (HEIGHT/2), parsedSeed);
                StdDraw.show();
                StdDraw.clear(Color.BLACK);
                counter += 1;
            }
        }
        w = new World(seed, WIDTH, HEIGHT);
        worldFrame = w.getWorld();
        //w = new World(WIDTH, HEIGHT);
        //worldFrame = w.getWorld();
        avatar = w.getAvatar();
        posX = avatar.getPosX();
        posY = avatar.getPosY();
    }

    public void replay() {
        In in = new In(TXTNAME);
        String s = in.readAll();
        int counter = 1;
        String a = "";
        if (s.charAt(0) == 'n' || s.charAt(0) == 'N') {
            while (s.charAt(counter) != 's' && s.charAt(counter) != 'S') {
                a = a + s.charAt(counter);
                counter += 1;
            }
        }
        Long seedL = Long.parseLong(a);
        w = new World(seedL, WIDTH, HEIGHT);
        worldFrame = w.getWorld();
        avatar = w.getAvatar();
        worldFrame = w.getWorld();
        posX = avatar.getPosX();
        posY = avatar.getPosY();
        for (int i = counter + 1; i < s.length(); i++) {
            userInput(s.charAt(i));
            StdDraw.show();
            ter.renderFrame(worldFrame);
            StdDraw.pause(100);
        }
        quit = true;
    }
}

