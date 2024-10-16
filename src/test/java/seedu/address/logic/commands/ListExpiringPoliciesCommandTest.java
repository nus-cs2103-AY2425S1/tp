package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.policy.HealthPolicy;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicySet;

public class ListExpiringPoliciesCommandTest {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private Model model;

    @BeforeEach
    public void setUp() {
        // reset model before each test to ensure isolation between tests
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_noExpiringPolicies_showsNoExpiringPoliciesMessage() {
        ListExpiringPoliciesCommand command = new ListExpiringPoliciesCommand();
        String expectedMessage = "No policies expiring within the next 30 days!";
        assertCommandSuccess(command, model, expectedMessage, model);
    }

    @Test
    public void execute_expiringPoliciesFound_showsExpiringPolicies() {
        Model modelWithExpiringPolicies = new ModelManager(getTypicalAddressBookWithExpiringPolicies(),
                new UserPrefs());

        // dynamically calculate the expected expiry date (25 days from now)
        LocalDate expiringDate = LocalDate.now().plusDays(25);
        String formattedExpiryDate = expiringDate.format(DATE_FORMATTER);

        ListExpiringPoliciesCommand command = new ListExpiringPoliciesCommand();
        String expectedMessage = "The following policies are near expiry:\n\n"
                + "Insuree phone: 94351253\nPolicy Type: Health\n"
                + "Premium Amount: 250.00\nCoverage Amount: 15000.00\nExpiry Date: " + formattedExpiryDate + "\n\n";

        assertCommandSuccess(command, modelWithExpiringPolicies, expectedMessage, modelWithExpiringPolicies);
    }

    @Test
    public void equals() {
        ListExpiringPoliciesCommand command1 = new ListExpiringPoliciesCommand();
        ListExpiringPoliciesCommand command2 = new ListExpiringPoliciesCommand();

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // different object -> returns true
        assertTrue(command1.equals(command2));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different class -> returns false
        assertFalse(command1.equals(new ClearCommand()));
    }

    private ReadOnlyAddressBook getTypicalAddressBookWithExpiringPolicies() {
        // reset model to prevent any side effects across tests
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PolicySet policies = new PolicySet(); // Avoid mutating original policies

        // add new policy that expires within 30 days
        Policy expiringPolicy = new HealthPolicy(250.00, 15000.00, LocalDate.now().plusDays(25));
        policies.add(expiringPolicy);

        // create a new person with the updated policy set to avoid modifying the original state
        Person updatedPerson = new Person(person.getName(), person.getPhone(), person.getEmail(),
                person.getAddress(), person.getTags(), policies);

        model.setPerson(person, updatedPerson);
        return model.getAddressBook();
    }
}
