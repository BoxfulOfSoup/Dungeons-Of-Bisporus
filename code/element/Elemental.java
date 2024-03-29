package code.element;

import java.util.HashMap;

public class Elemental {
    HashMap <Element, Integer> value = new HashMap<Element, Integer>();
    public Elemental(){
        for (Element i : Element.values()){
            value.put(i, 0);
        }
    }

    public void set(Element elem, int newVal){
        if (newVal > get(elem)){
            value.put(elem, newVal);
        }
    }

    public boolean has(Element elem){
        return get(elem) > 0;
    }

    public Element[] toArray(){
        int count = 0;
        for (Integer i : value.values()){
            if (i > 0){
                count += 1;
            }
        }
        Element[] array = new Element[count];
        int pos = 0;
        for (Element i : Element.values()){
            if (has(i)){
                array[pos] = i;
                pos += 1;
            }
        }
        return array;
    }

    public void decay (){
        for (Element i : Element.values()){
            if (get(i) > 0){
                value.put(i, value.get(i)-1);
            }
        }
    }

    public boolean neutral (){
        for (Element i : Element.values()){
            if (get(i) > 0){
                return false;
            }
        }
        return true;
    }

    public int get (Element elem){
        return value.get(elem);
    }
}
