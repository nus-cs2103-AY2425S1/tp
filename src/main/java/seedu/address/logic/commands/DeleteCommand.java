package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final List<Index> indexList;

    public DeleteCommand(List<Index> indexList) {
        this.indexList = indexList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        List<Person> personToDeleteList = new ArrayList<>();

        //Want to arrange the indexList as descending order
        indexList.sort(Comparator.comparing(Index::getZeroBased).reversed());

        for (Index targetIndex : indexList) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
            personToDeleteList.add(personToDelete);
            model.deletePerson(personToDelete);
        }
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.listFormat(personToDeleteList)));
    }

    /**
     * Check if the list to be deleted are exactly equal
     *
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        boolean output = true;
        for (int i = 0; i < indexList.size(); i++) {
            Index targetIndex = indexList.get(i);
            Index otherIndex = otherDeleteCommand.indexList.get(i);
            output = output && targetIndex.equals(otherIndex);
        }
        return output;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndices", indexList != null ? indexList.toString() : "[]")
                .toString();
    }

}
