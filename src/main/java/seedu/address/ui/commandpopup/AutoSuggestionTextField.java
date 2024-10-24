package seedu.address.ui.commandpopup;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PopupControl;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import seedu.address.ui.CommandBox;


/**
 * This class is a TextField which implements an "autocomplete" functionality, based on a supplied list of commandSet.
 *
 * @author Matthew Lee
 */

public class AutoSuggestionTextField extends TextField {

    private final SortedSet<String> commandSet;
    //popup GUI
    private PopupControl suggestionPopup;
    private ListView<TextFlow> suggestionList;
    private CommandBox commandBox;

    private ObservableList<TextFlow> textFlowItems;


    public AutoSuggestionTextField() {
        super();
        this.commandSet = PopUpCommandsSet.commands();

        suggestionPopup = new PopupControl();
        suggestionList = new ListView<>();
        suggestionList.setFocusTraversable(true);
        // Set up the popup
        suggestionPopup.getScene().setRoot(suggestionList);
        suggestionPopup.setAutoHide(true); // Auto hide when clicking outside

        // Listen for text changes
        textProperty().addListener((obs, oldText, newText) -> {
            if (newText != null && !newText.isEmpty()) {
                showSuggestions(newText);
            } else {
                suggestionPopup.hide();
            }
        });

    }

    private void showSuggestions(String input) {
        textFlowItems = FXCollections.observableArrayList(
                // Filter suggestions based on the input
                popUpFilter(commandSet, input).stream()
                        .map(s -> buildTextFlow(s, input))
                        .toList());
        suggestionList.setItems(textFlowItems);
        suggestionList.setPrefHeight(calculateListViewHeight(textFlowItems));

        if (!suggestionList.getItems().isEmpty()) {
            // Calculate the position of the popup
            Point2D location = localToScreen(0, getHeight());
            suggestionPopup.show(this, location.getX(), location.getY());
        } else {
            suggestionPopup.hide();
        }

        // Key event handling for suggestions
        suggestionList.setOnKeyPressed(event -> {
            if (event.isControlDown()) {
                switch (event.getCode()) {
                case DOWN:
                    // Cycle down through suggestions
                    int currentIndexDown = suggestionList.getSelectionModel().getSelectedIndex();
                    int nextIndexDown = (currentIndexDown + 1) % suggestionList.getItems().size();
                    suggestionList.getSelectionModel().select(nextIndexDown);
                    suggestionList.scrollTo(nextIndexDown);
                    event.consume(); // Prevent further handling of the DOWN key
                    break;

                case UP:
                    // Cycle up through suggestions
                    int currentIndexUp = suggestionList.getSelectionModel().getSelectedIndex();
                    int previousIndexUp = (currentIndexUp - 1 + suggestionList.getItems().size())
                            % suggestionList.getItems().size();
                    suggestionList.getSelectionModel().select(previousIndexUp);
                    suggestionList.scrollTo(previousIndexUp);
                    event.consume(); // Prevent further handling of the UP key
                    break;

                case ENTER:
                    // Insert selected suggestion into the text field
                    String selectedItem = suggestionList.getSelectionModel().getSelectedItem().getAccessibleText();
                    if (selectedItem != null) {
                        setText(selectedItem); // Insert the selected suggestion
                        suggestionPopup.hide(); // Hide the popup after selection
                    }
                    event.consume(); // Prevent further handling of the ENTER key
                    break;

                default:
                    break;
                }
            }
        });

    }

    public void hidePopup() {
        this.suggestionPopup.hide();
    }

    public void setCommandBox(CommandBox commandBox) {
        this.commandBox = commandBox;
    }
    /**
     * Method to calculate total height based on the number of items and their heights
     */
    private double calculateListViewHeight(ObservableList<TextFlow> items) {
        double height = 0;
        for (TextFlow item : items) {
            height += 25;
        }
        return height;
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
                suggestionPopup.hide();
            } else {
                //filter all possible suggestions depends on "Text", case insensitive
                List<String> filteredEntries = popUpFilter(commandSet, enteredText);
                if (!filteredEntries.isEmpty()) {
                    //build popup - list of "CustomMenuItem"
                    populatePopup(filteredEntries, enteredText);
                    //no suggestions -> hide
                } else {
                    suggestionPopup.hide();
                }
            }
        });

        // Modified listener for handling Enter key when popup is open
        this.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                commandBox.handleCommandEntered();
                // Optionally hide the popup only after a command has been processed
                suggestionPopup.hide();
                event.consume(); // Prevent JavaFX default hiding behavior
            }
        });

        this.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            suggestionPopup.hide();
        });
    }

    public List<String> popUpFilter(SortedSet<String> set, String filter) {
        return set.stream()
                .filter(e -> e.toLowerCase().startsWith(filter.toLowerCase()))
                .collect(Collectors.toList());
    }


    /*
     * Populate the entry set with the given search results. Display is limited to 10 commandSet, for performance.
     *
     * @param filteredList The set of matching strings.
     */
    public void populatePopup(List<String> filteredList, String searchRequest) {
        //List of "suggestions"
        List<TextFlow> menuItems = new LinkedList<>();
        for (int i = 0; i < filteredList.size(); i++) {
            final String result = filteredList.get(i);

            //label with graphic (TextFlow) to highlight matching prefix
            Label entryLabel = new Label();
            entryLabel.setGraphic(buildTextFlow(result, searchRequest));
            entryLabel.setPrefHeight(10);

            //if a suggestion is selected, put it into the textfield and run the command if it is a no additional
            //field command
            suggestionList.setOnMouseClicked(actionEvent -> {
                this.setText(result);
                this.positionCaret(result.length());
                suggestionPopup.hide();
                if (result.equals("save") || result.equals("load") || result.equals("help")
                        || result.equals("clear") || result.equals("list")) {
                    commandBox.handleCommandEntered();
                }
            });
        }
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
        int size = 18;
        int filterIndex = text.toLowerCase().indexOf(filter.toLowerCase());
        Text textBefore = new Text(text.substring(0, filterIndex));
        textBefore.setFont(Font.font("Comfortaa", FontWeight.BOLD, size));
        Text textAfter = new Text(text.substring(filterIndex + filter.length()));
        textAfter.setFont(Font.font("Comfortaa", FontWeight.BOLD, size));
        Text textFilter = new Text(text.substring(filterIndex, filterIndex + filter.length()));
        textFilter.setFont(Font.font("Comfortaa", FontWeight.BOLD, size));
        textFilter.setFill(Color.ORANGE);
        TextFlow result = new TextFlow(textBefore, textFilter, textAfter);
        result.setPadding(new Insets(2, 10, 2, 10));
        return result;
    }

    /**
     * Get the existing set of autocomplete commandSet.
     *
     * @return The existing autocomplete commandSet.
     */


    public SortedSet<String> getEntries() {
        return commandSet;
    }
}
