package epsilon.model.entities;

import java.util.ArrayList;

public abstract class Stage{
    private final ArrayList<Layout> layers;
    private String name;
    public Stage(String name){
        this.name = name;
        layers = new ArrayList<>();
    }
    public ArrayList<Layout> getLayers(){
        return layers;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
}