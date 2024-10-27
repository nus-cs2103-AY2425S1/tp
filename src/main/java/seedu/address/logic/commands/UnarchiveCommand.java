package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;


/**
 * Unarchives a person identified using its displayed index.
 */
public class UnarchiveCommand extends Command {

    public static final String COMMAND_WORD = "unarchive";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unarchives the persons identified by the index numbers used in the displayed persons list.\n"
            + "Parameters: INDEX (one or more, all must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNARCHIVE_PERSON_SUCCESS = "Unarchived Person: %1$s";
    public static final String MESSAGE_UNARCHIVE_PEOPLE_SUCCESS = "Unarchived People: \n%1$s";

    private final List<Index> targetIndices;

    public UnarchiveCommand(List<Index> targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        List<Person> peopleToUnarchive = new ArrayList<>();
        for (Index index : targetIndices) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person personToUnarchive = lastShownList.get(index.getZeroBased());
            peopleToUnarchive.add(personToUnarchive);
        }

        assert peopleToUnarchive.size() == targetIndices.size();

        List<String> resultMessages = new ArrayList<>();
        for (Person person : peopleToUnarchive) {
            model.unarchivePerson(person);
            resultMessages.add(Messages.format(person));
        }

        assert resultMessages.size() == targetIndices.size();

        if (resultMessages.size() == 1) {
            return new CommandResult(String.format(MESSAGE_UNARCHIVE_PERSON_SUCCESS, resultMessages.get(0)));
        } else {
            return new CommandResult(String.format(MESSAGE_UNARCHIVE_PEOPLE_SUCCESS,
                    String.join("\n", resultMessages)));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnarchiveCommand)) {
            return false;
        }

        UnarchiveCommand otherUnarchiveCommand = (UnarchiveCommand) other;
        return targetIndices.equals(otherUnarchiveCommand.targetIndices);
    }
}
