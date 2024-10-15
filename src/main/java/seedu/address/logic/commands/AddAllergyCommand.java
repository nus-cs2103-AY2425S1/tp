package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.PriorityCommand.PATIENT_DOES_NOT_EXIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Command to add a tag to a patient in the address book.
 */
public class AddAllergyCommand extends Command {

    public static final String COMMAND_WORD = "addAllergy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds allergy to the patient in the address book. "
            + "Parameters: NRIC (must be a valid NRIC in the system) "
            + "[" + PREFIX_TAG + "Allergy]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S1234567A "
            + PREFIX_TAG + "Pollen "
            + PREFIX_TAG + "Peanut";

    public static final String MESSAGE_SUCCESS = "New tag added: %1$s";
    public static final String MESSAGE_DUPLICATE_TAG = "This allergy already exists in the address book %1$s";
    public static final String MESSAGE_CONSTRAINT = "Allergy should be alphanumeric "
            + "and not exceed 30 characters long. \n%1$s";
    private final Nric nric;
    private final Set<Tag> allergies;

    /**
     * Creates an AddAllergyCommand to add the specified {@code Tag} to the patient with the specified {@code Nric}.
     *
     * @param nric The NRIC of the patient to add the tag to.
     * @param allergies The allergies to be added to the patient.
     */
    public AddAllergyCommand(Nric nric, Set<Tag> allergies) {
        requireAllNonNull(nric, allergies);
        this.nric = nric;
        this.allergies = allergies;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        for (Person person : lastShownList) {
            if (person.getNric().equals(this.nric)) {
                Set<Tag> updatedallergieset = new HashSet<>(person.getTags());
                // check for duplicates
                for (Tag tag : allergies) {
                    if (!updatedallergieset.add(tag)) {
                        throw new CommandException(String.format(MESSAGE_DUPLICATE_TAG, tag.toString()));
                    }
                }
                Person editedPerson = new Person(
                        person.getName(), person.getPhone(), person.getEmail(),
                        person.getNric(), person.getAddress(), person.getDateOfBirth(),
                        person.getGender(), updatedallergieset, person.getPriority(), person.getAppointments(),
                        person.getMedCons());
                model.setPerson(person, editedPerson);
                model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
                return new CommandResult(String.format(MESSAGE_SUCCESS, allergies));
            }
        }
        throw new CommandException(PATIENT_DOES_NOT_EXIST);
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

        AddAllergyCommand otherAddAllergyCommand = (AddAllergyCommand) other;
        return nric.equals(otherAddAllergyCommand.nric)
                && allergies.equals(otherAddAllergyCommand.allergies);
    }
}
