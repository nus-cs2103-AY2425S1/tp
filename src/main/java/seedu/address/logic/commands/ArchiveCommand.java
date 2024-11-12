package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;


/**
 * Archives a person identified using its displayed index.
 */
public class ArchiveCommand extends Command {

    public static final String COMMAND_WORD = "archive";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Archives the persons identified by the index numbers used in the displayed persons list.\n"
            + "Parameters: INDEX (one or more, all must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_ARCHIVE_PERSON_SUCCESS = "Archived Person: %1$s";
    public static final String MESSAGE_ARCHIVE_PEOPLE_SUCCESS = "Archived People: \n%1$s";

    private final List<Index> targetIndices;

    public ArchiveCommand(List<Index> targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        Predicate<Person> lastShownPredicate = model.getFilteredPersonListPredicate();

        List<Person> peopleToArchive = new ArrayList<>();
        for (Index index : targetIndices) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person personToArchive = lastShownList.get(index.getZeroBased());
            peopleToArchive.add(personToArchive);
        }

        assert peopleToArchive.size() == targetIndices.size();

        List<String> resultMessages = new ArrayList<>();
        for (Person person : peopleToArchive) {
            model.archivePerson(person);
            resultMessages.add(Messages.format(person));
        }

        assert resultMessages.size() == targetIndices.size();

        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredPersonList(lastShownPredicate);

        if (resultMessages.size() == 1) {
            return new CommandResult(String.format(MESSAGE_ARCHIVE_PERSON_SUCCESS, resultMessages.get(0)));
        } else {
            return new CommandResult(String.format(MESSAGE_ARCHIVE_PEOPLE_SUCCESS,
                    String.join("\n", resultMessages)));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ArchiveCommand)) {
            return false;
        }

        ArchiveCommand otherArchiveCommand = (ArchiveCommand) other;
        return targetIndices.equals(otherArchiveCommand.targetIndices);
    }
}
