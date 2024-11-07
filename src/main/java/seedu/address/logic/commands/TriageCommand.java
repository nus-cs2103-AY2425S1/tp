package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRIAGE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Optional;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Triage;

/**
 * Command to triage a person using a given NRIC and triage value.
 */
public class TriageCommand extends Command {

    public static final String COMMAND_WORD = "triage";
    public static final String COMMAND_WORD_SHORT = "t";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Triage patient using numbers 1 to 5. "
            + "Based on Phase of Illness Model -> 1 indicating stable while 5 indicating bereaved.\n"
            + "Parameters: NRIC "
            + PREFIX_TRIAGE + "[TRIAGE]\n"
            + "Example: " + COMMAND_WORD + " S1234567A "
            + PREFIX_TRIAGE + "3";

    public static final String MESSAGE_ADD_TRIAGE_SUCCESS = "Successfully triaged Person: %1$s";

    private final Nric nric;
    private final Triage triage;

    /**
     * Constructs a TriageCommand.
     *
     * @param nric The NRIC of the person to triage.
     * @param triage The triage level (from 1 to 5) for the person.
     */
    public TriageCommand(Nric nric, Triage triage) {
        requireAllNonNull(nric, triage);
        this.nric = nric;
        this.triage = triage;
    }

    /**
     * Executes the command to triage a person by updating their triage value.
     *
     * @param model The model where the triage data is stored.
     * @return The result of the command execution as a CommandResult.
     * @throws CommandException If no person with the matching NRIC is found.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        Optional<Person> personWithMatchingNric = lastShownList.stream()
                .filter(person -> nric.equals(person.getNric()))
                .findFirst();

        if (personWithMatchingNric.isPresent()) {
            Person personToEdit = personWithMatchingNric.get();
            Person editedPerson = new Person(
                    personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(), personToEdit.getNric(),
                    personToEdit.getAddress(), triage, personToEdit.getRemark(), personToEdit.getTags(),
                    personToEdit.getAppointment(), personToEdit.getLogEntries());

            model.setPerson(personToEdit, editedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_ADD_TRIAGE_SUCCESS, Messages.format(editedPerson)));
        } else {
            throw new CommandException(Messages.MESSAGE_NO_PERSON_FOUND);
        }
    }

    /**
     * Checks if two TriageCommand objects are equal.
     *
     * @param other The object to compare with.
     * @return true if the other object is a TriageCommand and has the same NRIC and triage value.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TriageCommand)) {
            return false;
        }

        TriageCommand e = (TriageCommand) other;
        return nric.equals(e.nric)
                && triage.equals(e.triage);
    }
}
