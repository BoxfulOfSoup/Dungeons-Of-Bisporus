package code.prop;

import java.awt.Graphics;
import java.util.Random;

import code.element.Element;
import code.output.Image;

public class Wall extends Prop {
    public int time = new Random().nextInt(5);
    Image img = new Image("/sprite/map_master.png");

    public static Wall generate (int level){
        Wall wall = new Wall();
        if (Math.random() < level/10f){
            wall.element = Element.generate(level);
        }
        return wall;
    }

    public int[][] pathfind (int x, int y){
        int[][] value = new int[16][16];
        return value;
    }

    public void paint (Graphics context, int x, int y, float zoom){
        img.drawElemental(context, element.toArray(), 0, x*16, (y*16)+24, zoom);
        if (!element.neutral() && time == 8){
            trapPaint(context, x, y, zoom);
        }
    }

    public void trapPaint (Graphics context, int x, int y, float zoom){
        for (int oX = -1; oX < 2; oX += 1){
            for (int oY = -1; oY < 2; oY += 1){
                img.drawElemental(context, element.toArray(), 16, (x+oX)*16, ((y+oY)*16)+24, zoom);
            }
        }
    }
}
