package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.*;


public class World {
    public HashMap<Integer, Set<Integer>> validCoordinates;
    public ArrayList<Room> listofRooms;
    private Key key;
    public static final int NUMROOMS = 10;
    private int WIDTH;
    private int HEIGHT;
    private static final int MINROOMSIZE = 5;
    private static final int MAXROOMSIZE = 15;

    private TETile[][] world;
    private Random random;

    private Avatar avatar;
    private TERenderer ter;


    public World(long seed, int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        random = new Random(seed);
        validCoordinates = new HashMap<>();
        ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        world = new TETile[WIDTH][HEIGHT];
        for (int i = 0; i < HEIGHT - 5; i++) {
            validCoordinates.put(i, new HashSet<>());
        }
        makeWorld(NUMROOMS);
        key = new Key(this, random);
        avatar = new Avatar(this, random);
        Door door = new Door(this, random);
        //ter.renderFrame(world);
    }

    public World(int width, int height) {
        ter = new TERenderer();
        WIDTH = width;
        HEIGHT = height;
        world = new TETile[WIDTH][HEIGHT];
        fillWithNothing();
        ter.renderFrame(world);
    }

    public TETile[][] getWorld() {
        return world;
    }

    public void fillWithNothing() {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    public void makeWorld(int x) {
        fillWithNothing();
        int rooms = 0;
        listofRooms = new ArrayList<>();
        while (rooms <= x) {
            int height = RandomUtils.uniform(random, MINROOMSIZE, MAXROOMSIZE);
            int width = RandomUtils.uniform(random, MINROOMSIZE, MAXROOMSIZE);
            int row = RandomUtils.uniform(random, 0, HEIGHT - 5);
            int col = RandomUtils.uniform(random, 0, WIDTH);
            Room p = new Room(height, width, row, col, validCoordinates);
            if (col + width <= WIDTH && row + height <= HEIGHT - 5 && p.isValid(WIDTH, HEIGHT - 5)) {
                p.makeRoom(world);
                addCoords(p);
                rooms += 1;
                listofRooms.add(p);
            }
        }
        connect();
    }

    public void addCoords(Room x) {
        for (int i = x.getRow(); i < x.getRow() + x.getHeight(); i++) {
            for (int j = x.getCol(); j < x.getCol() + x.getWidth(); j++) {
                if (validCoordinates.containsKey(i)) {
                    validCoordinates.get(i).add(j);
                } else {
                    Set<Integer> k = new HashSet<>();
                    k.add(j);
                    validCoordinates.put(i, k);
                }
            }
        }
    }

    public void makeVWalls(int x, int y) {
        if (x + 1 < WIDTH && world[x + 1][y] == Tileset.NOTHING) {
            world[x + 1][y] = Tileset.WALL;
        }
        if (x - 1 >= 0 && world[x - 1][y] == Tileset.NOTHING) {
            world[x - 1][y] = Tileset.WALL;
        }
    }

    public void makeHWalls(int x, int y) {
        if (y + 1 < HEIGHT - 5 && world[x][y + 1] == Tileset.NOTHING) {
            world[x][y + 1] = Tileset.WALL;
        }
        if (y - 1 >= 0 && world[x][y - 1] == Tileset.NOTHING) {
            world[x][y - 1] = Tileset.WALL;
        }
    }
    public void connect() {
        for (int i = 0; i < listofRooms.size() - 1; i++) {
            int room1X = listofRooms.get(i).getCenter()[0];
            int room2X = listofRooms.get(i + 1).getCenter()[0];
            int room1Y = listofRooms.get(i).getCenter()[1];
            int room2Y = listofRooms.get(i + 1).getCenter()[1];
            if (room1X <= room2X && room1Y <= room2Y) {
                while (room1Y != room2Y) {
                    world[room1X][room1Y] = Tileset.FLOOR;
                    room1Y += 1;
                    makeVWalls(room1X, room1Y);
                }
                makeVWalls(room1X, room1Y + 1);
                while (room1X != room2X) {
                    world[room1X][room1Y] = Tileset.FLOOR;
                    makeHWalls(room1X, room1Y);
                    room1X += 1;
                }
            } else if (room1X <= room2X && room1Y >= room2Y) {
                while (room1Y != room2Y) {
                    world[room1X][room1Y] = Tileset.FLOOR;
                    room1Y -= 1;
                    makeVWalls(room1X, room1Y);
                }
                makeVWalls(room1X, room1Y - 1);
                while (room1X != room2X) {
                    world[room1X][room1Y] = Tileset.FLOOR;
                    makeHWalls(room1X, room1Y);
                    room1X += 1;
                }
            } else if (room1X >= room2X && room1Y <= room2Y) {
                while (room1Y != room2Y) {
                    world[room1X][room1Y] = Tileset.FLOOR;
                    room1Y += 1;
                    makeVWalls(room1X, room1Y);
                }
                makeVWalls(room1X, room1Y + 1);
                while (room1X != room2X) {
                    world[room1X][room1Y] = Tileset.FLOOR;
                    makeHWalls(room1X, room1Y);
                    room1X -= 1;
                }
            } else if (room1X >= room2X && room1Y >= room2Y) {
                while (room1Y != room2Y) {
                    world[room1X][room1Y] = Tileset.FLOOR;
                    room1Y -= 1;
                    makeVWalls(room1X, room1Y);
                }
                makeVWalls(room1X, room1Y - 1);
                while (room1X != room2X) {
                    world[room1X][room1Y] = Tileset.FLOOR;
                    makeHWalls(room1X, room1Y);
                    room1X -= 1;
                }
            }
        }
    }

    public void drawTypeTile() {
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        if (0 <= x && x < WIDTH && 0 <= y && y < HEIGHT) {
            for (int i = 0; i < world[x][y].description().length(); i++) {
                world[i + 1][HEIGHT-1] = new TETile(world[x][y].description().charAt(i), Color.white, Color.black, world[x][y].description());
            }
            StdDraw.pause(10);
            ter.renderFrame(world);
            for (int j = 0; j < world[x][y].description().length(); j++) {
                world[j+1][HEIGHT-1] = Tileset.NOTHING;
            }
        }
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public TERenderer getTer() {
        return ter;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public ArrayList<Room> getRooms() {
        return listofRooms;
    }

}