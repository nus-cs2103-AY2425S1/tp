package seedu.address.logic.commands.wedding;

import static seedu.address.logic.Messages.MESSAGE_ASSIGN_WEDDING_SUCCESS;
import static seedu.address.logic.Messages.MESSAGE_FORCE_ASSIGN_WEDDING_TO_CONTACT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_WEDDING_ALREADY_ASSIGNED;
import static seedu.address.logic.Messages.MESSAGE_WEDDING_NOT_FOUND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
 * Adds a wedding to an existing person in the Wedlinker.
 */
public class AssignWeddingCommand extends Command {

    public static final String COMMAND_WORD = "assign-wedding";

    public static final String COMMAND_KEYWORD = "asw";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds one or multiple weddings to the person identified "
            + "by the index number used in the last person listing.\n"
            + "Wedding names are case sensitive.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_WEDDING + "WEDDING [p1/] [p2/] + ... "
            + PREFIX_WEDDING + "WEDDING [p1/] [p2/] (can specify multiple weddings)\n"
            + "[p1/] and [p2/] can be used to set a person as the first or second partner in a Wedding.\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_WEDDING + "Craig's Wedding " + PREFIX_WEDDING + "Wedding April 2025 p1/.";

    private final Index index;
    private final Map<Wedding, String> weddingsToAdd;
    private final boolean force;

    /**
     * Constructs a {@code AssignWedding} Command to add weddings to a person with the force flag.
     * @param index The index of the person in the person list.
     * @param weddingsToAdd The list of weddings to be added.
     * @param force Whether the command should force the assignment by creating the Wedding object.
     */
    public AssignWeddingCommand(Index index, Map<Wedding, String> weddingsToAdd, boolean force) {
        this.index = index;
        this.weddingsToAdd = weddingsToAdd;
        this.force = force;
    }

    /**
     * Generates a command execution success message showing the added weddings and the person.
     *
     * @param personToEdit The person to whom the weddings were added.
     * @return A success message indicating the weddings that were added and the name of the person.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String addedWeddings = weddingsToAdd.keySet().stream()
                .map(wedding -> wedding.toString().replaceAll("[\\[\\]]", ""))
                .collect(Collectors.joining(", "));
        return String.format(MESSAGE_ASSIGN_WEDDING_SUCCESS, addedWeddings, personToEdit.getName().toString());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(
                    MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, 1, lastShownList.size() + 1
            ));
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        Set<Wedding> modelWeddings = new HashSet<>();

        for (Map.Entry<Wedding, String> entry : weddingsToAdd.entrySet()) {
            Wedding wedding = entry.getKey();

            if (!model.hasWedding(wedding)) {
                if (this.force) {
                    CreateWeddingCommand newWeddingCommand = new CreateWeddingCommand(wedding);
                    newWeddingCommand.execute(model);
                } else {
                    throw new CommandException(
                            MESSAGE_WEDDING_NOT_FOUND
                                    + "\n"
                                    + MESSAGE_FORCE_ASSIGN_WEDDING_TO_CONTACT);
                }
            }

            // Work with the model's copy of the wedding
            wedding = model.getWedding(wedding);

            // Check if person is already assigned to the wedding
            if (wedding.hasPerson(personToEdit)) {
                throw new CommandException(String.format(
                        MESSAGE_WEDDING_ALREADY_ASSIGNED, personToEdit.getName()
                ));
            }
            Wedding editedWedding = wedding.clone();
            String type = entry.getValue();
            switch (type) {
            case "p1":
                if (editedWedding.hasPartner1()) {
                    Person modelPartner1 = model.getPerson(editedWedding.getPartner1());
                    modelPartner1.removeWedding(wedding);
                    model.setPerson(modelPartner1, modelPartner1);
                }
                editedWedding.setPartner1(model.getPerson(personToEdit));
                break;
            case "p2":
                if (editedWedding.hasPartner2()) {
                    Person modelPartner2 = model.getPerson(editedWedding.getPartner2());
                    modelPartner2.removeWedding(wedding);
                    model.setPerson(modelPartner2, modelPartner2);
                }
                editedWedding.setPartner2(model.getPerson(personToEdit));
                break;
            case "g":
                editedWedding.addToGuestList(model.getPerson(personToEdit));
                break;
            default:
                break;
            }
            model.setWedding(wedding, editedWedding);
            modelWeddings.add(editedWedding);
        }

        Set<Wedding> updatedWeddings = new HashSet<>(personToEdit.getWeddings());
        updatedWeddings.addAll(modelWeddings);

        Person editedPerson = PersonWeddingUtil.getNewPerson(personToEdit, updatedWeddings);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AssignWeddingCommand otherCommand)) {
            return false;
        }

        return index.equals(otherCommand.index) && weddingsToAdd.keySet()
                .equals(otherCommand.weddingsToAdd.keySet())
                && this.force == otherCommand.force;
    }
}
