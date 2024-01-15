package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class Avatar  {
    private int posX;
    private int posY;

    private TETile avatar;

    private World world;
    private World dw;
    private TETile[][] worldMap;
    private TETile[][] dwMap;

    Random random;

    public Avatar(World w, Random rand) {
        random = rand;
        world = w;
        worldMap = world.getWorld();
        dw = new World(w.getWidth(), w.getHeight());
        dwMap = dw.getWorld();
        int x = 0;
        int y = 0;
        while (w.getWorld()[x][y] != Tileset.FLOOR) {
            x = RandomUtils.uniform(random, 0, w.getWidth());
            y = RandomUtils.uniform(random, 0, w.getHeight() - 5);
        }
        posX = x;
        posY = y;
        avatar = Tileset.AVATAR;
        worldMap[posX][posY] = avatar;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

}
