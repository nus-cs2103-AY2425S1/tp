package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.dateformatter.DateFormatter.MM_DD_YYYY_FORMATTER;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.policy.CoverageAmount;
import seedu.address.model.policy.ExpiryDate;
import seedu.address.model.policy.HealthPolicy;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicySet;
import seedu.address.model.policy.PremiumAmount;

public class ListExpiringPoliciesCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        // reset model before each test to ensure isolation between tests
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_noExpiringPolicies_showsNoExpiringPoliciesMessage() {
        ListExpiringPoliciesCommand command = new ListExpiringPoliciesCommand(30);
        String expectedMessage = String.format("No policies expiring within the next %d day(s)!", 30);
        assertCommandSuccess(command, model, expectedMessage, model);
    }
    @Test
    public void execute_expiringPoliciesFound_showsExpiringPolicies() {
        Model modelWithExpiringPolicies = new ModelManager(getTypicalAddressBookWithExpiringPolicies(),
                new UserPrefs());

        // dynamically calculate the expected expiry date (25 days from now)
        LocalDate expiringDate = LocalDate.now().plusDays(25);
        String formattedExpiryDate = expiringDate.format(MM_DD_YYYY_FORMATTER);

        ListExpiringPoliciesCommand command = new ListExpiringPoliciesCommand(30);
        String expectedMessage = String.format(
                "The following policies are expiring within %d day(s):\n\n", 30)
                + "Insuree name: Alice Pauline   |   "
                + "Insuree phone: 94351253\nPolicy Type: Health   |   "
                + "Premium Amount: 250.00\nCoverage Amount: 15000.00   |   Expiry Date: "
                + formattedExpiryDate + "\n\n";

        assertCommandSuccess(command, modelWithExpiringPolicies, expectedMessage, modelWithExpiringPolicies);
    }

    @Test
    public void execute_customDaysSpecified_showsExpiringPolicies() {
        Model modelWithExpiringPolicies = new ModelManager(getTypicalAddressBookWithExpiringPolicies(),
                new UserPrefs());

        LocalDate expiringDateAlice = LocalDate.now().plusDays(25);
        String formattedExpiryDateAlice = expiringDateAlice.format(MM_DD_YYYY_FORMATTER);

        LocalDate expiringDateBenson = LocalDate.of(2024, 12, 23);
        String formattedExpiryDateBenson = expiringDateBenson.format(MM_DD_YYYY_FORMATTER);

        // Command with 60 days time frame
        ListExpiringPoliciesCommand command = new ListExpiringPoliciesCommand(60);
        String expectedMessage = String.format(
                "The following policies are expiring within %d day(s):\n\n", 60)
                + "Insuree name: Alice Pauline   |   Insuree phone: 94351253\n"
                + "Policy Type: Health   |   Premium Amount: 250.00\n"
                + "Coverage Amount: 15000.00   |   Expiry Date: " + formattedExpiryDateAlice + "\n\n"
                + "Insuree name: Benson Meier   |   Insuree phone: 98765432\n"
                + "Policy Type: Health   |   Premium Amount: 300.00\n"
                + "Coverage Amount: 3000.00   |   Expiry Date: " + formattedExpiryDateBenson + "\n\n";

        assertCommandSuccess(command, modelWithExpiringPolicies, expectedMessage, modelWithExpiringPolicies);
    }

    @Test
    public void equals() {
        ListExpiringPoliciesCommand command1 = new ListExpiringPoliciesCommand(30);
        ListExpiringPoliciesCommand command2 = new ListExpiringPoliciesCommand(30);
        ListExpiringPoliciesCommand command3 = new ListExpiringPoliciesCommand(60);

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        assertTrue(command1.equals(command2));

        // different number of days -> returns false
        assertFalse(command1.equals(command3));

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
        Policy expiringPolicy = new HealthPolicy(new PremiumAmount(250.00),
                new CoverageAmount(15000.00), new ExpiryDate(LocalDate.now().plusDays(25)));
        policies.add(expiringPolicy);

        // create a new person with the updated policy set to avoid modifying the original state
        Person updatedPerson = new Person(person.getName(), person.getPhone(), person.getEmail(),
                person.getAddress(), person.getTags(), policies);

        model.setPerson(person, updatedPerson);
        return model.getAddressBook();
    }
}
