package code.element;

public enum Element {
    FIRE,
    METAL,
    PLANT,
    EARTH,
    WATER;

    static int versus (Element attacker, Element victim){
        // -1 = Victim controls Attacker
        // 0 = Neutral
        // 1 = Victim generates Attacker
        switch (attacker) {
            case FIRE:
                if (victim == WATER) {return -1;}
                if (victim == PLANT) {return 1;}
                break;
            case METAL:
                if (victim == FIRE) {return -1;}
                if (victim == EARTH) {return 1;}
                break;
            case PLANT:
                if (victim == METAL) {return -1;}
                if (victim == WATER) {return 1;}
                break;
            case EARTH:
                if (victim == PLANT) {return -1;}
                if (victim == FIRE) {return 1;}
                break;
            case WATER:
                if (victim == EARTH) {return -1;}
                if (victim == METAL) {return 1;}
                break;
        }
        return 0;
    }

    public static int attackPower (Element[] attacker, Element[] victim){
        int power = 1;
        for (Element i : attacker){
            for (Element j : victim){
                power += versus(i, j);
            }
        }
        return power;
    }

    public static int xOffset (Element element){
        switch (element) {
            case FIRE:
                return 1;
            case METAL:
                return 2;
            case PLANT:
                return 3;
            case EARTH:
                return 4;
            case WATER:
                return 5;
            default:
                return 0;
        }
    }

    public static Elemental generate (int level){
        Elemental result = new Elemental();
        result.set(Element.random(), 1);
        while (Math.random() < level/10f){
            Element next = Element.random();
            if (!result.has(next)){
                result.set(next, 1);
            }
        }
        return result;
    }

    public static Element random (){
        Element[] choices = Element.values();
        return choices[(int)(Math.floor(Math.random()*choices.length))];
    }
}
