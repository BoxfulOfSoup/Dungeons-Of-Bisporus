package code.output;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import code.element.Element;
import code.prop.Mushroom;

public class Image {
    char alt = '?';

    BufferedImage source;

    public Image(String sourceTL){
        try {
            source = ImageIO.read(getClass().getResourceAsStream(sourceTL));
        }catch(IOException error){
            error.printStackTrace();
        }
    }

    public void drawElemental(Graphics context, Element[] element, int yOffset, int x, int y, float zoom){
        if (element.length <= 0){
            context.drawImage(source, (int)(x*zoom), (int)(y*zoom), (int)((x+16)*zoom),
            (int)((y+16)*zoom), 0, yOffset, 16, 16+yOffset, null);
            return;
        }
        for (int i = 0; i < 4; i += 1){
            int sourceXOffset = Element.xOffset(element[i % element.length]);

            int destX = i < 2 ? 0 : 8;
            int destY = i % 2 == 0 ? 0 : 8;
            context.drawImage(source, (int)((x+destX)*zoom), (int)((y+destY)*zoom), (int)((x+destX+8)*zoom), (int)((y+destY+8)*zoom), 0+(sourceXOffset*16)+destX, yOffset+destY, 8+(sourceXOffset*16)+destX, 8+yOffset+destY, null);
        }
    }

    public void drawPot(Graphics context, int x, int y, float zoom, Mushroom info){
        context.drawImage(source, (int)(x*zoom), (int)(y*zoom), (int)((x+16)*zoom), (int)((y+8)*zoom), 0, info.growth*8, 16, 8+(info.growth*8), null);
        context.drawImage(source, (int)(x*zoom), (int)((y+8)*zoom), (int)((x+16)*zoom), (int)((y+16)*zoom), 0, 24, 16, 32, null);
    }
}
