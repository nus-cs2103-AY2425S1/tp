package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_LABEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_NETWORK;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.addresses.Network;
import seedu.address.model.addresses.PublicAddress;
import seedu.address.model.person.Person;

/**
 * Retrieves the public addresses of a contact.
 */
public class RetrievePublicAddressCommand extends Command {

    public static final String COMMAND_WORD = "retrievepa";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Retrieve public addresses by label.\n"
            + "Parameters: "
            + PREFIX_PUBLIC_ADDRESS_LABEL + "LABEL "
            + "[" + PREFIX_PUBLIC_ADDRESS_NETWORK + "NETWORK] "
            + "[" + PREFIX_NAME + "NAME]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PUBLIC_ADDRESS_LABEL + "MyWallet "
            + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC "
            + PREFIX_NAME + "John";

    public static final String MESSAGE_RETRIEVE_PUBLIC_ADDRESS_SUCCESS =
            "Retrieved %1$d public addresses:\n%2$s";

    public static final String MESSAGE_PERSON_FORMAT = "%1$s | %2$s | %3$s | %4$s";

    private final String label;
    private final String name;
    private final Network network;

    /**
     * Creates a RetrievePublicAddressCommand to retrieve the corresponding public addresses.
     *
     * @param label   label of the desired public addresses
     * @param name    name of owner desired public addresses
     * @param network network type of the desired public addresses
     */
    public RetrievePublicAddressCommand(String label, String name, Network network) {
        requireNonNull(label);
        requireNonNull(name);

        this.label = label;
        this.name = name;
        this.network = network;
    }

    /**
     * Creates a RetrievePublicAddressCommand for any network.
     *
     * @param label label of the desired public addresses
     * @param name  name of owner desired public addresses
     */
    public RetrievePublicAddressCommand(String label, String name) {
        this(label, name, null);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> matchingPersons = model.getAddressBook().getPersonList()
                .stream()
                .filter(person -> person.getName().toString().toLowerCase().contains(name.toLowerCase()))
                .toList();

        List<String> personDetails = matchingPersons
                .stream()
                .flatMap(person -> {
                    String personName = person.getName().toString();
                    Set<PublicAddress> desiredPublicAddresses =
                            network == null ? person.getPublicAddressesByLabel(label)
                                            : person.getPublicAddressesByLabelAndNetwork(label, network);

                    return desiredPublicAddresses.stream()
                            .map(publicAddress -> String.format(MESSAGE_PERSON_FORMAT, personName,
                                    publicAddress.getNetwork(), publicAddress.getLabel(),
                                    publicAddress.getPublicAddressString()));
                })
                .sorted()
                .toList();

        return new CommandResult(String.format(
                MESSAGE_RETRIEVE_PUBLIC_ADDRESS_SUCCESS, personDetails.size(), String.join("\n", personDetails)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RetrievePublicAddressCommand)) {
            return false;
        }

        RetrievePublicAddressCommand otherCommand = (RetrievePublicAddressCommand) other;
        return label.equals(otherCommand.label)
                && Objects.equals(network, otherCommand.network)
                && name.equals(otherCommand.name);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("label", label)
                .add("name", name)
                .add("network", network)
                .toString();
    }

}
