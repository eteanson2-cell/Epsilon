package epsilon.controller.interfaces;

public interface IMenu{
    void init();
    void selectOption();
    void changeOption(byte optionNumber);
    void changeMenu(IMenu nextMenu);
    void goBack();
    boolean showWarning();
}