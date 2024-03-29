package code.output;

import java.awt.Color;
import java.awt.Graphics;

import code.element.Element;
import code.prop.Player;

public class GUI {
    public static void playerGui (Graphics context, float zoom, Player player){
        Image master = new Image("/sprite/gui_master.png");

        context.setColor(Color.red);
        context.fillRect(0, 0, (int)(256*zoom), (int)(8*zoom));
        context.setColor(Color.green);
        context.fillRect(0, 0, (int)((((float)player.hp)/100)*256f*zoom), (int)(8*zoom));

        context.drawImage(master.source, 0, (int)(8*zoom), (int)(8*zoom), (int)(16*zoom), 0, 0, 8, 8, null);
        numberPaint(context, player.seeds, 8, 8, zoom);
        context.drawImage(master.source, (int)(128*zoom), (int)(8*zoom), (int)(136*zoom), (int)(16*zoom), 8, 0, 16, 8, null);
        numberPaint(context, player.level, 136, 8, zoom);

        Element[] elem = player.element.toArray();

        for (int i = 0; i < elem.length; i += 1){
            int xOff = Element.xOffset(elem[i]);
            context.drawImage(master.source, (int)(i*8*zoom), (int)(16*zoom), (int)((i+1)*8*zoom), (int)(24*zoom), 8+(xOff*8), 0, 16+(xOff*8), 8, null);
        }
    }
    public static void numberPaint (Graphics context, int number, int x, int y, float zoom){
        Image master = new Image("/sprite/gui_master.png");
        String stringNum = String.valueOf(number);
        for (int i = 0; i < stringNum.length(); i += 1){
            int xOfNum = Character.getNumericValue(stringNum.charAt(i));
            context.drawImage(master.source, (int)(((i*8)+x)*zoom), (int)(y*zoom), (int)(((i*8)+x+8)*zoom), (int)((y+8)*zoom), xOfNum*8, 8, (xOfNum+1)*8, 16, null);
        }
    }
}
