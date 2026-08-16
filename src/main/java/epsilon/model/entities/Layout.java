package epsilon.model.entities;

import java.awt.Graphics2D;
import java.util.ArrayList;

import epsilon.model.entities.interfaces.IEntity;
import epsilon.model.entities.interfaces.ILayout;

public class Layout implements ILayout{
    private final ArrayList<IEntity> objects;
    private String name;
    public Layout(String name){
        this.name = name;
        objects = new ArrayList<>();
    }
    public ArrayList<IEntity> getObjects(){
        return objects;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    @Override
    public void add(IEntity entity){
        objects.add(entity);
    }
    @Override
    public IEntity remove(int index){
        IEntity backUp = objects.get(index);
        objects.remove(index);
        return backUp;
    }
    public void renderObjects(Graphics2D g2d){
        for (IEntity entity : objects) {
            entity.fill(g2d);
        }
    }
}