package seedu.address.logic.commands;

import static seedu.address.logic.Messages.MESSAGE_ADD_WEDDING_SUCCESS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_WEDDING_NOT_FOUND;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;

/**
 * Adds a wedding to an existing person in the Wedlinker.
 */
public class AssignWeddingCommand extends Command {

    public static final String COMMAND_WORD = "assign-wedding";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds one or multiple weddings to the person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "w/[WEDDING]... (can specify multiple weddings)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "w/Craig's Wedding w/Wedding April 2025.";

    private final Index index;
    private final HashSet<Wedding> weddingsToAdd;

    /**
     * Constructs a {@code AssignWedding} Command to add weddings to a person.
     * @param index The index of the person in the person list.
     * @param weddingsToAdd The list of weddings to be added.
     */
    public AssignWeddingCommand(Index index, HashSet<Wedding> weddingsToAdd) {
        this.index = index;
        this.weddingsToAdd = weddingsToAdd;
    }

    /**
     * Generates a command execution success message showing the added weddings and the person.
     *
     * @param personToEdit The person to whom the weddings were added.
     * @return A success message indicating the weddings that were added and the name of the person.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String addedWeddings = weddingsToAdd.stream()
                .map(wedding -> wedding.toString().replaceAll("[\\[\\]]", ""))
                .collect(Collectors.joining(", "));
        return String.format(MESSAGE_ADD_WEDDING_SUCCESS, addedWeddings, personToEdit.getName().toString());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        for (Wedding wedding : weddingsToAdd) {
            if (!model.hasWedding(wedding)) {
                throw new CommandException(MESSAGE_WEDDING_NOT_FOUND);
            }
        }

        Set<Wedding> updatedWeddings = new HashSet<>(personToEdit.getWeddings());
        updatedWeddings.addAll(weddingsToAdd);

        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getTags(),
                updatedWeddings);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }
}
