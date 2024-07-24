package gui;

/**
 * Base class for all GUI elements containing a reference to the GUI the Element belongs to
 */
public class GuiElement {

    private final Gui gui;

    public GuiElement(Gui gui) {
        this.gui = gui;
    }

    public Gui getGui(){
        return gui;
    }
}
