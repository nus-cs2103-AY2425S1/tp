package seedu.address.model.history;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.edit.EditCommand;

/**
 * A list of history commands.
 */
public class HistoryCommandList implements Iterable<HistoryCommand> {
    private final ObservableList<HistoryCommand> internalList = FXCollections.observableArrayList();
    private static String originalCommandText;

    public HistoryCommandList() {
    }

    /**
     * Adds a history command to the list.
     */
    public void add(Command toAdd) {
        requireNonNull(toAdd);
        assert originalCommandText != null : "The original command text should not be null";
        assert toAdd instanceof AddCommand
                || toAdd instanceof ClearCommand
                || toAdd instanceof DeleteCommand
                || toAdd instanceof EditCommand : "History command is not changing the person list";

        internalList.add(0, HistoryCommand.of(toAdd, originalCommandText));
    }

    public static void setCommandHistoryText(String input) {
        originalCommandText = input;
    }

    public ObservableList<HistoryCommand> getHistoryCommands() {
        return this.internalList;
    }

    @Override
    public Iterator<HistoryCommand> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object otherList) {
        if (this == otherList) {
            return true;
        }

        // instanceof handles nulls
        if (!(otherList instanceof HistoryCommandList)) {
            return false;
        }

        HistoryCommandList other = (HistoryCommandList) otherList;

        return internalList.equals(other.getHistoryCommands());
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
