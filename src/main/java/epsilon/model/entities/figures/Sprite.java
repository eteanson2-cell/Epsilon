package epsilon.model.entities.figures;

import java.awt.Color;
import java.awt.Graphics2D;

import epsilon.model.dataStructure.linearStructure.dynamic.LinkedList;
import epsilon.model.entities.interfaces.IEntity;
import static epsilon.utils.FunctionUtils.showMessage;

public class Sprite implements IEntity{
    private LinkedList figures;
    public Sprite(){
        figures = new LinkedList();
    }
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Sprite(LinkedList figures){
        for (int i = 0; i < figures.size(); i++) {
            Object obj = figures.get(i);
            if(obj instanceof Figure == false){
                System.exit(1);
            }
        }
        setFigures(figures);
    }
    //getters
    public LinkedList getFigures(){
        return figures;
    }
    public Figure getFigure(int index){
        return (Figure)figures.get(index);
    }
    public int getSize(){
        return figures.size();
    }
    @Override
    public Point getCenter(){
        return new Point();
    }
    //setters
    public void setFigures(LinkedList figures){
        for (int index = 0; index < figures.size(); index++) {
            Object object = figures.get(index);
            if(object instanceof Figure == false){
                return;
            }
        }
        this.figures = figures;
    }
    public void setFigure(Figure figure, int index){
        figures.modify(figure,index);
    }
    //actions
    public void addFigure(Figure figure){
        figures.add(figure);
    }
    public void removeFigure(int index){
        figures.remove(index);
    }
    public void removeFigure(){
        figures.remove();
    }
    public boolean removeFigure(Figure figure){
        for (int i = 0; i < figures.size() ;i++) {
            Figure pol = (Figure)figures.get(i);
            if(pol == figure){
                figures.remove(i);
                return true;
            }
        }
        showMessage("The polygon was not found");
        return false;
    }
    public Color verifyFigureColor(Figure figure, Color color){
        if(figure.getInsideColor() == null){
            return color;
        }
        else{
            return figure.getInsideColor();
        } 
    }
    @Override
    public void draw(Graphics2D g2d){
        for (int i = 0; i < figures.size() ;i++) {
            Figure figure = (Figure)figures.get(i);
            figure.draw(g2d);
        }
    }
    @Override
    public void fill(Graphics2D g2d){
        for (int i = 0; i < figures.size() ;i++) {
            Figure figure = (Figure)figures.get(i);
            figure.fill(g2d);
        }
    }
    @Override
    public boolean intersects(IEntity entity) {
        for (int i = 0; i < figures.size() ;i++) {
            Figure figure = (Figure)figures.get(i);
            if(figure.intersects(entity)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void move(double x, double y) {
        for (int i = 0; i < figures.size() ;i++) {
            Figure figure = (Figure)figures.get(i);
            figure.move(x, y);
        }
    }
} 