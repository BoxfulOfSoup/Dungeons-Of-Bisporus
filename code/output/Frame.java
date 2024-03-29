package code.output;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import code.element.Element;
import code.map.Grid;
import code.map.Tile;
import code.prop.Mushroom;
import code.prop.Player;

public class Frame extends JFrame implements KeyListener {

    float zoom = 2f;
    int width = 256;
    int height = 280;

    int scene = 0;

    Canvas content;

    public Frame () {
        content = new Canvas(zoom, width, height);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(content);
        this.pack();

        this.addKeyListener(this);

        this.setLocationRelativeTo(null);
        this.setBackground(Color.BLACK);
        this.setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        switch(scene){
            case 0://Title
                if (e.getKeyChar() == 's'){
                    content.board = Grid.generate(1);
                    scene = 1;
                }
                break;
            case 1://In Game
                int playerX = content.board.player.x;
                int playerY = content.board.player.y;
                switch (e.getKeyChar()) {
                    case 'q':
                    case 'a':
                    case 'z':
                        playerX -= 1;
                        break;
                    case 'e':
                    case 'd':
                    case 'c':
                        playerX += 1;
                }
                switch (e.getKeyChar()) {
                    case 'q':
                    case 'w':
                    case 'e':
                        playerY -= 1;
                        break;
                    case 'z':
                    case 'x':
                    case 'c':
                        playerY += 1;
                }
                if (playerX < 0 || playerX > 15 || playerY < 0 || playerY > 15){
                    //Out of bounds, attempt to exit grid
                    boolean existsEnemy = false;
                    outerloop:
                    for (Tile[] i : content.board.value){
                        for (Tile j : i){
                            if (j.enemy != null){
                                existsEnemy = true;
                                break outerloop;
                            }
                        }
                    }
                    if (!existsEnemy){
                        content.board.player.level += 1;
                        content.board.player.x = 0;
                        content.board.player.y = 0;
                        Player savedPlayer = content.board.player;
                        content.board = Grid.generate(content.board.player.level);
                        content.board.player = savedPlayer;
                    }
                }else{
                    Tile destination = content.board.value[playerX][playerY];
                    boolean collision = false;
                    if (destination.wall != null){
                        //Break Wall
                        destination.wall = null;
                        collision = true;
                    }
                    if (destination.enemy != null){
                        //Attack Enemy
                        int damage = Element.attackPower(content.board.player.element.toArray(), destination.enemy.element.toArray());
                        destination.enemy.hp -= (damage > 0 ? damage : 1);
                        if (destination.enemy.hp <= 0){
                            destination.enemy = null;
                            content.board.player.seeds += 1;
                        }
                        collision = true;
                    }
                    if (destination.mushroom != null){
                        //Eat Mushroom
                        content.board.player.eat(destination.mushroom);
                        destination.mushroom = null;
                    }else{
                        //Plant Mushroom
                        if (e.getKeyChar() == 'r' && content.board.player.seeds > 0){
                            content.board.player.seeds -= 1;
                            destination.mushroom = new Mushroom();
                        }
                    }
                    if (collision == false){
                        //Move
                        content.board.player.x = playerX;
                        content.board.player.y = playerY;
                    }
                    content.board.enemyAi();
                    content.board.player.update();
                    content.board.trap();
                }
                content.board.grow();
                if (content.board.player.hp <= 0){
                    scene = 2;
                }
                break;
            case 2://Death
                if (e.getKeyChar() == 's'){
                    scene = 0;
                }
                break;
        }
        content.scene = scene;
        paintComponents(getGraphics());
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}