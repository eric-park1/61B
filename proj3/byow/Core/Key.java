package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class Key {
    private TETile[][] worldMap;

    private TETile key = Tileset.FLOWER;
    private TERenderer ter;
    private Random random;

    public Key(World world, Random rand) {
        random = rand;
        Room withKey = world.getRooms().get(RandomUtils.uniform(random, 0, world.getRooms().size()));
        worldMap = world.getWorld();
        ter = world.getTer();
        int randomCol = RandomUtils.uniform(random, withKey.getCol() + 1, withKey.getCol() + withKey.getWidth() - 1);
        int randomWidth = RandomUtils.uniform(random, withKey.getRow() + 1, withKey.getRow() + withKey.getHeight() - 1);
        worldMap[randomCol][randomWidth] = key;
        ter.renderFrame(worldMap);
    }
}
