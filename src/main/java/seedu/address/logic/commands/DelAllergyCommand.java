package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.Messages.MESSAGE_PERSON_NRIC_NOT_FOUND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Allergy;
import seedu.address.model.person.Nric;
import seedu.address.model.person.NricMatchesPredicate;
import seedu.address.model.person.Person;

/**
 * Command to unassign one or more allergies from a patient in the address book.
 */
public class DelAllergyCommand extends Command {

    public static final String COMMAND_WORD = "delAllergy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes allergy from patient in the address book. "
            + "Parameters: NRIC (must be a valid NRIC in the system) "
            + "[" + PREFIX_ALLERGY + "Allergy]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S1234567A "
            + PREFIX_ALLERGY + "Pollen "
            + PREFIX_ALLERGY + "Peanut";

    public static final String MESSAGE_DELETE_ALLERGY_SUCCESS = "Removed allergy: %1$s from Nric: %2$s";
    public static final String PATIENT_DOES_NOT_EXIST = "Patient does not exist in contact list";
    public static final String MESSAGE_ALLERGY_NOT_FOUND = "Allergy not found: %1$s";

    private final Nric nric;
    private final Set<Allergy> allergies;

    /**
     * Creates a {@code DelAllergyCommand} to unassign the specified allergies from a patient.
     *
     * @param nric The NRIC of the patient to unassign the allergies from.
     * @param allergies The set of allergies to be removed.
     */
    public DelAllergyCommand(Nric nric, Set<Allergy> allergies) {
        requireAllNonNull(nric, allergies);

        this.nric = nric;
        this.allergies = allergies;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Person person = model.fetchPersonIfPresent(new NricMatchesPredicate(nric))
                .orElseThrow(() -> new CommandException(MESSAGE_PERSON_NRIC_NOT_FOUND));

        Set<Allergy> updatedAllergySet = new HashSet<>(person.getAllergies());

        // check if the allergies to delete exist in the current set
        for (Allergy allergy : allergies) {
            if (!updatedAllergySet.remove(allergy)) {
                throw new CommandException(String.format(MESSAGE_ALLERGY_NOT_FOUND, allergy.allergyName));
            }
        }

        // create an edited person with the updated allergies
        Person editedPerson = new Person(
                person.getName(), person.getPhone(), person.getEmail(),
                person.getNric(), person.getAddress(), person.getDateOfBirth(),
                person.getGender(), updatedAllergySet, person.getPriority(), person.getAppointments(),
                person.getMedCons());

        model.setPerson(person, editedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(generateSuccessMessage(editedPerson));
    }


    /**
     * Generates a command execution success message based on the removed medical conditions.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String resultAllergy = allergies.stream()
                .map(medCon -> medCon.allergyName)
                .collect(Collectors.joining(", ", "[", "]"));

        return String.format(MESSAGE_DELETE_ALLERGY_SUCCESS, resultAllergy, personToEdit.getNric().value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DelAllergyCommand)) {
            return false;
        }

        DelAllergyCommand c = (DelAllergyCommand) other;
        return nric.equals(c.nric)
                && allergies.equals(c.allergies);
    }
}
