package code.map;

public class NavMesh {
    public int[][] value = new int[16][16];

    public static NavMesh generateFromGrid (int destX, int destY, Grid gridInfo){
        NavMesh returned = new NavMesh();
        returned.iterateTile(1, destX, destY, gridInfo);
        return returned;
    }
    void iterateTile (int num, int x, int y, Grid gridInfo){
        if (num >= value[x][y] && value[x][y] != 0){
            return;
        }
        if (gridInfo.value[x][y].wall != null){
            value[x][y] = -1;
            return;
        }
        value[x][y] = num;
        if (x != 0){  iterateTile(num+1, x-1, y, gridInfo); }
        if (y != 0){  iterateTile(num+1, x, y-1, gridInfo); }
        if (x != 15){ iterateTile(num+1, x+1, y, gridInfo); }
        if (y != 15){ iterateTile(num+1, x, y+1, gridInfo); }
    }

    @Override
    public String toString (){
        String map = "";
        for (int[] i : value){
            for (int j : i){
                map += j;
                if (j >= 0 && j < 10){ map += " ";}
                map += " ";
            }
            map += "\n";
        }
        return map;
    }
}
