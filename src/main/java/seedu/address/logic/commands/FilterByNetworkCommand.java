package seedu.address.logic.commands;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.addresses.Network;
import seedu.address.model.person.Person;

import java.util.List;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_NETWORK;

public class FilterByNetworkCommand extends Command {

    public static final String COMMAND_WORD = "filter";
    public static final String MESSAGE_SUCCESS = "People with () public addresses shown.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters the address book and returns people who has public addresses of the specified network.\n"
            + "Parameters: NETWORK (Must be all CAPS) " + PREFIX_PUBLIC_ADDRESS_NETWORK
            + "NETWORK\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC";
    public static final String MESSAGE_FILTER_SUCCESS = "There are %1$d people with %2$s public addresses.\n"
            + "%3$s";

    private final Network specifiedNetwork;

    public FilterByNetworkCommand(Network specifiedNetwork) {
        requireAllNonNull(specifiedNetwork);
        this.specifiedNetwork = specifiedNetwork;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();

        List<Person> personsWithSpecifiedNetwork = lastShownList.stream()
                .filter(person -> person.getPublicAddressesComposition().getPublicAddresses()
                        .containsKey(specifiedNetwork))
                .toList();

        if (personsWithSpecifiedNetwork.isEmpty()) {
            throw new CommandException("No person with " + this.specifiedNetwork + " public address found.");
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < personsWithSpecifiedNetwork.size(); i++) {
            Person person = personsWithSpecifiedNetwork.get(i);
            String newPerson = (i + 1) + ". " + person.getName() + "\n";
            result.append(newPerson);
        }
        String personsListString = result.toString().trim();

        return new CommandResult(String.format(MESSAGE_FILTER_SUCCESS,
                personsWithSpecifiedNetwork.size(),
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
