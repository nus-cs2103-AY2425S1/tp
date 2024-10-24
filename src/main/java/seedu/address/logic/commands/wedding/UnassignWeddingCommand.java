package seedu.address.logic.commands.wedding;

import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_REMOVE_WEDDING_SUCCESS;
import static seedu.address.logic.Messages.MESSAGE_WEDDING_NOT_FOUND_IN_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;

/**
 * Removes a wedding associated with an existing person in the Wedlinker.
 */
public class UnassignWeddingCommand extends Command {
    public static final String COMMAND_WORD = "unassign-wedding";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes one or multiple weddings from the person identified "
            + "by the index number used in the last person listing.\n"
            + "Wedding names are case sensitive.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_WEDDING + "WEDDING... (can specify multiple weddings)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_WEDDING + "Craig's Wedding " + PREFIX_WEDDING + "Wedding April 2025.";

    private final Index index;
    private final HashSet<Wedding> weddingsToRemove;

    /**
     * Constructs an UnassignWeddingCommand to remove weddings from a person.
     *
     * @param index The index of the person in the person list.
     * @param weddingsToRemove The list of weddings to be removed.
     */
    public UnassignWeddingCommand(Index index, HashSet<Wedding> weddingsToRemove) {
        this.index = index;
        this.weddingsToRemove = weddingsToRemove;
    }

    /**
     * Generates a command execution success message showing the removed wedding and the person.
     *
     * @param personToEdit The person from whom the weddings were removed.
     * @return A success message indicating the weddings that were removed and the name of the person.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String removedWeddings = weddingsToRemove.stream()
                .map(wedding -> wedding.toString().replaceAll("[\\[\\]]", ""))
                .collect(Collectors.joining(", "));
        return String.format(MESSAGE_REMOVE_WEDDING_SUCCESS, removedWeddings, personToEdit.getName().toString());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Set<Wedding> updatedWeddings = new HashSet<>(personToEdit.getWeddings());

        if (personToEdit.getWeddings().isEmpty()) {
            throw new CommandException(MESSAGE_WEDDING_NOT_FOUND_IN_CONTACT);
        }

        if (weddingsToRemove.isEmpty()) {
            throw new CommandException(MESSAGE_WEDDING_NOT_FOUND_IN_CONTACT);
        }

        if (!updatedWeddings.containsAll(weddingsToRemove)) {
            throw new CommandException(MESSAGE_WEDDING_NOT_FOUND_IN_CONTACT);
        }
        for (Wedding wedding : updatedWeddings) {
            if (weddingsToRemove.contains(wedding)) {
                wedding.decreasePeopleCount();
            }
        }
        updatedWeddings.removeAll(weddingsToRemove);

        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getTags(),
                updatedWeddings);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(personToEdit));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UnassignWeddingCommand)) {
            return false;
        }

        UnassignWeddingCommand otherCommand = (UnassignWeddingCommand) other;
        return index.equals(otherCommand.index)
                && weddingsToRemove.equals(otherCommand.weddingsToRemove);
    }

}
