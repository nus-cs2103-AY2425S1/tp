package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.JOE;
import static seedu.address.testutil.TypicalPersons.MOE;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_BTC_MAIN;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_ETH_MAIN;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_SOL_MAIN;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.addresses.Network;
import seedu.address.model.addresses.PublicAddress;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class RetrievePublicAddressCommandTest {
    private Model model = new ModelManager(new AddressBook(), new UserPrefs());

    private String createPersonDetails(Person person, PublicAddress publicAddress) {
        return String.format(RetrievePublicAddressCommand.MESSAGE_PERSON_FORMAT,
            person.getName(), publicAddress.getNetwork(), publicAddress.getLabel(),
            publicAddress.getPublicAddressString());
    }

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new AddressBook(), new UserPrefs());
        model.addPerson(new PersonBuilder(JOE).build());
        model.addPerson(new PersonBuilder(MOE).build());
        model.addPerson(new PersonBuilder(IDA).build());
    }

    // EP: Empty network, empty name
    @Test
    public void execute_validArgsAnyNetworkAnyName_success() {
        RetrievePublicAddressCommand retrieveCommand =
            new RetrievePublicAddressCommand(VALID_PUBLIC_ADDRESS_BTC_MAIN.getLabel(), "");

        List<String> personsDetails =
            Stream.of(createPersonDetails(JOE, VALID_PUBLIC_ADDRESS_BTC_MAIN),
                    createPersonDetails(JOE, VALID_PUBLIC_ADDRESS_ETH_MAIN),
                    createPersonDetails(MOE, VALID_PUBLIC_ADDRESS_SOL_MAIN))
                .sorted()
                .toList();
        String expecedMessage = String.format(RetrievePublicAddressCommand.MESSAGE_RETRIEVE_PUBLIC_ADDRESS_SUCCESS,
            personsDetails.size(), String.join("\n", personsDetails));

        assertCommandSuccess(retrieveCommand, model, expecedMessage, model);
    }

    // EP: Empty network
    @Test
    public void execute_validArgsAnyNetworkSpecifiedName_success() {
        RetrievePublicAddressCommand retrieveCommand =
            new RetrievePublicAddressCommand(VALID_PUBLIC_ADDRESS_BTC_MAIN.getLabel(), JOE.getName().toString());

        List<String> personsDetails =
            Stream.of(createPersonDetails(JOE, VALID_PUBLIC_ADDRESS_BTC_MAIN),
                    createPersonDetails(JOE, VALID_PUBLIC_ADDRESS_ETH_MAIN))
                .sorted()
                .toList();
        String expecedMessage = String.format(RetrievePublicAddressCommand.MESSAGE_RETRIEVE_PUBLIC_ADDRESS_SUCCESS,
            personsDetails.size(), String.join("\n", personsDetails));

        assertCommandSuccess(retrieveCommand, model, expecedMessage, model);
    }

    // EP: Empty name
    @Test
    public void execute_validArgsSpecifiedNetworkAnyName_success() {
        RetrievePublicAddressCommand retrieveCommand =
            new RetrievePublicAddressCommand(VALID_PUBLIC_ADDRESS_BTC_MAIN.getLabel(), "", Network.BTC);

        List<String> personsDetails =
            Stream.of(createPersonDetails(JOE, VALID_PUBLIC_ADDRESS_BTC_MAIN))
                .toList();
        String expecedMessage = String.format(RetrievePublicAddressCommand.MESSAGE_RETRIEVE_PUBLIC_ADDRESS_SUCCESS,
            personsDetails.size(), String.join("\n", personsDetails));

        assertCommandSuccess(retrieveCommand, model, expecedMessage, model);
    }

    // EP: Valid fields
    @Test
    public void execute_validArgsSpecifiedNetworkAndName_success() {
        RetrievePublicAddressCommand retrieveCommand =
            new RetrievePublicAddressCommand(VALID_PUBLIC_ADDRESS_BTC_MAIN.getLabel(),
                JOE.getName().toString(), Network.BTC);

        List<String> personsDetails =
            Stream.of(createPersonDetails(JOE, VALID_PUBLIC_ADDRESS_BTC_MAIN)).toList();
        String expecedMessage = String.format(RetrievePublicAddressCommand.MESSAGE_RETRIEVE_PUBLIC_ADDRESS_SUCCESS,
            personsDetails.size(), String.join("\n", personsDetails));

        assertCommandSuccess(retrieveCommand, model, expecedMessage, model);
    }

    // EP: Partial label
    @Test
    public void execute_validArgsPartialLabel_success() {
        RetrievePublicAddressCommand retrieveCommand =
            new RetrievePublicAddressCommand(VALID_PUBLIC_ADDRESS_BTC_MAIN.getLabel().substring(0, 4), "");

        List<String> personsDetails =
            Stream.of(createPersonDetails(JOE, VALID_PUBLIC_ADDRESS_BTC_MAIN),
                    createPersonDetails(JOE, VALID_PUBLIC_ADDRESS_ETH_MAIN),
                    createPersonDetails(MOE, VALID_PUBLIC_ADDRESS_SOL_MAIN))
                .sorted()
                .toList();
        String expecedMessage = String.format(RetrievePublicAddressCommand.MESSAGE_RETRIEVE_PUBLIC_ADDRESS_SUCCESS,
            personsDetails.size(), String.join("\n", personsDetails));

        assertCommandSuccess(retrieveCommand, model, expecedMessage, model);
    }

    // EP: Different case label
    @Test
    public void execute_validArgsDifferentCaseLabel_success() {
        RetrievePublicAddressCommand retrieveCommand =
            new RetrievePublicAddressCommand(VALID_PUBLIC_ADDRESS_BTC_MAIN.getLabel().toUpperCase(), "");

        List<String> personsDetails =
            Stream.of(createPersonDetails(JOE, VALID_PUBLIC_ADDRESS_BTC_MAIN),
                    createPersonDetails(JOE, VALID_PUBLIC_ADDRESS_ETH_MAIN),
                    createPersonDetails(MOE, VALID_PUBLIC_ADDRESS_SOL_MAIN))
                .sorted()
                .toList();
        String expecedMessage = String.format(RetrievePublicAddressCommand.MESSAGE_RETRIEVE_PUBLIC_ADDRESS_SUCCESS,
            personsDetails.size(), String.join("\n", personsDetails));

        assertCommandSuccess(retrieveCommand, model, expecedMessage, model);
    }

    // EP: Partial name
    @Test
    public void execute_validArgsPartialName_success() {
        RetrievePublicAddressCommand retrieveCommand =
            new RetrievePublicAddressCommand(VALID_PUBLIC_ADDRESS_BTC_MAIN.getLabel(),
                JOE.getName().toString().substring(0, 5));

        List<String> personsDetails =
            Stream.of(createPersonDetails(JOE, VALID_PUBLIC_ADDRESS_BTC_MAIN),
                    createPersonDetails(JOE, VALID_PUBLIC_ADDRESS_ETH_MAIN))
                .sorted()
                .toList();
        String expecedMessage = String.format(RetrievePublicAddressCommand.MESSAGE_RETRIEVE_PUBLIC_ADDRESS_SUCCESS,
            personsDetails.size(), String.join("\n", personsDetails));

        assertCommandSuccess(retrieveCommand, model, expecedMessage, model);
    }

    // EP: Different case name
    @Test
    public void execute_validArgsDifferentCaseName_success() {
        RetrievePublicAddressCommand retrieveCommand =
            new RetrievePublicAddressCommand(VALID_PUBLIC_ADDRESS_BTC_MAIN.getLabel(),
                JOE.getName().toString().toUpperCase());

        List<String> personsDetails =
            Stream.of(createPersonDetails(JOE, VALID_PUBLIC_ADDRESS_BTC_MAIN),
                    createPersonDetails(JOE, VALID_PUBLIC_ADDRESS_ETH_MAIN))
                .sorted()
                .toList();
        String expecedMessage = String.format(RetrievePublicAddressCommand.MESSAGE_RETRIEVE_PUBLIC_ADDRESS_SUCCESS,
            personsDetails.size(), String.join("\n", personsDetails));

        assertCommandSuccess(retrieveCommand, model, expecedMessage, model);
    }

    // EP: Non-matching label
    @Test
    public void execute_validArgsNoMatchingLabel_success() {
        RetrievePublicAddressCommand retrieveCommand =
            new RetrievePublicAddressCommand("non-existent", "");

        List<String> personsDetails = Collections.emptyList();
        String expecedMessage = String.format(RetrievePublicAddressCommand.MESSAGE_RETRIEVE_PUBLIC_ADDRESS_SUCCESS,
            personsDetails.size(), String.join("\n", personsDetails));

        assertCommandSuccess(retrieveCommand, model, expecedMessage, model);
    }

    // EP: Non-matching name
    @Test
    public void execute_validArgsNoMatchingName_success() {
        RetrievePublicAddressCommand retrieveCommand =
            new RetrievePublicAddressCommand(VALID_PUBLIC_ADDRESS_BTC_MAIN.getLabel(), "non-existent");

        List<String> personsDetails = Collections.emptyList();
        String expecedMessage = String.format(RetrievePublicAddressCommand.MESSAGE_RETRIEVE_PUBLIC_ADDRESS_SUCCESS,
            personsDetails.size(), String.join("\n", personsDetails));

        assertCommandSuccess(retrieveCommand, model, expecedMessage, model);
    }

    // EP: Non-matching network
    @Test
    public void execute_validArgsNoMatchingNetwork_success() {
        RetrievePublicAddressCommand retrieveCommand =
            new RetrievePublicAddressCommand(VALID_PUBLIC_ADDRESS_BTC_MAIN.getLabel(),
                JOE.getName().toString(), Network.SOL);

        List<String> personsDetails = Collections.emptyList();
        String expecedMessage = String.format(RetrievePublicAddressCommand.MESSAGE_RETRIEVE_PUBLIC_ADDRESS_SUCCESS,
            personsDetails.size(), String.join("\n", personsDetails));

        assertCommandSuccess(retrieveCommand, model, expecedMessage, model);
    }

    // EP: All non-matching
    @Test
    public void execute_validArgsNoPublicAddresses_success() {
        RetrievePublicAddressCommand retrieveCommand =
            new RetrievePublicAddressCommand(VALID_PUBLIC_ADDRESS_BTC_MAIN.getLabel(), IDA.getName().toString());

        List<String> personsDetails = Collections.emptyList();
        String expecedMessage = String.format(RetrievePublicAddressCommand.MESSAGE_RETRIEVE_PUBLIC_ADDRESS_SUCCESS,
            personsDetails.size(), String.join("\n", personsDetails));

        assertCommandSuccess(retrieveCommand, model, expecedMessage, model);
    }

    // EP: Same object
    @Test
    public void equals_sameObject_returnsTrue() {
        RetrievePublicAddressCommand command = new RetrievePublicAddressCommand(
            "MyWallet", "John", Network.BTC);
        assertEquals(command, command);
    }

    // EP: Same values
    @Test
    public void equals_sameValues_returnsTrue() {
        RetrievePublicAddressCommand command1 = new RetrievePublicAddressCommand(
            "MyWallet", "John", Network.BTC);
        RetrievePublicAddressCommand command2 = new RetrievePublicAddressCommand(
            "MyWallet", "John", Network.BTC);
        assertEquals(command1, command2);
    }

    // EP: Different labels
    @Test
    public void equals_differentLabel_returnsFalse() {
        RetrievePublicAddressCommand command1 = new RetrievePublicAddressCommand(
            "MyWallet", "John", Network.BTC);
        RetrievePublicAddressCommand command2 = new RetrievePublicAddressCommand(
            "mywallet", "John", Network.BTC);
        assertNotEquals(command1, command2);
    }

    // EP: Different names casing
    @Test
    public void equals_differentName_returnsFalse() {
        RetrievePublicAddressCommand command1 = new RetrievePublicAddressCommand(
            "MyWallet", "John", Network.BTC);
        RetrievePublicAddressCommand command2 = new RetrievePublicAddressCommand(
            "MyWallet", "john", Network.BTC);
        assertNotEquals(command1, command2);
    }

    // EP: Different networks
    @Test
    public void equals_differentNetwork_returnsFalse() {
        RetrievePublicAddressCommand command1 = new RetrievePublicAddressCommand(
            "MyWallet", "John", Network.BTC);
        RetrievePublicAddressCommand command2 = new RetrievePublicAddressCommand(
            "MyWallet", "John", Network.ETH);
        assertNotEquals(command1, command2);
    }

    // EP: null
    @Test
    public void equals_nullObject_returnsFalse() {
        RetrievePublicAddressCommand command = new RetrievePublicAddressCommand(
            "MyWallet", "John", Network.BTC);
        assertNotEquals(null, command);
    }

    // EP: Different type
    @Test
    public void equals_differentClass_returnsFalse() {
        RetrievePublicAddressCommand command = new RetrievePublicAddressCommand(
            "MyWallet", "John", Network.BTC);
        assertNotEquals("Not a RetrievePublicAddressCommand", command);
    }

    @Test
    public void toString_returnsCorrectString() {
        RetrievePublicAddressCommand command = new RetrievePublicAddressCommand(
            "MyWallet", "John", Network.BTC);
        String expectedString = new ToStringBuilder(command)
            .add("label", "MyWallet")
            .add("name", "John")
            .add("network", Network.BTC)
            .toString();
        assertEquals(expectedString, command.toString());
    }

}
