package code.prop;

import java.awt.Graphics;

import code.element.Element;
import code.element.Elemental;
import code.output.Image;

public class Mushroom extends Prop {
    public int growth = -1;
    Image img = new Image("/sprite/mushroom_master.png");
    Image seedImg = new Image("/sprite/pot_master.png");

    public static Mushroom generate (int superLevel){
        Mushroom mushroom = new Mushroom();
        mushroom.element = Element.generate(superLevel);
        return mushroom;
    }

    public void paint (Graphics context, int x, int y, float zoom){
        if (growth > 2){
            img.drawElemental(context, element.toArray(), 0, x*16, (y*16)+24, zoom);
        }else{
            seedImg.drawPot(context, x*16, (y*16)+24, zoom, this);
        }
    }

    public void grow (){
        if (growth < 3){
            growth += 1;
        }
    }

    public void apply (Elemental addition){
        for (Element i : Element.values()){
            element.set(i, addition.get(i));
        }
    }
}
