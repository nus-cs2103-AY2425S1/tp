package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.owner.Owner;
import seedu.address.model.pet.Pet;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_LINK_COMMAND = "Must link owner to pet! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";

    public static final String MESSAGE_INVALID_OWNER_DISPLAYED_INDEX = "The owner index provided is invalid";
    public static final String MESSAGE_INVALID_PET_DISPLAYED_INDEX = "The pet index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_OWNERS_LISTED_OVERVIEW = "%1$d owner listed!";
    public static final String MESSAGE_PETS_LISTED_OVERVIEW = "%1$d pet listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
        "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
            Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code owner} for display to the user.
     */
    public static String format(Owner owner) {
        final StringBuilder builder = new StringBuilder();
        builder.append(owner.getName())
            .append("; Phone: ")
            .append(owner.getPhone())
            .append("; Email: ")
            .append(owner.getEmail())
            .append("; Address: ")
            .append(owner.getAddress());
        return builder.toString();
    }

    /**
     * Formats the {@code pet} for display to the user.
     */
    public static String format(Pet pet) {
        final StringBuilder builder = new StringBuilder();
        builder.append(pet.getName().name)
            .append("; Species: ")
            .append(pet.getSpecies().value)
            .append("; Breed: ")
            .append(pet.getBreed().value)
            .append("; Age: ")
            .append(pet.getAge().value)
            .append("; Sex: ")
            .append(pet.getSex().value)
            .append("; Tags: ");
        pet.getTags().forEach(builder::append);
        return builder.toString();
    }
}
