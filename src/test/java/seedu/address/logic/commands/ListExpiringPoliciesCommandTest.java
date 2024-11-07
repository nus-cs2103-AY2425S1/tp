package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.dateformatter.DateFormatter.MM_DD_YYYY_FORMATTER;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClients.getTypicalPrudy;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyPrudy;
import seedu.address.model.UserPrefs;
import seedu.address.model.claim.ClaimList;
import seedu.address.model.client.Client;
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
        model = new ModelManager(getTypicalPrudy(), new UserPrefs());
    }

    @Test
    public void execute_noExpiringPolicies_showsNoExpiringPoliciesMessage() {
        ListExpiringPoliciesCommand command = new ListExpiringPoliciesCommand(30);
        String expectedMessage = getNoExpiringPoliciesMessage(30);
        assertCommandSuccess(command, model, expectedMessage, model);
    }

    @Test
    public void execute_zeroDaysInput_showsNoExpiringPoliciesMessage() {
        // Edge case where daysFromExpiry is zero (assuming it's valid based on design)
        ListExpiringPoliciesCommand command = new ListExpiringPoliciesCommand(0);
        String expectedMessage = getNoExpiringPoliciesMessage(0);
        assertCommandSuccess(command, model, expectedMessage, model);
    }


    @Test
    public void execute_expiringPoliciesFound_showsExpiringPolicies() {
        Model modelWithExpiringPolicies = new ModelManager(getTypicalPrudyWithExpiringPolicies(), new UserPrefs());

        // Expiry date calculation for Alice Pauline
        LocalDate expiringDate = LocalDate.now().plusDays(25);
        String formattedExpiryDate = expiringDate.format(MM_DD_YYYY_FORMATTER);

        ListExpiringPoliciesCommand command = new ListExpiringPoliciesCommand(30);
        String expectedMessage = getExpectedExpiringPoliciesMessage(30,
                "Alice Pauline", "94351253", "Health", "250.00", "15000.00", formattedExpiryDate);

        assertCommandSuccess(command, modelWithExpiringPolicies, expectedMessage, modelWithExpiringPolicies);
    }

    @Test
    public void execute_customDaysSpecified_showsExpiringPolicies() {
        Model modelWithExpiringPolicies = new ModelManager(getTypicalPrudyWithExpiringPolicies(), new UserPrefs());

        // Expiry dates for Alice Pauline and Benson Meier
        LocalDate expiringDateAlice = LocalDate.now().plusDays(25);
        String formattedExpiryDateAlice = expiringDateAlice.format(MM_DD_YYYY_FORMATTER);

        LocalDate expiringDateBenson = LocalDate.of(2024, 12, 23);
        String formattedExpiryDateBenson = expiringDateBenson.format(MM_DD_YYYY_FORMATTER);

        ListExpiringPoliciesCommand command = new ListExpiringPoliciesCommand(60);
        String expectedMessage = String.format("The following policies are expiring within %d day(s):\n\n", 60)
                + String.format("Insuree name: Alice Pauline   |   Insuree phone: 94351253\n"
                + "Policy Type: Health   |   Premium Amount: 250.00\n"
                + "Coverage Amount: 15000.00   |   Expiry Date: %s\n\n", formattedExpiryDateAlice)
                + String.format("Insuree name: Benson Meier   |   Insuree phone: 98765432\n"
                + "Policy Type: Health   |   Premium Amount: 300.00\n"
                + "Coverage Amount: 3000.00   |   Expiry Date: %s\n\n", formattedExpiryDateBenson);

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

    /**
     * Generates a message indicating that there are no expiring policies.
     *
     * @param days The number of days within which policies are checked for expiration.
     * @return The expected message for no expiring policies.
     */
    private String getNoExpiringPoliciesMessage(int days) {
        return String.format("No policies expiring within the next %d day(s)!", days);
    }

    /**
     * Generates the expected success message for an expiring policy.
     *
     * @param days            The number of days within which policies are checked for expiration.
     * @param insureeName     The name of the client who holds the policy.
     * @param insureePhone    The phone number of the client.
     * @param policyType      The type of the policy.
     * @param premiumAmount   The premium amount of the policy.
     * @param coverageAmount  The coverage amount of the policy.
     * @param formattedExpiryDate The formatted expiry date of the policy.
     * @return The expected message for an expiring policy.
     */
    private String getExpectedExpiringPoliciesMessage(int days, String insureeName, String insureePhone,
                                                      String policyType, String premiumAmount,
                                                      String coverageAmount, String formattedExpiryDate) {
        return String.format("The following policies are expiring within %d day(s):\n\n", days)
                + String.format("Insuree name: %s   |   Insuree phone: %s\n"
                        + "Policy Type: %s   |   Premium Amount: %s\n"
                        + "Coverage Amount: %s   |   Expiry Date: %s\n\n",
                insureeName, insureePhone, policyType, premiumAmount, coverageAmount, formattedExpiryDate);
    }

    /**
     * Retrieves a typical Prudy object with clients who have expiring policies.
     *
     * @return ReadOnlyPrudy with clients that have expiring policies set.
     */
    private ReadOnlyPrudy getTypicalPrudyWithExpiringPolicies() {
        Model model = new ModelManager(getTypicalPrudy(), new UserPrefs());
        Client client = model.getFilteredClientList().get(INDEX_FIRST_CLIENT.getZeroBased());

        PolicySet policies = new PolicySet();
        Policy expiringPolicy = new HealthPolicy(new PremiumAmount(250.00),
                new CoverageAmount(15000.00), new ExpiryDate(LocalDate.now().plusDays(25)),
                new ClaimList());
        policies.add(expiringPolicy);

        Client updatedClient = new Client(client.getName(), client.getPhone(), client.getEmail(),
                client.getAddress(), client.getTags(), policies);

        model.setClient(client, updatedClient);
        return model.getPrudy();
    }

}
