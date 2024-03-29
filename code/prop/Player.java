package code.prop;

import java.awt.Graphics;

import code.element.Element;
import code.output.GUI;
import code.output.Image;

public class Player extends Prop {
    Image img = new Image("/sprite/player_master.png");
    public int hp = 100;
    public int x = 0;
    public int y = 0;
    public int seeds = 3;
    public int level = 1;

    public void paint (Graphics context, int x, int y, float zoom){
        GUI.playerGui(context, zoom, this);
        img.drawElemental(context, element.toArray(), 0, x*16, (y*16)+24, zoom);
    }

    public void update (){
        element.decay();
        //Death
    }

    public void eat (Mushroom food){
        Element[] effects = food.element.toArray();
        for (Element i : effects){
            element.set(i,60);
        }
        hp += 10;
        if (hp > 100){
            hp = 100;
        }
    }
}
