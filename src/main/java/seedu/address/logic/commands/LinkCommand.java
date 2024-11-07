package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.link.Link;
import seedu.address.model.owner.Owner;
import seedu.address.model.pet.Pet;

/**
 * Links between 2 {@code Linkable} entities. In the current implementation,
 * it only links from {@code Owner} to {@code Pet}.
 */
public class LinkCommand extends Command {
    public static final String COMMAND_WORD = "link";

    public static final String MESSAGE_USAGE =
        COMMAND_WORD + ": Links a owner to a pet. "
        + "Usage: " + COMMAND_WORD + " OWNER_INDEX "
        + "[" + PREFIX_TO + "PET_INDEX]...\n"
        + "Example: " + COMMAND_WORD + " o12 "
        + PREFIX_TO + "p1, "
        + COMMAND_WORD + " o13 "
        + PREFIX_TO + "p3 "
        + PREFIX_TO + "p2";

    public static final String MESSAGE_SUCCESS = "Linked %1$s pet(s) to %2$s: %3$s\n%4$s";
    public static final String MESSAGE_DUPLICATE_LINK =
        "This link already exists in the address book";
    public static final String MESSAGE_RETURN_TO_MAIN_MENU = "Please enter 'list' to return to the main menu";

    private final Index ownerIndex;
    private final Set<Index> petIndexes;

    /**
     * Creates a LinkCommand to link the specified {@code Linkable} entities.
     */
    public LinkCommand(Index ownerIndex, Set<Index> petIndexes) {
        requireAllNonNull(ownerIndex, petIndexes);
        if (petIndexes.size() == 0) {
            throw new IllegalArgumentException("At least one pet index must be provided");
        }
        this.ownerIndex = ownerIndex;
        this.petIndexes = petIndexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Get last shown lists
        List<Owner> ownerList = model.getFilteredOwnerList();
        List<Pet> petList = model.getFilteredPetList();

        if (ownerIndex.getZeroBased() >= ownerList.size()) {
            throw new CommandException(
              Messages.MESSAGE_INVALID_OWNER_DISPLAYED_INDEX
            );
        }

        Owner owner = ownerList.get(ownerIndex.getZeroBased());

        // Validate all links first
        List<Pet> validatedPetList = new ArrayList<>();
        Set<Link> validatedLinks = getValidatedLinks(model, owner, petList, validatedPetList);

        // Add all links
        validatedLinks.forEach(model::addLink);

        return new CommandResult(
          String.format(MESSAGE_SUCCESS, validatedLinks.size(), owner.getName(),
            String.join(", ", validatedPetList.stream().map(p -> p.getName().toString()).toList()),
            MESSAGE_RETURN_TO_MAIN_MENU
            )
        );
    }

    private Set<Link> getValidatedLinks(Model model, Owner owner, List<Pet> petList, List<Pet> validatedPetList)
            throws CommandException {
        validatedPetList.clear();

        Set<Link> links = new HashSet<>();
        Iterator<Index> petIndexIterator = petIndexes.iterator();
        while (petIndexIterator.hasNext()) {
            Index petIndex = petIndexIterator.next();
            if (petIndex.getZeroBased() >= petList.size()) {
                throw new CommandException(
                    Messages.MESSAGE_INVALID_PET_DISPLAYED_INDEX
                );
            }

            Pet pet = petList.get(petIndex.getZeroBased());
            Link link = new Link(owner, pet);
            validatedPetList.add(pet);

            if (model.hasLink(link)) {
                throw new CommandException(MESSAGE_DUPLICATE_LINK);
            }
            links.add(link);
        }
        return links;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LinkCommand)) {
            return false;
        }

        LinkCommand otherLinkCommand = (LinkCommand) other;
        return otherLinkCommand.ownerIndex.equals(ownerIndex)
            && otherLinkCommand.petIndexes.equals(this.petIndexes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
          .add("ownerIndex", ownerIndex.getOneBased())
          .add("petIndexes", Arrays.toString(petIndexes.stream().map(Index::getOneBased).toArray()))
          .toString();
    }
}
