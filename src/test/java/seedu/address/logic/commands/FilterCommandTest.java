package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookFilter;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.RoleContainsKeywordsPredicate;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBookFilter(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBookFilter(), new UserPrefs());
    }

    @Test
    public void execute_multipleCriteria_filtersSuccessfully() throws CommandException {
        // Create a FilterCommand with multiple criteria
        FilterCommand filterCommand = new FilterCommand("John", "friends", "example@test.com",
                "94351253", "Main St");

        // Set up expected model to filter based on criteria
        expectedModel.updateFilteredPersonList(person -> new NameContainsKeywordsPredicate(Arrays.asList("John"))
                    .test(person)
                && new TagContainsKeywordsPredicate(Arrays.asList("friends")).test(person)
                && new EmailContainsKeywordsPredicate(Arrays.asList("example@test.com")).test(person)
                && new PhoneContainsKeywordsPredicate(Arrays.asList("94351253")).test(person)
                && new AddressContainsKeywordsPredicate(Arrays.asList("Main St")).test(person));

        // Execute the command
        CommandResult result = filterCommand.execute(model);

        // Check that the filtered list matches the expected result
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
        assertEquals(String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_emptyCriteria_throwsCommandException() {
        // Attempting to filter with no criteria should result in an error
        FilterCommand filterCommand = new FilterCommand("", "", "", "", "");
        assertThrows(CommandException.class, () -> filterCommand.execute(model));
    }

    @Test
    public void execute_nameCriteria_filtersByName() throws CommandException {
        // Filter by name only
        FilterCommand filterCommand = new FilterCommand("John", "", "", "", "");

        // Set up expected model to filter by name only
        expectedModel.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList("John")));

        // Execute the command
        CommandResult result = filterCommand.execute(model);

        // Check that the filtered list matches the expected result
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
        assertEquals(String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_tagCriteria_filtersByTag() throws CommandException {
        // Filter by tag only
        FilterCommand filterCommand = new FilterCommand("", "friends", "", "", "");

        // Set up expected model to filter by tag only
        expectedModel.updateFilteredPersonList(new RoleContainsKeywordsPredicate(Arrays.asList("friends")));

        // Execute the command
        CommandResult result = filterCommand.execute(model);

        // Check that the filtered list matches the expected result
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
        assertEquals(String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_emailCriteria_filtersByEmail() throws CommandException {
        // Filter by email only
        FilterCommand filterCommand = new FilterCommand("", "", "example@test.com", "", "");

        // Set up expected model to filter by email only
        expectedModel.updateFilteredPersonList(new EmailContainsKeywordsPredicate(Arrays.asList("example@test.com")));

        // Execute the command
        CommandResult result = filterCommand.execute(model);

        // Check that the filtered list matches the expected result
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
        assertEquals(String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_phoneCriteria_filtersByPhone() throws CommandException {
        // Filter by phone only
        FilterCommand filterCommand = new FilterCommand("", "", "", "94351253", "");

        // Set up expected model to filter by phone only
        expectedModel.updateFilteredPersonList(new PhoneContainsKeywordsPredicate(Arrays.asList("94351253")));

        // Execute the command
        CommandResult result = filterCommand.execute(model);

        // Check that the filtered list matches the expected result
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
        assertEquals(String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_addressCriteria_filtersByAddress() throws CommandException {
        // Filter by address only
        FilterCommand filterCommand = new FilterCommand("", "", "", "", "Main St");

        // Set up expected model to filter by address only
        expectedModel.updateFilteredPersonList(new AddressContainsKeywordsPredicate(Arrays.asList("Main St")));

        // Execute the command
        CommandResult result = filterCommand.execute(model);

        // Check that the filtered list matches the expected result
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
        assertEquals(String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()),
                result.getFeedbackToUser());
    }
}
