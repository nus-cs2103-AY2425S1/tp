package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Interest;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddInterestCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_addDuplicateInterest_failure() {
        // Trying to add an interest that already exists
        Set<Interest> duplicateInterest = Set.of(new Interest("Swimming")); // Case-insensitive duplicate
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        AddInterestCommand addInterestCommand = new AddInterestCommand(INDEX_FIRST_PERSON, duplicateInterest);

        String expectedMessage = String.format(AddInterestCommand.MESSAGE_DUPLICATE_INTERESTS, "[Swimming]");

        assertCommandFailure(addInterestCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidPersonIndex_failure() {
        Set<Interest> newInterests = Set.of(new Interest("Swimming"));
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddInterestCommand addInterestCommand = new AddInterestCommand(outOfBoundIndex, newInterests);

        assertCommandFailure(addInterestCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_addDuplicateCaseInsensitiveInterest_throwsCommandException() {
        Set<Interest> duplicateInterest = Set.of(new Interest("Swimming")); // Case-insensitive duplicate
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        AddInterestCommand addInterestCommand = new AddInterestCommand(INDEX_FIRST_PERSON, duplicateInterest);

        String expectedMessage = String.format(AddInterestCommand.MESSAGE_DUPLICATE_INTERESTS, duplicateInterest);

        assertCommandFailure(addInterestCommand, model, expectedMessage);
    }

    @Test
    public void execute_addNewInterest_success() throws Exception {
        Set<Interest> newInterests = Set.of(new Interest("Hiking"));
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // Combine current and new interests for expected result
        Set<Interest> combinedInterests = new HashSet<>(personToEdit.getInterests());
        combinedInterests.addAll(newInterests);

        // Convert to String[] for PersonBuilder
        String[] combinedInterestsArray = combinedInterests.stream()
                .map(Interest::toString)
                .toArray(String[]::new);

        Person editedPerson = new PersonBuilder(personToEdit).withInterests(combinedInterestsArray).build();
        AddInterestCommand addInterestCommand = new AddInterestCommand(INDEX_FIRST_PERSON, newInterests);

        String expectedMessage = String.format(AddInterestCommand.MESSAGE_SUCCESS, personToEdit.getName(),
                newInterests);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(addInterestCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_addDuplicateInterestsInCommand_throwsCommandException() {
        Set<Interest> interests = Set.of(new Interest("Swimming"), new Interest("swimming"));
        AddInterestCommand addInterestCommand = new AddInterestCommand(INDEX_FIRST_PERSON, interests);

        String expectedMessage = "This person already has some of the interests: [swimming, Swimming]";

        assertCommandFailure(addInterestCommand, model, expectedMessage);
    }

}
