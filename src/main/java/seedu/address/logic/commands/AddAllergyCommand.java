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
 * Command to add an allergy to a patient in the address book.
 */
public class AddAllergyCommand extends Command {

    public static final String COMMAND_WORD = "addAllergy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds allergy to the patient in the address book. "
            + "Parameters: NRIC (must be a valid NRIC in the system) "
            + "[" + PREFIX_ALLERGY + "Allergy]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S1234567A "
            + PREFIX_ALLERGY + "Pollen "
            + PREFIX_ALLERGY + "Peanut";

    public static final String MESSAGE_ADD_ALLERGY_SUCCESS = "Added allergy/allergies: %1$s to Nric: %2$s";
    public static final String PATIENT_DOES_NOT_EXIST = "Patient does not exist in contact list";
    public static final String MESSAGE_DUPLICATE_ALLERGY = "Allergy already assigned: %1$s";

    private final Nric nric;
    private final Set<Allergy> allergies;

    /**
     * Creates an AddAllergyCommand to add the specified {@code Allergy} to the patient with the specified {@code Nric}.
     *
     * @param nric The NRIC of the patient to add the allergy to.
     * @param allergies The allergies to be added to the patient.
     */
    public AddAllergyCommand(Nric nric, Set<Allergy> allergies) {
        requireAllNonNull(nric, allergies);
        this.nric = nric;
        this.allergies = allergies;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Person person = model.fetchPersonIfPresent(new NricMatchesPredicate(nric))
                .orElseThrow(() -> new CommandException(MESSAGE_PERSON_NRIC_NOT_FOUND));

        // if the NRICs do not match, throw exception immediately.
        if (!person.getNric().equals(this.nric)) {
            throw new CommandException(PATIENT_DOES_NOT_EXIST);
        }

        // add the allergies.
        Set<Allergy> updatedAllergySet = new HashSet<>(person.getAllergies());
        for (Allergy allergy : allergies) {
            if (!updatedAllergySet.add(allergy)) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_ALLERGY, allergy.allergyName));
            }
        }

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
     * Generates a command execution success message based on the added allergies.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String resultAllergies = allergies.stream()
                .map(allergy -> allergy.allergyName)
                .collect(Collectors.joining(", ", "[", "]"));

        return String.format(MESSAGE_ADD_ALLERGY_SUCCESS, resultAllergies, personToEdit.getNric().value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddAllergyCommand)) {
            return false;
        }

        AddAllergyCommand c = (AddAllergyCommand) other;
        return nric.equals(c.nric)
                && allergies.equals(c.allergies);
    }
}
