package seedu.address.ui;

import javafx.stage.Stage;

/**
 * API of UI component
 */
public interface Ui {

    /**
     * States of UI display.
     */
    public enum UiState {
        DETAILS, TASKS, SPECIFIC_DETAILS
    }

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

}
