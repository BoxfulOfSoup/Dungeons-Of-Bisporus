package code.prop;

import java.awt.Graphics;

import code.element.Element;
import code.output.Image;

public class Enemy extends Prop {
    Image img = new Image("/sprite/entity_master.png");
    public int level = 1;
    public int hp = 5*level;
    public boolean moved = false;

    public static Enemy generate (int superLevel){
        Enemy enemy = new Enemy();
        enemy.element = Element.generate(superLevel);
        while (enemy.level < superLevel && Math.random() < 0.7){
            enemy.level += 1;
        }
        return enemy;
    }

    public void paint (Graphics context, int x, int y, float zoom){
        img.drawElemental(context, element.toArray(), (level-1)*16, x*16, (y*16)+24, zoom);
    }
}
