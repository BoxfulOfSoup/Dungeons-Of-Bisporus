package code.map;

import java.awt.Graphics;

import code.element.Element;
import code.prop.Enemy;
import code.prop.Player;
import code.prop.Wall;

public class Grid {
    public Tile[][] value = new Tile[16][16];
    public Player player = new Player();

    public Grid(){
        for (int i = 0; i < 16; i += 1){
            for (int j = 0; j < 16; j += 1){
                value[i][j] = new Tile();
            }
        }
    }

    public void paint (Graphics context, float zoom){
        for (int i = 0; i < 16; i += 1){
            for (int j = 0; j < 16; j += 1){
                if  (i == player.x && j == player.y){
                    player.paint(context, i, j, zoom);
                    continue;
                }
                value[i][j].paint(context, i, j, zoom);
            }
        }
    }

    public static Grid generate(int level){
        Grid grid = new Grid();
        for (int i = 1; i < 8; i += 1){
            if (Math.random() < 0.6){
                continue;
            }
            int defaultCord = 15-i;
            for (int x = i; x <= defaultCord; x += 1){
                if (Math.random() < 0.5){grid.value[x][i] = Tile.generate(level);}
                if (Math.random() < 0.5){grid.value[x][defaultCord] = Tile.generate(level);}
            }
            for (int y = i; y <= defaultCord; y += 1){
                if (Math.random() < 0.5){grid.value[i][y] = Tile.generate(level);}
                if (Math.random() < 0.5){grid.value[defaultCord][y] = Tile.generate(level);}
            }
            i += 1;
        }
        return grid;
    }

    public void enemyAi(){
        NavMesh nav = navigate(player.x, player.y);
        for (Tile[] i : value){
            for (Tile j : i){
                if (j.enemy != null){
                    j.enemy.moved = false;
                }
            }
        }
        for (int x = 0; x < 16; x += 1){
            for (int y = 0; y < 16; y += 1){
                Enemy enemy = value[x][y].enemy;
                if (enemy != null){
                    if (enemy.hp <= 0){
                        enemy = null;
                        player.seeds += 1;
                        continue;
                    }
                    if (enemy.moved){
                        continue;
                    }
                    enemy.moved = true;
                    int currentValue = nav.value[x][y];
                    if (currentValue == 0){
                        continue;
                    }
                    if (currentValue <= 2){
                        int damage = enemy.level*Element.attackPower(enemy.element.toArray(), player.element.toArray());
                        player.hp -= (damage > 0 ? damage : 1);
                        continue;
                    }
                    if (x != 0){
                        int newVal = nav.value[x-1][y];
                        if (newVal != -1 && newVal < currentValue && value[x-1][y].enemy == null){
                            value[x-1][y].enemy = enemy;
                            value[x][y].enemy = null;
                            continue;
                        }
                    }
                    if (x != 15){
                        int newVal = nav.value[x+1][y];
                        if (newVal != -1 && newVal < currentValue && value[x+1][y].enemy == null){
                            value[x+1][y].enemy = enemy;
                            value[x][y].enemy = null;
                            continue;
                        }
                    }
                    if (y != 0){
                        int newVal = nav.value[x][y-1];
                        if (newVal != -1 && newVal < currentValue && value[x][y-1].enemy == null){
                            value[x][y-1].enemy = enemy;
                            value[x][y].enemy = null;
                            continue;
                        }
                    }
                    if (y != 15){
                        int newVal = nav.value[x][y+1];
                        if (newVal != -1 && newVal < currentValue && value[x][y+1].enemy == null){
                            value[x][y+1].enemy = enemy;
                            value[x][y].enemy = null;
                            continue;
                        }
                    }
                }
            }
        }
    }

    public void trap(){
        for (int x = 0; x < 16; x += 1){
            for (int y = 0; y < 16; y += 1){
                Wall trap = value[x][y].wall;
                if (trap == null || trap.element.neutral()){
                    continue;
                }
                if (trap.time >= 8){
                    trap.time -= 8;
                }
                trap.time += 1;
                if (trap.time != 8){
                    continue;
                }
                //Offset
                for (int oX = -1; oX < 2; oX += 1){
                    if (x == 0 && oX == -1){
                        continue;
                    }
                    if (x == 15 && oX == 1){
                        continue;
                    }
                    for (int oY = -1; oY < 2; oY += 1){
                        if (y == 0 && oY == -1){
                            continue;
                        }
                        if (y == 15 && oY == 1){
                            continue;
                        }
                        int destX = x+oX;
                        int destY = y+oY;
                        Tile dest = value[destX][destY];
                        if (player.x == destX && player.y == destY){
                            int damage = Element.attackPower(trap.element.toArray(), player.element.toArray());
                            player.hp -= (damage > 0 ? damage : 1);
                        }
                        if (dest.enemy != null){
                            int damage = Element.attackPower(trap.element.toArray(), dest.enemy.element.toArray());
                            damage = (damage/2)+1;
                            dest.enemy.hp -= (damage > 0 ? damage : 1);
                        }
                        if (dest.mushroom != null){
                            dest.mushroom.apply(trap.element);
                        }
                    }
                }
            }
        }
    }

    public NavMesh navigate(int x, int y){
        return NavMesh.generateFromGrid(x, y, this);
    }

    public void grow(){
        for (Tile[] i : value){
            for (Tile j : i){
                if (j.mushroom != null){
                    j.mushroom.grow();
                }
            }
        }
    }
}
