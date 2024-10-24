package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WORKEXP;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.WorkExp;

/**
 * Adds a work experience to an existing person in the address book.
 */
public class AddWorkExperienceCommand extends Command {

    public static final String COMMAND_WORD = "addw";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a work experience to the specified person. "
            + "Parameters: "
            + PREFIX_INDEX + "INDEX "
            + PREFIX_WORKEXP + "WORK EXPERIENCE \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_WORKEXP + "Intern,Google,2024";

    public static final String MESSAGE_SUCCESS = "New work experience added to %1$s: %2$s";
    public static final String MESSAGE_INVALID_INDEX = "The person index provided is invalid.";

    private final Index index;
    private final WorkExp workExp;

    /**
     * Creates an AddWorkExperience to add the specified {@code WorkExp} to a person at the specified {@code Index}.
     */
    public AddWorkExperienceCommand(Index index, WorkExp workExp) {
        requireNonNull(index);
        requireNonNull(workExp);
        this.index = index;
        this.workExp = workExp;
    }

    /**
     * Executes the AddWorkExperienceCommand to add a work experience to a specified person in the address book.
     *
     * @param model The model in which the command operates. The model must not be null.
     * @return A CommandResult indicating the success of the operation, including a message detailing
     *     the person who was updated and the added work experience.
     * @throws CommandException If the specified index is out of bounds, indicating the person does not exist.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        // Assert that the list of persons is not null
        assert lastShownList != null : "Filtered person list should not be null";

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        // Assert that the person to edit is not null
        assert personToEdit != null : "Person to edit should not be null";

        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), workExp,
                personToEdit.getTags(), personToEdit.getUniversity(), personToEdit.getMajor(),
                personToEdit.getInterests());

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // Assert that the person was successfully updated
        assert model.getFilteredPersonList().contains(editedPerson) : "Edited person should be in the updated list";

        return new CommandResult(String.format(MESSAGE_SUCCESS, personToEdit.getName(), workExp));
    }

    /**
     * Checks whether this AddWorkExperienceCommand is equal to another object.
     *
     * @param other The other object to compare to.
     * @return True if the other object is an instance of AddWorkExperienceCommand and has the same
     *     index and work experience as this command. Otherwise, returns false.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddWorkExperienceCommand)) {
            return false;
        }

        AddWorkExperienceCommand otherCommand = (AddWorkExperienceCommand) other;
        return index.equals(otherCommand.index)
                && workExp.equals(otherCommand.workExp);
    }
}
