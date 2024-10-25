package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand.FilterPersonDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.FilterPredicate;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Module;
import seedu.address.model.person.Name;

public class FilterCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        FilterPersonDescriptor firstDescriptor = new FilterPersonDescriptor();
        firstDescriptor.setName(new Name("Alice"));
        FilterCommand firstFilterCommand = new FilterCommand(firstDescriptor);

        FilterPersonDescriptor secondDescriptor = new FilterPersonDescriptor();
        secondDescriptor.setName(new Name("Alice"));
        secondDescriptor.setGender(new Gender("female"));
        FilterCommand secondFilterCommand = new FilterCommand(secondDescriptor);

        // same object -> returns true
        assertTrue(firstFilterCommand.equals(firstFilterCommand));

        // same values -> returns true
        FilterCommand filterCommandCopy = new FilterCommand(firstDescriptor);
        assertTrue(firstFilterCommand.equals(filterCommandCopy));

        // different types -> returns false
        assertFalse(firstFilterCommand.equals(1));

        // null -> returns false
        assertFalse(firstFilterCommand.equals(null));

        // different person -> returns false
        assertFalse(firstFilterCommand.equals(secondFilterCommand));
    }

    @Test
    public void execute_noConditionSatisfied_noPersonFound() {
        // name that not in the data
        FilterPersonDescriptor descriptor = new FilterPersonDescriptor();
        descriptor.setName(new Name("hahaha"));
        FilterPredicate predicate = preparePredicate(descriptor);

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FilterCommand command = new FilterCommand(descriptor);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_oneConditionSatisfied_personFound() {
        FilterPersonDescriptor descriptor = new FilterPersonDescriptor();
        descriptor.setGender(new Gender("female"));
        FilterPredicate predicate = preparePredicate(descriptor);

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        FilterCommand command = new FilterCommand(descriptor);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, CARL, ELLE, FIONA), model.getFilteredPersonList());

        descriptor = new FilterPersonDescriptor();
        descriptor.setModules(Set.of(new Module("MA1522")));
        predicate = preparePredicate(descriptor);

        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        command = new FilterCommand(descriptor);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_twoConditionSatisfied() {
        FilterPersonDescriptor descriptor = new FilterPersonDescriptor();
        descriptor.setModules(Set.of(new Module("MA1522")));
        descriptor.setName(new Name("Alice"));
        FilterPredicate predicate = preparePredicate(descriptor);

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FilterCommand command = new FilterCommand(descriptor);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredPersonList());
    }


    @Test
    public void toStringMethod() {
        FilterPersonDescriptor descriptor = new FilterPersonDescriptor();
        descriptor.setName(new Name("Alice"));
        FilterCommand filterCommand = new FilterCommand(descriptor);
        String expected = FilterCommand.class.getCanonicalName() + "{filterPersonDescriptor=" + descriptor + "}";
        assertEquals(expected, filterCommand.toString());
    }

    /**
     * Parses {@code FilterPersonDescription} into a {@code FilterPredicate}.
     */
    private FilterPredicate preparePredicate(FilterPersonDescriptor descriptor) {
        return new FilterPredicate(descriptor);
    }
}
