package epsilon.controller.gameState.game2.gameAssets;

import epsilon.model.entities.figures.auxiliar.Pixel;

public class Block{
    protected BlockType blockType;
    protected Pixel color;
    public Block (BlockType blockType){
        this.blockType = blockType;
    }
    public BlockType getType(){
        return blockType;
    }
    public void setType(BlockType blockType){
        this.blockType = blockType;
    }
    public Pixel getColor(){
        return color;
    }
    public void setColor(Pixel color){
        this.color = color;
    }
    @Override
    public String toString(){
        return blockType+"";
    }
}