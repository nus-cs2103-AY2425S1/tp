package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Stores the history of commands executed. (Solution inspired from addressbook-level-4)
 */
public class CommandHistory {
    private final ObservableList<String> userInputHistory = FXCollections.observableArrayList();
    private final ObservableList<String> unmodifiableUserInputHistory =
            FXCollections.unmodifiableObservableList(userInputHistory);

    private int currentIndex = 0;

    /**
     * Appends {@code userInput} to the list of user input entered.
     */
    public void add(String userInput) {
        requireNonNull(userInput);
        userInputHistory.add(userInput);
        currentIndex = userInputHistory.size(); // reset index to the end
    }

    /**
     * Returns an unmodifiable view of {@code userInputHistory}.
     */
    public ObservableList<String> getHistory() {
        return unmodifiableUserInputHistory;
    }

    /**
     * Returns the previous command in the history.
     */
    public String getPrevious() {
        if (currentIndex > 0) {
            currentIndex--;
        }
        return userInputHistory.get(currentIndex);
    }

    /**
     * Returns the next command in the history.
     */
    public String getNext() {
        if (currentIndex <= userInputHistory.size() - 1) {
            currentIndex++;
        }
        // If currentIndex == size, retrieval will not work but need to allow this possibility so that last command
        // is not skipped over when up is pressed after reaching the blank line by pressing down all the way
        if (currentIndex < userInputHistory.size()) {
            return userInputHistory.get(currentIndex);
        }

        assert currentIndex == userInputHistory.size() : "Index should be equal to size here";
        return ""; // return blank line when pressing down after most recent command is reached
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof CommandHistory)) {
            return false;
        }

        CommandHistory other = (CommandHistory) obj;
        return userInputHistory.equals(other.userInputHistory);
    }
}
