package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_NETWORK;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.addresses.Network;
import seedu.address.model.person.Person;


/**
 * A command to filter the address book based on the specified network.
 */
public class FilterByNetworkCommand extends Command {

    public static final String COMMAND_WORD = "filter";
    public static final String MESSAGE_SUCCESS = "People with () public addresses shown.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters the address book and returns people who has public addresses of the specified network.\n"
            + "Parameters: NETWORK (" + PREFIX_PUBLIC_ADDRESS_NETWORK
            + "NETWORK)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC";
    public static final String MESSAGE_FILTER_SUCCESS = "There are %1$d people with %2$s public addresses.\n"
            + "%3$s";
    public static final String MESSAGE_FILTER_FAIL = "No person with %1$s public address found.";

    private final Network specifiedNetwork;

    /**
     * Creates a new `FilterByNetworkCommand` object.
     *
     * @param specifiedNetwork The network to filter by.
     * @throws NullPointerException If `specifiedNetwork` is null.
     */
    public FilterByNetworkCommand(Network specifiedNetwork) {
        requireAllNonNull(specifiedNetwork);
        this.specifiedNetwork = specifiedNetwork;
    }

    /**
     * Executes the filter command on the given address book model.
     *
     * @param model The address book model.
     * @return A `CommandResult` object containing the result of the filtering operation.
     * @throws CommandException If no person with the specified network is found.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();

        List<Person> personsWithSpecifiedNetwork = lastShownList.stream()
                .map(person -> person.getPublicAddressesComposition()
                        .getPublicAddresses()
                        .containsKey(specifiedNetwork)
                        ? person
                        : null)
                .toList();

        int numOfPersonWithSpecifiedNetwork = personsWithSpecifiedNetwork.stream()
                .filter(Objects::nonNull)
                .toList()
                .size();

        if (personsWithSpecifiedNetwork.stream().allMatch(Objects::isNull)) {
            throw new CommandException(String.format(MESSAGE_FILTER_FAIL, this.specifiedNetwork));
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < personsWithSpecifiedNetwork.size(); i++) {
            Person person = personsWithSpecifiedNetwork.get(i);
            if (person != null) {
                String newPerson = (i + 1) + ". " + person.getName() + "\n";
                result.append(newPerson);
            }
        }
        String personsListString = result.toString().trim();

        return new CommandResult(String.format(MESSAGE_FILTER_SUCCESS,
                numOfPersonWithSpecifiedNetwork,
                this.specifiedNetwork,
                personsListString));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterByNetworkCommand e)) {
            return false;
        }

        return specifiedNetwork.equals(e.specifiedNetwork);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("network", specifiedNetwork)
                .toString();
    }

}
