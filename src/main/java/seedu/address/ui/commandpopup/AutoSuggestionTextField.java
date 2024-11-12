package seedu.address.ui.commandpopup;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PopupControl;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
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
    @FXML
    private ListView<TextFlow> suggestionList;
    private CommandBox commandBox;
    private ObservableList<TextFlow> textFlowItems;

    /**
     * Constructor that initializes the AutoSuggestionTextField.
     * Sets up the suggestion popup, adds listeners for text changes,
     * and handles key events for cycling through suggestions and selecting them.
     */
    public AutoSuggestionTextField() {
        super();
        this.commandSet = PopUpCommandsSet.commands();

        suggestionPopup = new PopupControl();
        suggestionList = new ListView<>();
        suggestionList.setFocusTraversable(false);

        // Set up the popup
        suggestionPopup.getScene().setRoot(suggestionList);
        suggestionPopup.setAutoHide(true); // Auto hide when clicking outside


        // Listen for text changes
        textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.trim().isEmpty()) {
                String[] text = newText.trim().split("\\s+");
                String command = text[0];
                boolean hasParams = false;
                if (text.length > 1 || newText.contains(" ")) {
                    hasParams = true;
                }
                showSuggestions(command, hasParams);
            } else {
                suggestionPopup.hide();
            }
        });

        // Add key event handler to both the TextField and ListView
        EventHandler<KeyEvent> keyEventHandler = event -> {
            if (event.getCode() == KeyCode.UP && !event.isShiftDown()) {
                commandBox.handleUpEntered();
            }
            if (event.getCode() == KeyCode.DOWN && !event.isShiftDown()) {
                commandBox.handleDownEntered();
            }
            if (event.isShiftDown()) {
                switch (event.getCode()) {
                case UP:
                    cycleThroughList(-1);
                    event.consume();
                    break;
                case DOWN:
                    cycleThroughList(1);
                    event.consume();
                    break;
                default:
                    break;
                }
            } else if (event.getCode() == KeyCode.TAB) {
                handleTab();
                event.consume();
            } else if (event.getCode() == KeyCode.ENTER) {
                handleCommandEntered();
            }
        };

        suggestionList.setOnMouseClicked(event -> {
            TextFlow selectedTextFlow = suggestionList.getSelectionModel().getSelectedItem();
            if (selectedTextFlow instanceof CommandTextFlow) {
                CommandTextFlow commandFlow = (CommandTextFlow) selectedTextFlow;
                setText(commandFlow.getCommandText());
                this.positionCaret(commandFlow.getCommandText().length());
            }
        });

        // Add the handler to both the TextField and ListView
        this.addEventHandler(KeyEvent.KEY_PRESSED, keyEventHandler);
        suggestionList.addEventHandler(KeyEvent.KEY_PRESSED, keyEventHandler);

        // Prevent ListView from handling its default key events
        suggestionList.addEventFilter(KeyEvent.ANY, event -> {
            if (event.isControlDown() || (event.isShiftDown() && event.getCode() == KeyCode.ENTER)) {
                event.consume();
            }
        });

    }

    /**
     * Cycles through the suggestions in the ListView based on the direction.
     * Highlights the selected suggestion and wraps around if necessary.
     *
     * @param direction The direction to cycle: -1 for up, 1 for down.
     */
    private void cycleThroughList(int direction) {
        if (suggestionList.getItems().isEmpty()) {
            return;
        }

        int currentIndex = suggestionList.getSelectionModel().getSelectedIndex();
        int newIndex;

        if (currentIndex == -1) {
            // If nothing is selected, select first or last item depending on direction
            newIndex = (direction > 0) ? 0 : suggestionList.getItems().size() - 1;
        } else {
            // Calculate new index with wrapping
            newIndex = (currentIndex + direction + suggestionList.getItems().size())
                    % suggestionList.getItems().size();
        }

        // Update selection
        suggestionList.getSelectionModel().select(newIndex);
        suggestionList.scrollTo(newIndex);

        // Update background color of selected item
        updateItemBackgrounds(newIndex);
    }

    /**
     * Updates the background color of the suggestion items in the ListView.
     * Highlights the currently selected item.
     *
     * @param selectedIndex The index of the selected item in the ListView.
     */
    private void updateItemBackgrounds(int selectedIndex) {
        for (int i = 0; i < suggestionList.getItems().size(); i++) {
            TextFlow textFlow = suggestionList.getItems().get(i);
            if (i == selectedIndex) {
                textFlow.setBackground(new Background(new BackgroundFill(
                        Color.DODGERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
            } else {
                textFlow.setBackground(Background.EMPTY);
            }
        }
    }

    /**
     * Handles the event when Tab is pressed.
     * Inserts the selected command from the suggestion list into the TextField.
     */
    private void handleTab() {
        TextFlow selectedTextFlow = suggestionList.getSelectionModel().getSelectedItem();
        if (selectedTextFlow != null) {
            if (selectedTextFlow instanceof CommandTextFlow) {
                CommandTextFlow commandFlow = (CommandTextFlow) selectedTextFlow;
                setText(commandFlow.getCommandText());
                this.positionCaret(commandFlow.getCommandText().length());
                //if (suggestionPopup.isShowing()) {
                //    suggestionPopup.hide();
                //}
            }
        }
    }

    /**
     * Displays a list of suggestions based on the current input text.
     * Filters the suggestions from the command set and shows them in the popup.
     *
     * @param input The current input text from the TextField.
     */
    private void showSuggestions(String input, boolean hasParams) {
        textFlowItems = FXCollections.observableArrayList(
                // Filter suggestions based on the input
                popUpFilter(commandSet, input).stream()
                        .map(s -> new CommandTextFlow(s, input))
                        .toList());
        if (input.toLowerCase().equals("edit") && hasParams) {
            textFlowItems.remove(1);
        }
        suggestionList.setItems(textFlowItems);
        if (suggestionList.getItems().isEmpty()) {
            suggestionPopup.hide();
        }
        // Calculate the position of the popup
        Point2D location = localToScreen(0, getHeight());
        suggestionPopup.show(this, location.getX(), location.getY());
        double height = calculateListViewHeight(textFlowItems);
        double width = calculateListViewWidth(textFlowItems);
        suggestionList.setPrefHeight(height);
        suggestionList.setMaxHeight(height);
        suggestionList.setPrefWidth(width);
    }

    /**
     * Hides the suggestion popup.
     */
    public void hidePopup() {
        this.suggestionPopup.hide();
    }

    /**
     * Sets the CommandBox object that processes commands entered into the TextField.
     *
     * @param commandBox The CommandBox object to be set.
     */
    public void setCommandBox(CommandBox commandBox) {
        this.commandBox = commandBox;
    }

    /**
     * Calculates the total height of the ListView based on the number of items and their heights.
     *
     * @param items The items displayed in the ListView.
     * @return The calculated total height.
     */
    private double calculateListViewHeight(ObservableList<TextFlow> items) {
        double totalHeight = 2;
        for (TextFlow item : items) {
            totalHeight += 51;
        }
        return totalHeight;
    }

    /**
     * Calculates the width of the ListView based on the widest item and the width of the TextField.
     *
     * @param items The items displayed in the ListView.
     * @return The calculated width.
     */
    private double calculateListViewWidth(ObservableList<TextFlow> items) {
        double maxWidth = 0;
        for (TextFlow item : items) {
            maxWidth = Math.max(maxWidth, item.prefWidth(0));
        }

        // Add some padding
        maxWidth += 0; // 10 pixels padding on each side

        // Set a minimum width (at least as wide as the TextField)
        double minWidth = this.getWidth();
        maxWidth = Math.max(maxWidth, minWidth);

        // Set a maximum width (e.g., 80% of screen width)
        double screenMaxWidth = Screen.getPrimary().getVisualBounds().getWidth() * 0.8;
        return Math.min(maxWidth, screenMaxWidth);
    }

    /**
     * Filters the command set based on the input string.
     * Returns a list of commands that match the beginning of the input text.
     *
     * @param set The set of available commands.
     * @param filter The input text to filter by.
     * @return A list of matching command suggestions.
     */
    public List<String> popUpFilter(SortedSet<String> set, String filter) {
        return set.stream()
                .filter(e -> e.toLowerCase().startsWith(filter.toLowerCase()))
                .collect(Collectors.toList());
    }


    /**
     * Populates the suggestion popup with a list of matching commands.
     * Highlights the matching parts of the suggestions based on the search request.
     *
     * @param filteredList The list of matching commands to display.
     * @param searchRequest The search text used to filter the commands.
     */
    public List<Label> populatePopup(List<String> filteredList, String searchRequest) {
        //List of "suggestions"
        List<Label> menuItems = new LinkedList<>();
        for (int i = 0; i < filteredList.size(); i++) {
            final String result = filteredList.get(i);
            //label with graphic (TextFlow) to highlight matching prefix
            Label entryLabel = new Label();
            entryLabel.setGraphic(buildTextFlow(result, searchRequest));
            entryLabel.setPrefHeight(20);
            menuItems.add(entryLabel);
        }
        return menuItems;
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
     * Handles the Enter button pressed event.
     */
    @FXML
    public void handleCommandEntered() {
        commandBox.handleCommandEntered();
    }

    /**
     * Get the existing set of autocomplete commandSet.
     *
     * @return The existing autocomplete commandSet.
     */
    public SortedSet<String> getEntries() {
        return commandSet;
    }

    /**
     * Get the existing suggestionList.
     *
     * @return The existing suggestionList.
     */
    public ListView<TextFlow> getSuggestionList() {
        return suggestionList;
    }
}
