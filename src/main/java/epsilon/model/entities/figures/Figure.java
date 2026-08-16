package epsilon.model.entities.figures;

import java.awt.Color;

import epsilon.model.entities.interfaces.IEntity;

public abstract class Figure implements IEntity{
    protected Color borderColor;
    protected Color insideColor;
    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public Color getInsideColor() {
        return insideColor;
    }

    public void setInsideColor(Color insideColor) {
        this.insideColor = insideColor;
    }
}