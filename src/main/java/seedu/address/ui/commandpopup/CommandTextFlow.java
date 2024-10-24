package seedu.address.ui.commandpopup;

import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class CommandTextFlow extends TextFlow {
    private String command;

    public CommandTextFlow(String command, String filter) {
        int size = 18;
        this.command = command;
        int filterIndex = command.toLowerCase().indexOf(filter.toLowerCase());
        Text textBefore = new Text(command.substring(0, filterIndex));
        textBefore.setFont(Font.font("Comfortaa", FontWeight.BOLD, size));
        Text textAfter = new Text(command.substring(filterIndex + filter.length()));
        textAfter.setFont(Font.font("Comfortaa", FontWeight.BOLD, size));
        Text textFilter = new Text(command.substring(filterIndex, filterIndex + filter.length()));
        textFilter.setFont(Font.font("Comfortaa", FontWeight.BOLD, size));
        textFilter.setFill(Color.ORANGE);
        TextFlow result = new TextFlow(textBefore, textFilter, textAfter);
        result.setPadding(new Insets(2, 10, 2, 10));
    }

    public String getCommand() {
        return this.command;
    }
}
