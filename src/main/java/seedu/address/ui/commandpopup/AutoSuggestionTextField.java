package seedu.address.ui.commandpopup;


import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import seedu.address.ui.CommandBox;


/**
 * This class is a TextField which implements an "autocomplete" functionality, based on a supplied list of entries.
 *
 * @author Matthew Lee
 */

public class AutoSuggestionTextField extends TextField {

    private final SortedSet<String> entries;
    //popup GUI
    private ContextMenu entriesPopup;

    private CommandBox commandBox;


    public AutoSuggestionTextField() {
        super();
        this.entries = PopUpCommandsSet.commands();
        this.entriesPopup = new ContextMenuSkin();
        setListner();
    }

    public void setCommandBox(CommandBox commandBox) {
        this.commandBox = commandBox;
    }

    /**
     * "Suggestion" specific listners
     */
    private void setListner() {
        //Add "suggestions" by changing text
        this.textProperty().addListener((observable, oldValue, newValue) -> {
            String enteredText = this.getText();
            //hide suggestion if nothing has been entered
            if (enteredText == null || enteredText.isEmpty()) {
                entriesPopup.hide();
            } else {
                //filter all possible suggestions depends on "Text", case insensitive
                List<String> filteredEntries = popUpFilter(entries, enteredText);
                if (!filteredEntries.isEmpty()) {
                    //build popup - list of "CustomMenuItem"
                    populatePopup(filteredEntries, enteredText);
                    if (!entriesPopup.isShowing()) {
                        entriesPopup.show(this, Side.BOTTOM, 0, 0); //position of popup
                    }
                    //no suggestions -> hide
                } else {
                    entriesPopup.hide();
                }
            }
        });

        // Modified listener for handling Enter key when popup is open
        this.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                commandBox.handleCommandEntered();
                // Optionally hide the popup only after a command has been processed
                entriesPopup.hide();
                event.consume(); // Prevent JavaFX default hiding behavior
            }
        });

        //Hide always by focus-in (optional) and out
        this.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            entriesPopup.hide();
        });
    }

    public List<String> popUpFilter(SortedSet<String> set, String filter) {
        return set.stream()
                .filter(e -> e.toLowerCase().startsWith(filter.toLowerCase()))
                .collect(Collectors.toList());
    }


    /*
     * Populate the entry set with the given search results. Display is limited to 10 entries, for performance.
     *
     * @param filteredList The set of matching strings.
     */
    public void populatePopup(List<String> filteredList, String searchRequest) {
        //List of "suggestions"
        List<CustomMenuItem> menuItems = new LinkedList<>();
        for (int i = 0; i < filteredList.size(); i++) {
            final String result = filteredList.get(i);

            //label with graphic (TextFlow) to highlight matching prefix
            Label entryLabel = new Label();
            entryLabel.setGraphic(buildTextFlow(result, searchRequest));
            entryLabel.setPrefHeight(10);
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            menuItems.add(item);

            //if a suggestion is selected, put it into the textfield and run the command if it is a no additional
            //field command
            item.setOnAction(actionEvent -> {
                this.setText(result);
                this.positionCaret(result.length());
                entriesPopup.hide();
                if (result.equals("save") || result.equals("load") || result.equals("help")
                        || result.equals("clear") || result.equals("list")) {
                    commandBox.handleCommandEntered();
                }
            });
        }

        //"Refresh" context menu
        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    public void handleCommandEntered() {
        commandBox.handleCommandEntered();
    }

    /**
     * Build TextFlow with selected text. Return "case" dependent.
     *
     * @param text   - string with text
     * @param filter - string to select in text
     * @return - TextFlow
     */
    private TextFlow buildTextFlow(String text, String filter) {
        int filterIndex = text.toLowerCase().indexOf(filter.toLowerCase());
        Text textBefore = new Text(text.substring(0, filterIndex));
        Text textAfter = new Text(text.substring(filterIndex + filter.length()));
        Text textFilter = new Text(text.substring(filterIndex, filterIndex + filter.length()));
        textFilter.setFill(Color.ORANGE);
        return new TextFlow(textBefore, textFilter, textAfter);
    }

    /**
     * Get the existing set of autocomplete entries.
     *
     * @return The existing autocomplete entries.
     */


    public SortedSet<String> getEntries() {
        return entries;
    }
}
