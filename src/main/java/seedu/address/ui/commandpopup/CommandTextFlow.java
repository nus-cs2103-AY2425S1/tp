package seedu.address.ui.commandpopup;

import javafx.scene.text.TextFlow;

import java.awt.*;

public class CommandTextFlow extends TextFlow {
    private String command;

    public CommandTextFlow(String command) {
        this.command = command;
    }
}
