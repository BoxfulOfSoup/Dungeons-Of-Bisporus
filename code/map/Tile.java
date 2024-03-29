package code.map;

import java.awt.Graphics;

import code.prop.Wall;
import code.prop.Enemy;
import code.prop.Mushroom;

public class Tile {
    public Wall wall;
    public Enemy enemy;
    public Mushroom mushroom;

    public static Tile generate (int level){
        Tile tile = new Tile();
        if (Math.random() < 0.7){
            tile.wall = Wall.generate(level);
            return tile;
        }
        if (Math.random() < 0.1){
            tile.enemy = Enemy.generate(level);
        }
        return tile;
    }

    public void paint (Graphics context, int x, int y, float zoom){
        if (wall != null){
            wall.paint(context, x, y, zoom); return;
        }
        if (enemy != null){
            enemy.paint(context, x, y, zoom); return;
        }
        if (mushroom != null){
            mushroom.paint(context, x, y, zoom); return;
        }
    }
}
