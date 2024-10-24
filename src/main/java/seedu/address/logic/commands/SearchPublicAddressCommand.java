package seedu.address.logic.commands;


import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.StringUtil.INDENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS;

import java.util.List;
import java.util.Map;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.addresses.Network;
import seedu.address.model.addresses.PublicAddress;
import seedu.address.model.person.Person;


/**
 * Changes the searchPublicAddress of an existing person in the address book.
 */
public class SearchPublicAddressCommand extends Command {

    public static final String COMMAND_WORD = "searchPublicAddress";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Searches for a public address and returns the user, network and tag "
            + "throughout all the public addresses of all networks in the address book.\n"
            + "Parameters: PUBLIC_ADDRESS (must be a string) " + PREFIX_PUBLIC_ADDRESS
            + "PUBLIC_ADDRESS\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PUBLIC_ADDRESS + "0x28f91d6e72eaf4372892e6c6e45dc41b574163e9fcdf94f4997958b46d772fa2";

    public static final String MESSAGE_SEARCH_PUBLIC_ADDRESS_SUCCESS = "Successfully found Persons with public "
            + "address inputted:\n%1$s";
    public static final String MESSAGE_SEARCH_PUBLIC_ADDRESS_FAIL = "Can't find any Person with public address of"
            + " inputted:\n%1$s";
    public static final String MESSAGE_SEARCH_PUBLIC_ADDRESS_SUBSTRING_SUCCESS = "Successfully found Persons with "
            + "public "
            + "address containing the substring inputted:\n%1$s";
    public static final String MESSAGE_SEARCH_PUBLIC_ADDRESS_SUBSTRING_FAIL =
            "Can't find any Person with public address of"
                    + " containing the "
                    + "public substring inputted:\n%1$s";


    public static final String MESSAGE_ARGUMENTS = "Public Address: %1$s";


    private final String publicAddressString;

    /**
     * @param publicAddressString of the person to be updated to
     */
    public SearchPublicAddressCommand(String publicAddressString) {
        requireAllNonNull(publicAddressString);
        this.publicAddressString = publicAddressString;

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();

        List<Person> personsWithPublicAddressMatch = lastShownList.stream()
                .filter(person -> person.hasPublicAddressStringAmongAllNetworks(publicAddressString))
                .toList();


        if (personsWithPublicAddressMatch.isEmpty()) {
            throw new CommandException("No person with the public address found.");
        }

        return new CommandResult(generateSuccessMessage(personsWithPublicAddressMatch));
    }


    /**
     * Generates a command execution success message based on whether
     * the remark is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(List<Person> personsWithPublicAddressMatch) {
        String message = !personsWithPublicAddressMatch.isEmpty() ? MESSAGE_SEARCH_PUBLIC_ADDRESS_SUCCESS
                : MESSAGE_SEARCH_PUBLIC_ADDRESS_FAIL;
        // parses peron object and returns index+name+full public address of the person+label

        String personsDetails = personsWithPublicAddressMatch.stream()
                .map(person -> person.getName() + "\n" + INDENT + generateStringForPublicAddressesForPersonMap(person,
                        publicAddressString))
                .reduce((a, b) -> a + "\n" + b)
                .orElse("");
        return String.format(message, personsDetails);
    }

    private String generateStringForPublicAddressesForPersonSet(Person person, String publicAddressString) {
        Set<PublicAddress> setOfPublicAddresses =
                person.getPublicAddressObjectByPublicAddressStringMap(publicAddressString);
        return setOfPublicAddresses.stream().map(publicAddress -> publicAddress.getNetwork() + "\n" + INDENT
                        + INDENT + publicAddress.getLabel() + ": " + publicAddress.getPublicAddressString())
                .reduce((a, b) -> a + "\n" + b)
                .orElse("");
    }

    private String generateStringForPublicAddressesForPersonMap(Person person, String publicAddressString) {
        Map<Network, Set<PublicAddress>> mapOfPublicAddresses =
                person.getPublicAddressObjectByPublicAddressMap(publicAddressString);
        return mapOfPublicAddresses.entrySet().stream().map(entry -> entry.getKey() + "\n" + INDENT
                + INDENT + entry.getValue().stream().map(publicAddress -> publicAddress.getLabel() + ": "
                        + publicAddress.getPublicAddressString())
                .reduce((a, b) -> a + "\n" + b).orElse("")).reduce((a, b) -> a + "\n" + b).orElse("");

    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SearchPublicAddressCommand e)) {
            return false;
        }

        return publicAddressString.equals(e.publicAddressString);
    }

}
