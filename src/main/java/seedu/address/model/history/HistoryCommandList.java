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
public class HistoryCommandList implements Iterable<Command> {
    private final ObservableList<Command> internalList = FXCollections.observableArrayList();
    private final ObservableList<Command> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds a history command to the list.
     * The person must not already exist in the list.
     */
    public void add(Command toAdd) {
        requireNonNull(toAdd);
        assert toAdd instanceof AddCommand
                || toAdd instanceof ClearCommand
                || toAdd instanceof DeleteCommand
                || toAdd instanceof EditCommand : "History command is not changing the person list";

        internalList.add(toAdd);
    }

    /**
     * Returns the backing history command list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Command> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Command> iterator() {
        return internalList.iterator();
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
