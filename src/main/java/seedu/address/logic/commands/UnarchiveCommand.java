package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Unarchives an existing person in the address book.
 */
public class UnarchiveCommand extends Command {

    public static final String COMMAND_WORD = "unarchive";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unarchives the person identified "
            + "by the index number used in the displayed person list. "
            + "Parameters: INDEX (must be a positive integer)";
    public static final String MESSAGE_UNARCHIVE_PERSON_SUCCESS = "Unarchived Person: %1$s";

    public static final String MESSAGE_PERSON_IS_ALREADY_UNARCHIVED =
            "This person is already unarchived in the address book.";

    private final Index index;

    private Person personToUnarchive;

    /**
     * @param index of the person in the filtered person list to unarchive
     */
    public UnarchiveCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToUnarchive = lastShownList.get(index.getZeroBased());

        if (!personToUnarchive.isArchived()) {
            throw new CommandException(MESSAGE_PERSON_IS_ALREADY_UNARCHIVED);
        }

        Person unarchivedPerson = unarchivePerson(personToUnarchive);
        model.setPerson(personToUnarchive, unarchivedPerson);
        return new CommandResult(String.format(MESSAGE_UNARCHIVE_PERSON_SUCCESS, unarchivedPerson));
    }

    private static Person unarchivePerson(Person personToUnarchive) {
        return new Person(
                personToUnarchive.getName(),
                personToUnarchive.getPhone(),
                personToUnarchive.getEmail(),
                personToUnarchive.getAddress(),
                personToUnarchive.getPriority(),
                personToUnarchive.getRemark(),
                personToUnarchive.getDateOfBirth(),
                personToUnarchive.getIncome(),
                personToUnarchive.getFamilySize(),
                personToUnarchive.getTags(),
                personToUnarchive.getUpdatedAt(),
                false
        );
    }

    /**
     * @return the {@link Person} to be archived if it exists,
     *      otherwise {@code null} if person does not exist or command has not yet been executed.
     */
    public Person getPersonToUnarchive() {
        return personToUnarchive;
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
