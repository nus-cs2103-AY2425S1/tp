package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalCampusConnect;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

public class SuperFindCommandTest {

    private Model model;
    private final String nameKeyword = "Alice";
    private final String phoneKeyword = "94351253";
    private final String tagKeyword = "Friend";
    private final String emailKeyword = "alice@example.com";

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalCampusConnect(), new UserPrefs());
    }

    @Test
    public void execute_validNameKeyword_success() {
        ContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList(nameKeyword));
        SuperFindCommand command = new SuperFindCommand(predicate);

        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        Model expectedModel = new ModelManager(model.getCampusConnect(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyNameKeyword_success() {
        ContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(Arrays.asList(""));
        SuperFindCommand command = new SuperFindCommand(predicate);

        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        Model expectedModel = new ModelManager(model.getCampusConnect(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validEmailKeyword_success() {
        ContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(Arrays.asList(emailKeyword));
        SuperFindCommand command = new SuperFindCommand(predicate);

        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        Model expectedModel = new ModelManager(model.getCampusConnect(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyEmailKeyword_success() {
        ContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList(""));
        SuperFindCommand command = new SuperFindCommand(predicate);

        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        Model expectedModel = new ModelManager(model.getCampusConnect(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyPhoneKeyword_success() {
        ContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate(Arrays.asList(""));
        SuperFindCommand command = new SuperFindCommand(predicate);

        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        Model expectedModel = new ModelManager(model.getCampusConnect(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);

        // should return no person
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validPhoneKeyword_success() {
        ContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate(Arrays.asList(phoneKeyword));
        SuperFindCommand command = new SuperFindCommand(predicate);

        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        Model expectedModel = new ModelManager(model.getCampusConnect(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validTagKeyword_success() {
        ContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Arrays.asList(tagKeyword));
        SuperFindCommand command = new SuperFindCommand(predicate);

        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        Model expectedModel = new ModelManager(model.getCampusConnect(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyTagKeyword_success() {
        ContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Arrays.asList(""));
        SuperFindCommand command = new SuperFindCommand(predicate);

        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        Model expectedModel = new ModelManager(model.getCampusConnect(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);

        // should only return Persons with no tag
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        ContainsKeywordsPredicate predicateA = new NameContainsKeywordsPredicate(Arrays.asList(nameKeyword));
        ContainsKeywordsPredicate predicateB = new PhoneContainsKeywordsPredicate(Arrays.asList(phoneKeyword));
        SuperFindCommand commandA = new SuperFindCommand(predicateA);
        SuperFindCommand commandB = new SuperFindCommand(predicateB);
        SuperFindCommand commandC = new SuperFindCommand(predicateA);

        assertTrue(commandA.equals(commandC));
        assertFalse(commandA.equals(commandB));
        assertFalse(commandA.equals(null));
        assertFalse(commandA.equals(new Object()));
    }
}
