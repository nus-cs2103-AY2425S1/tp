package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.BTC_MAIN_ADDRESS;
import static seedu.address.testutil.TypicalPersons.BTC_MAIN_ADDRESS_ALT;
import static seedu.address.testutil.TypicalPersons.ETH_MAIN_ADDRESS;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.JOE;
import static seedu.address.testutil.TypicalPersons.MOE;
import static seedu.address.testutil.TypicalPersons.SOL_MAIN_ADDRESS;

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

    @Test
    public void execute_validArgsAnyNetworkAnyName_success() {
        RetrievePublicAddressCommand retrieveCommand =
                new RetrievePublicAddressCommand(BTC_MAIN_ADDRESS.getLabel(), "");

        List<String> personsDetails =
                Stream.of(createPersonDetails(JOE, BTC_MAIN_ADDRESS), createPersonDetails(JOE, ETH_MAIN_ADDRESS),
                                createPersonDetails(MOE, BTC_MAIN_ADDRESS_ALT),
                                createPersonDetails(MOE, SOL_MAIN_ADDRESS))
                        .sorted()
                        .toList();
        String expecedMessage = String.format(RetrievePublicAddressCommand.MESSAGE_RETRIEVE_PUBLIC_ADDRESS_SUCCESS,
                personsDetails.size(), String.join("\n", personsDetails));

        assertCommandSuccess(retrieveCommand, model, expecedMessage, model);
    }

    @Test
    public void execute_validArgsAnyNetworkSpecifiedName_success() {
        RetrievePublicAddressCommand retrieveCommand =
                new RetrievePublicAddressCommand(BTC_MAIN_ADDRESS.getLabel(), JOE.getName().toString());

        List<String> personsDetails =
                Stream.of(createPersonDetails(JOE, BTC_MAIN_ADDRESS), createPersonDetails(JOE, ETH_MAIN_ADDRESS))
                        .sorted()
                        .toList();
        String expecedMessage = String.format(RetrievePublicAddressCommand.MESSAGE_RETRIEVE_PUBLIC_ADDRESS_SUCCESS,
                personsDetails.size(), String.join("\n", personsDetails));

        assertCommandSuccess(retrieveCommand, model, expecedMessage, model);
    }

    @Test
    public void execute_validArgsSpecifiedNetworkAnyName_success() {
        RetrievePublicAddressCommand retrieveCommand =
                new RetrievePublicAddressCommand(BTC_MAIN_ADDRESS.getLabel(), "", Network.BTC);

        List<String> personsDetails =
                Stream.of(createPersonDetails(JOE, BTC_MAIN_ADDRESS), createPersonDetails(MOE, BTC_MAIN_ADDRESS_ALT))
                        .sorted()
                        .toList();
        String expecedMessage = String.format(RetrievePublicAddressCommand.MESSAGE_RETRIEVE_PUBLIC_ADDRESS_SUCCESS,
                personsDetails.size(), String.join("\n", personsDetails));

        assertCommandSuccess(retrieveCommand, model, expecedMessage, model);
    }

    @Test
    public void execute_validArgsSpecifiedNetworkAndName_success() {
        RetrievePublicAddressCommand retrieveCommand =
                new RetrievePublicAddressCommand(BTC_MAIN_ADDRESS.getLabel(), JOE.getName().toString(), Network.BTC);

        List<String> personsDetails =
                Stream.of(createPersonDetails(JOE, BTC_MAIN_ADDRESS)).toList();
        String expecedMessage = String.format(RetrievePublicAddressCommand.MESSAGE_RETRIEVE_PUBLIC_ADDRESS_SUCCESS,
                personsDetails.size(), String.join("\n", personsDetails));

        assertCommandSuccess(retrieveCommand, model, expecedMessage, model);
    }

    @Test
    public void execute_validArgsPartialLabel_success() {
        RetrievePublicAddressCommand retrieveCommand =
                new RetrievePublicAddressCommand(BTC_MAIN_ADDRESS.getLabel().substring(0, 5), "");

        List<String> personsDetails =
                Stream.of(createPersonDetails(JOE, BTC_MAIN_ADDRESS), createPersonDetails(JOE, ETH_MAIN_ADDRESS),
                                createPersonDetails(MOE, BTC_MAIN_ADDRESS_ALT),
                                createPersonDetails(MOE, SOL_MAIN_ADDRESS))
                        .sorted()
                        .toList();
        String expecedMessage = String.format(RetrievePublicAddressCommand.MESSAGE_RETRIEVE_PUBLIC_ADDRESS_SUCCESS,
                personsDetails.size(), String.join("\n", personsDetails));

        assertCommandSuccess(retrieveCommand, model, expecedMessage, model);
    }

    @Test
    public void execute_validArgsDifferentCaseLabel_success() {
        RetrievePublicAddressCommand retrieveCommand =
                new RetrievePublicAddressCommand(BTC_MAIN_ADDRESS.getLabel().toUpperCase(), "");

        List<String> personsDetails =
                Stream.of(createPersonDetails(JOE, BTC_MAIN_ADDRESS), createPersonDetails(JOE, ETH_MAIN_ADDRESS),
                                createPersonDetails(MOE, BTC_MAIN_ADDRESS_ALT),
                                createPersonDetails(MOE, SOL_MAIN_ADDRESS))
                        .sorted()
                        .toList();
        String expecedMessage = String.format(RetrievePublicAddressCommand.MESSAGE_RETRIEVE_PUBLIC_ADDRESS_SUCCESS,
                personsDetails.size(), String.join("\n", personsDetails));

        assertCommandSuccess(retrieveCommand, model, expecedMessage, model);
    }

    @Test
    public void execute_validArgsPartialName_success() {
        RetrievePublicAddressCommand retrieveCommand =
                new RetrievePublicAddressCommand(BTC_MAIN_ADDRESS.getLabel(),
                        JOE.getName().toString().substring(0, 5));

        List<String> personsDetails =
                Stream.of(createPersonDetails(JOE, BTC_MAIN_ADDRESS), createPersonDetails(JOE, ETH_MAIN_ADDRESS))
                        .sorted()
                        .toList();
        String expecedMessage = String.format(RetrievePublicAddressCommand.MESSAGE_RETRIEVE_PUBLIC_ADDRESS_SUCCESS,
                personsDetails.size(), String.join("\n", personsDetails));

        assertCommandSuccess(retrieveCommand, model, expecedMessage, model);
    }

    @Test
    public void execute_validArgsDifferentCaseName_success() {
        RetrievePublicAddressCommand retrieveCommand =
                new RetrievePublicAddressCommand(BTC_MAIN_ADDRESS.getLabel(), JOE.getName().toString().toUpperCase());

        List<String> personsDetails =
                Stream.of(createPersonDetails(JOE, BTC_MAIN_ADDRESS), createPersonDetails(JOE, ETH_MAIN_ADDRESS))
                        .sorted()
                        .toList();
        String expecedMessage = String.format(RetrievePublicAddressCommand.MESSAGE_RETRIEVE_PUBLIC_ADDRESS_SUCCESS,
                personsDetails.size(), String.join("\n", personsDetails));

        assertCommandSuccess(retrieveCommand, model, expecedMessage, model);
    }

    @Test
    public void execute_validArgsNoMatchingLabel_success() {
        RetrievePublicAddressCommand retrieveCommand =
                new RetrievePublicAddressCommand("non-existent", "");

        List<String> personsDetails = Collections.emptyList();
        String expecedMessage = String.format(RetrievePublicAddressCommand.MESSAGE_RETRIEVE_PUBLIC_ADDRESS_SUCCESS,
                personsDetails.size(), String.join("\n", personsDetails));

        assertCommandSuccess(retrieveCommand, model, expecedMessage, model);
    }

    @Test
    public void execute_validArgsNoMatchingName_success() {
        RetrievePublicAddressCommand retrieveCommand =
                new RetrievePublicAddressCommand(BTC_MAIN_ADDRESS.getLabel(), "non-existent");

        List<String> personsDetails = Collections.emptyList();
        String expecedMessage = String.format(RetrievePublicAddressCommand.MESSAGE_RETRIEVE_PUBLIC_ADDRESS_SUCCESS,
                personsDetails.size(), String.join("\n", personsDetails));

        assertCommandSuccess(retrieveCommand, model, expecedMessage, model);
    }

    @Test
    public void execute_validArgsNoMatchingNetwork_success() {
        RetrievePublicAddressCommand retrieveCommand =
                new RetrievePublicAddressCommand(BTC_MAIN_ADDRESS.getLabel(), JOE.getName().toString(), Network.SOL);

        List<String> personsDetails = Collections.emptyList();
        String expecedMessage = String.format(RetrievePublicAddressCommand.MESSAGE_RETRIEVE_PUBLIC_ADDRESS_SUCCESS,
                personsDetails.size(), String.join("\n", personsDetails));

        assertCommandSuccess(retrieveCommand, model, expecedMessage, model);
    }

    @Test
    public void execute_validArgsNoPublicAddresses_success() {
        RetrievePublicAddressCommand retrieveCommand =
                new RetrievePublicAddressCommand(BTC_MAIN_ADDRESS.getLabel(), IDA.getName().toString());

        List<String> personsDetails = Collections.emptyList();
        String expecedMessage = String.format(RetrievePublicAddressCommand.MESSAGE_RETRIEVE_PUBLIC_ADDRESS_SUCCESS,
                personsDetails.size(), String.join("\n", personsDetails));

        assertCommandSuccess(retrieveCommand, model, expecedMessage, model);
    }


    @Test
    public void equals_sameObject_returnsTrue() {
        RetrievePublicAddressCommand command = new RetrievePublicAddressCommand("MyWallet", "John", Network.BTC);
        assertTrue(command.equals(command));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        RetrievePublicAddressCommand command1 = new RetrievePublicAddressCommand("MyWallet", "John", Network.BTC);
        RetrievePublicAddressCommand command2 = new RetrievePublicAddressCommand("MyWallet", "John", Network.BTC);
        assertTrue(command1.equals(command2));
    }

    @Test
    public void equals_differentLabel_returnsFalse() {
        RetrievePublicAddressCommand command1 = new RetrievePublicAddressCommand("MyWallet", "John", Network.BTC);
        RetrievePublicAddressCommand command2 = new RetrievePublicAddressCommand("mywallet", "John", Network.BTC);
        assertFalse(command1.equals(command2));
    }

    @Test
    public void equals_differentName_returnsFalse() {
        RetrievePublicAddressCommand command1 = new RetrievePublicAddressCommand("MyWallet", "John", Network.BTC);
        RetrievePublicAddressCommand command2 = new RetrievePublicAddressCommand("MyWallet", "john", Network.BTC);
        assertFalse(command1.equals(command2));
    }

    @Test
    public void equals_differentNetwork_returnsFalse() {
        RetrievePublicAddressCommand command1 = new RetrievePublicAddressCommand("MyWallet", "John", Network.BTC);
        RetrievePublicAddressCommand command2 = new RetrievePublicAddressCommand("MyWallet", "John", Network.ETH);
        assertFalse(command1.equals(command2));
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        RetrievePublicAddressCommand command = new RetrievePublicAddressCommand("MyWallet", "John", Network.BTC);
        assertFalse(command.equals(null));
    }

    @Test
    public void equals_differentClass_returnsFalse() {
        RetrievePublicAddressCommand command = new RetrievePublicAddressCommand("MyWallet", "John", Network.BTC);
        assertFalse(command.equals("Not a RetrievePublicAddressCommand"));
    }

    @Test
    public void toString_returnsCorrectString() {
        RetrievePublicAddressCommand command = new RetrievePublicAddressCommand("MyWallet", "John", Network.BTC);
        String expectedString = new ToStringBuilder(command)
                .add("label", "MyWallet")
                .add("name", "John")
                .add("network", Network.BTC)
                .toString();
        assertEquals(expectedString, command.toString());
    }

}
