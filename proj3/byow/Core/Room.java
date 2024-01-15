package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.HashMap;
import java.util.Set;

public class Room {
    private int height;
    private int width;
    private int row;
    private int col;
    public HashMap<Integer, Set<Integer>> validCoord;

    public Room(int a, int b, int c, int d, HashMap<Integer, Set<Integer>> validC) {
        height = a;
        width = b;
        row = c;
        col = d;
        validCoord = validC;
    }

    public boolean isValid(int x, int y) {
        for (int i = row; i < row + height; i++) {
            for (int j = col; j < col + width; j++) {
                if (validCoord.get(i).contains(j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void makeRoom(TETile[][] map) {
        for (int i = col; i < col + width; i++) {
            for (int j = row; j < row + height; j++) {
                map[i][j] = Tileset.FLOOR;
            }
        }
        makeWall(map);
    }

    public void makeWall(TETile[][] map) {
        for (int i = row; i < row + height; i++) {
            map[col][i] = Tileset.WALL;
        }
        for (int i = row; i < row + height; i++) {
            map[col + width - 1][i] = Tileset.WALL;
        }
        for (int i = col; i < col + width; i++) {
            map[i][row] = Tileset.WALL;
        }
        for (int i = col; i < col + width; i++) {
            map[i][row + height - 1] = Tileset.WALL;
        }
    }

    public int[] getCenter() {
        int[] center = new int[2];
        int x = (col + width - 1) - (width / 2);
        int y = (row + height - 1) - (height/ 2);
        center[0] = x;
        center[1] = y;
        return center;
    }
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
