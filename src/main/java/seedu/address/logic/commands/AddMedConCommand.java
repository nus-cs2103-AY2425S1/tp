package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.Messages.MESSAGE_PERSON_NRIC_NOT_FOUND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDCON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.MedCon;
import seedu.address.model.person.Nric;
import seedu.address.model.person.NricMatchesPredicate;
import seedu.address.model.person.Person;

/**
 * Command to assign a medical condition to a patient in the address book.
 */
public class AddMedConCommand extends Command {

    public static final String COMMAND_WORD = "addMedCon";
    public static final String COMMAND_WORD_INSENSITIVE = "addmedcon";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns "
            + "one or more medical conditions to a patient in MediBase3. \n"
            + "Parameters: "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_MEDCON + "CONDITION...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "T0123456F "
            + PREFIX_MEDCON + "Diabetes "
            + PREFIX_MEDCON + "Hypertension";

    public static final String MESSAGE_ADD_MEDCON_SUCCESS = "Added medical condition(s): %1$s to Nric: %2$s";
    public static final String PATIENT_DOES_NOT_EXIST = "Patient does not exist in contact list";
    public static final String MESSAGE_DUPLICATE_MEDCON = "Condition(s) already assigned: %1$s";

    private final Nric nric;
    private final Set<MedCon> medCons;

    /**
     * @param nric of the patient to assign the medical conditions to
     * @param medCons set of medical conditions to be assigned.
     */
    public AddMedConCommand(Nric nric, Set<MedCon> medCons) {
        requireAllNonNull(nric, medCons);

        this.nric = nric;
        this.medCons = medCons;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Person person = model.fetchPersonIfPresent(new NricMatchesPredicate(nric))
                .orElseThrow(() -> new CommandException(MESSAGE_PERSON_NRIC_NOT_FOUND));

        // if the NRICs do not match, throw exception immediately.
        if (!person.getNric().equals(this.nric)) {
            throw new CommandException(PATIENT_DOES_NOT_EXIST);
        }

        Set<MedCon> updatedMedConSet = getUpdatedMedConSet(person);

        // Create the edited person with updated medical conditions.
        Person editedPerson = new Person(
                person.getName(), person.getPhone(), person.getEmail(),
                person.getNric(), person.getAddress(), person.getDateOfBirth(),
                person.getGender(), person.getAllergies(), person.getPriority(), person.getAppointments(),
                updatedMedConSet);

        model.setPerson(person, editedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Retrieves the updated set of medical conditions for a person, ensuring no duplicate conditions are added.
     *
     * @param person The {@code Person} whose medical conditions are being updated.
     * @return A {@code Set<MedCon>} containing the updated list of medical conditions for the person.
     * @throws CommandException if there are any duplicate medical conditions in the provided set.
     */
    private Set<MedCon> getUpdatedMedConSet(Person person) throws CommandException {
        Set<MedCon> updatedMedConSet = new HashSet<>(person.getMedCons());

        String duplicates = medCons.stream()
                .filter(medCon -> !updatedMedConSet.add(medCon))
                .map(MedCon::getMedCon)
                .collect(Collectors.joining(", "));
        if (!duplicates.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_MEDCON, duplicates));
        }

        return updatedMedConSet;
    }


    /**
     * Generates a command execution success message based on the added medical conditions.
     */
    private String generateSuccessMessage(Person personToEdit) {
        StringBuilder medConsString = new StringBuilder();
        medCons.forEach(medCon -> medConsString.append(medCon.medConName).append(", "));

        // Remove trailing comma and space, if any
        if (medConsString.length() > 0) {
            medConsString.setLength(medConsString.length() - 2);
        }

        String resultMedCon = '[' + medConsString.toString() + ']';

        return String.format(MESSAGE_ADD_MEDCON_SUCCESS, resultMedCon, personToEdit.getNric().value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddMedConCommand)) {
            return false;
        }

        AddMedConCommand c = (AddMedConCommand) other;
        return nric.equals(c.nric)
                && medCons.equals(c.medCons);
    }
}
