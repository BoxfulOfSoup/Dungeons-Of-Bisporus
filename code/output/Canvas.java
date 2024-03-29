package code.output;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import code.map.Grid;

public class Canvas extends JPanel{

    Grid board = Grid.generate(1);
    float zoom = 2f;
    int width = 256;
    int height = 280;
    int scene = 0;

    Image titleScreen = new Image("/sprite/title.png");
    Image deathScreen = new Image("/sprite/death.png");

    Canvas(float newZoom, int x, int y){
        zoom = newZoom;
        width = x;
        height = y;
        this.setPreferredSize(new Dimension((int)(zoom*width), (int)(zoom*height)));
    }

    public void paint (Graphics g){
        switch (scene){
            case 0://Title
                g.drawImage(titleScreen.source, 0, 0, (int)(256*zoom), (int)(280*zoom), null);
                break;
            case 1://In Game
                board.paint(g, zoom);
                break;
            case 2://Title
                g.drawImage(deathScreen.source, 0, 0, (int)(256*zoom), (int)(280*zoom), null);
                GUI.numberPaint(g, board.player.level, 128, 176, zoom);
                break;
        }
    }
}
