package epsilon.controller;

import epsilon.controller.interfaces.ActionMenu;
import epsilon.controller.interfaces.Controller;
import epsilon.controller.interfaces.IMenu;
import epsilon.model.dataStructure.linearStructure.statik.Array;

public abstract class GameMenu implements IMenu, Controller{
    public IMenu prevMenu;
    public byte optionNumber;
    public Array options;
    public boolean isEnabled;
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public GameMenu(Array options){
        for (int i = 0; i < options.size(); i++) {
            if(options.get(i) instanceof ActionMenu == false){
                throw new UnsupportedOperationException("Invalid object type");
            }
        }
        this.options = options;
    }
    @Override
    public void selectOption(){
        ActionMenu actionEvent = (ActionMenu)options.get(optionNumber);
        actionEvent.run();
    }
    @Override
    public void init(){
        isEnabled = true;
    }
    public boolean isEnabled(){
        return isEnabled;
    }
    public void setActive(boolean isEnabled){
        this.isEnabled = isEnabled;
    }
    @Override
    public void changeOption(byte optionNumber) {
        if(isEnabled){
            if(optionNumber < 0){
                this.optionNumber = (byte)(options.size()-1);
            }
            else if(optionNumber >= options.size()){
                this.optionNumber = 0;
            }
            else{
                this.optionNumber = optionNumber;
            }
        }
    }
    @Override
    public void changeMenu(IMenu nextMenu) {
        nextMenu.init();
        /*try {
            Thread.sleep(20);
        } catch (InterruptedException ex) {
        }*/
        isEnabled = false;
    }

    @Override
    public void goBack() {
        prevMenu.init();
        isEnabled = false;
    }
    public void setPrevMenu(IMenu prevMenu){
        this.prevMenu = prevMenu;
    }
}