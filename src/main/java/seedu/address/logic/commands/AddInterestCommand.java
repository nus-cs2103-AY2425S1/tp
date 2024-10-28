package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTEREST;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Interest;
import seedu.address.model.person.Person;

/**
 * Adds interests to an existing person in the address book.
 */
public class AddInterestCommand extends Command {

    public static final String COMMAND_WORD = "addi";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds interests to the specified person. "
            + "Parameters: "
            + PREFIX_INDEX + "INDEX "
            + PREFIX_INTEREST + "INTEREST \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_INTEREST + "Swimming "
            + PREFIX_INTEREST + "Hiking\n";

    public static final String MESSAGE_SUCCESS = "New interests added to %1$s: %2$s";
    public static final String MESSAGE_DUPLICATE_INTERESTS = "This person already has some of the interests: %1$s";
    public static final String MESSAGE_INVALID_INDEX = "The person index provided is invalid";

    private final Index index;
    private final Set<Interest> interestsToAdd;

    /**
     * Creates an AddInterestCommand to add the specified {@code interests} to a person.
     */
    public AddInterestCommand(Index index, Set<Interest> interests) {
        requireNonNull(index);
        requireNonNull(interests);
        this.index = index;
        this.interestsToAdd = interests;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        // Get current interests
        Set<Interest> currentInterests = new HashSet<>(personToEdit.getInterests());

        // Normalize interests for case-insensitive comparison
        Set<String> currentInterestNamesLowerCase = currentInterests.stream()
                .map(interest -> interest.interestName.toLowerCase())
                .collect(Collectors.toSet());

        // Check for case-insensitive duplicates within the new interests provided
        Set<String> newInterestNamesLowerCase = new HashSet<>();
        Set<Interest> duplicateInterests = new HashSet<>();
        Set<Interest> validInterestsToAdd = new HashSet<>();

        for (Interest interest : interestsToAdd) {
            String interestNameLowercase = interest.interestName.toLowerCase();

            // Check if the interest already exists in the person's current interests or is a duplicate in the command
            if (currentInterestNamesLowerCase.contains(interestNameLowercase)
                    || !newInterestNamesLowerCase.add(interestNameLowercase)) {
                duplicateInterests.add(interest);
            } else {
                validInterestsToAdd.add(interest);
            }
        }

        // If all interests are duplicates, throw an error
        if (validInterestsToAdd.isEmpty() && !duplicateInterests.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_INTERESTS, duplicateInterests));
        }

        // Add new interests to current interests
        currentInterests.addAll(validInterestsToAdd);

        // Create the updated person with new interests
        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getWorkExp(),
                personToEdit.getTags(), personToEdit.getUniversity(), personToEdit.getMajor(), currentInterests);

        // Update the person in the model
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, personToEdit.getName(), validInterestsToAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddInterestCommand // instanceof handles nulls
                && index.equals(((AddInterestCommand) other).index)
                && interestsToAdd.equals(((AddInterestCommand) other).interestsToAdd));
    }
}
