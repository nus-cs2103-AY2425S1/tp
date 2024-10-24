package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.LogicManager;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes the people identified using the displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
                + ": Deletes the people identified by the index numbers used in the displayed person list.\n"
                + "Parameters: INDEXES (must be a positive integer)\n"
                + "Example: " + COMMAND_WORD + " 1, 2";

    public static final String MESSAGE_DELETE_PEOPLE_SUCCESS = "Deleted People:\n%s";

    private final Index[] targetIndexes;
    private final List<Person> personsToDelete = new ArrayList<>();
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);


    /**
     * Takes in the array of indexes to be used for deletion.
     *
     * @param targetIndexes array of Index.
     */
    public DeleteCommand(Index[] targetIndexes) {
        this.targetIndexes = targetIndexes;

    }

    /**
     * Executes the Delete command with multiple >= 1 index.
     *
     * @param model {@code Model} which the command should operate on.
     * @return Message to user that deletions were successful.
     * @throws CommandException If any of the index given falls out of range.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        for (Index targetIndex : targetIndexes) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEXES);
            }
            personsToDelete.add(lastShownList.get(targetIndex.getZeroBased()));
        }

        logger.info("----------------[PEOPLE TO BE DELETED]["
                + Arrays.stream(targetIndexes)
                .map(index -> String.valueOf(index.getOneBased())) // Convert to String
                .collect(Collectors.joining(", ")) + "]");

        String s = personsToDelete.stream().map(person -> {
            model.deleteAppointments(person.getName());
            model.deletePerson(person);
            return Messages.format(person);
        }).collect(Collectors.joining("\n\n"));

        return new CommandResult(String.format(MESSAGE_DELETE_PEOPLE_SUCCESS, s));
    }

    public Index[] getTargetIndexes() {
        return targetIndexes;
    }

    public List<Person> getPersonsToDelete() {
        return personsToDelete;
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand otherDeleteCommand)) {
            return false;
        }

        return Arrays.equals(targetIndexes, otherDeleteCommand.targetIndexes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndexes", Arrays.toString(targetIndexes))
                .toString();
    }
}
