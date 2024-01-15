package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

public class Door {
    private ArrayList<Room> listOfRooms;
    private TETile[][] worldMap;
    private TERenderer ter;
    private Random random;

    public Door(World world, Random rand) {
        random = rand;
        listOfRooms = world.getRooms();
        worldMap = world.getWorld();
        ter = world.getTer();
        int door = 0;
        while (door == 0) {
            if (makeDoor(listOfRooms.get(RandomUtils.uniform(random, 0, listOfRooms.size())))) {
                door += 1;
            }
        }
    }

    public boolean makeDoor(Room r) {
        int randomCol = random.nextBoolean() ? r.getCol() : r.getCol() + r.getWidth();
        int randomRow = random.nextBoolean() ? r.getRow() : r.getRow() + r.getHeight();
        int newRandom = RandomUtils.uniform(random, 1, 3);
        if (newRandom == 1) {
            int a = RandomUtils.uniform(random, r.getRow() + 1, r.getRow() + r.getHeight());
            if (isValidDoor(randomCol, a)) {
                worldMap[randomCol][a] = Tileset.LOCKED_DOOR;
                ter.renderFrame(worldMap);
                return true;
            }
        } else if (newRandom == 2) {
            int a = RandomUtils.uniform(random, r.getCol() + 1, r.getCol() + r.getWidth());
            if (isValidDoor(a, randomRow)) {
                worldMap[a][randomRow] = Tileset.LOCKED_DOOR;
                ter.renderFrame(worldMap);
                return true;
            }
        }
        return false;
    }


    public boolean isValidDoor(int x, int y) {
        int numWalls = 0;
        if (worldMap[x][y] == Tileset.WALL) {
            if ((worldMap[x + 1][y] == Tileset.WALL && worldMap[x - 1][y] == Tileset.WALL)
                    || (worldMap[x][y + 1] == Tileset.WALL && worldMap[x][y - 1] == Tileset.WALL)) {
                return true;
            }
        }
        return false;
    }
}