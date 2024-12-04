package seedu.address.logic.commands.listingcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AREA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGION;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LISTINGS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandUtils;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listing.Address;
import seedu.address.model.listing.Area;
import seedu.address.model.listing.Listing;
import seedu.address.model.listing.Price;
import seedu.address.model.listing.Region;
import seedu.address.model.name.Name;
import seedu.address.model.person.Person;

/**
 * Edits the details of an existing listing in the system.
 */
public class EditListingCommand extends Command {

    public static final String COMMAND_WORD = "editlisting";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the listing identified "
            + "by the listing index number. Buyers cannot be edited using this command. "
            + "Use addlistingbuyers or removelistingbuyers to manage buyers.\n"
            + "Parameters: LISTING_INDEX "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PRICE + "PRICE] "
            + "[" + PREFIX_AREA + "AREA] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_REGION + "REGION]...\n"
            + "Example: " + COMMAND_WORD + " 2 "
            + PREFIX_PRICE + "450000 "
            + PREFIX_AREA + "1200";

    public static final String MESSAGE_EDIT_LISTING_SUCCESS = "Successfully edited listing: %1$s";
    public static final String MESSAGE_DUPLICATE_LISTING = "This listing name / address already exists in the system.";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index listingIndex;
    private final EditListingDescriptor editListingDescriptor;

    /**
     * @param listingIndex of the listing in the filtered listing list to edit
     * @param editListingDescriptor details to edit the listing with
     */
    public EditListingCommand(Index listingIndex, EditListingDescriptor editListingDescriptor) {
        requireNonNull(listingIndex);
        requireNonNull(editListingDescriptor);

        this.listingIndex = listingIndex;
        this.editListingDescriptor = new EditListingDescriptor(editListingDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Listing> lastShownListingList = model.getFilteredListingList();

        int zeroBasedListing = listingIndex.getZeroBased();
        CommandUtils.handleInvalidListingIndex(zeroBasedListing, lastShownListingList.size());
        Listing listingToEdit = lastShownListingList.get(zeroBasedListing);

        Optional<Index> sellerIndex = editListingDescriptor.getSellerIndex();

        Listing editedListing;

        List<Person> lastShownPersonList = model.getFilteredPersonList();

        // Gets the new seller if edited, or the original seller if not edited
        Person seller = getUpdatedSeller(sellerIndex, lastShownPersonList, listingToEdit);

        editedListing = createEditedListing(listingToEdit, editListingDescriptor, seller);

        boolean canEditListingWithoutDuplicates = model.canEditListing(listingToEdit, editedListing);

        if (isIdentifierChanged(listingToEdit, editedListing) && canEditListingWithoutDuplicates) {
            throw new CommandException(MESSAGE_DUPLICATE_LISTING);
        }

        model.setListing(listingToEdit, editedListing);
        model.updateFilteredListingList(PREDICATE_SHOW_ALL_LISTINGS);
        return new CommandResult(String.format(MESSAGE_EDIT_LISTING_SUCCESS, Messages.format(editedListing)));
    }

    /**
     * Creates and returns a {@code Listing} with the details of {@code listingToEdit}
     * edited with {@code editListingDescriptor}.
     */
    private static Listing createEditedListing(Listing listingToEdit, EditListingDescriptor editListingDescriptor,
                                               Person seller) {
        assert listingToEdit != null;

        Name updatedName = editListingDescriptor.getName().orElse(listingToEdit.getName());
        Price updatedPrice = editListingDescriptor.getPrice().orElse(listingToEdit.getPrice());
        Area updatedArea = editListingDescriptor.getArea().orElse(listingToEdit.getArea());
        Address updatedAddress = editListingDescriptor.getAddress().orElse(listingToEdit.getAddress());
        Region updatedRegion = editListingDescriptor.getRegion().orElse(listingToEdit.getRegion());

        return new Listing(updatedName, updatedAddress, updatedPrice, updatedArea, updatedRegion,
                seller, listingToEdit.getBuyers());
    }

    private Person getUpdatedSeller(Optional<Index> sellerIndex,
                                    List<Person> lastShownPersonList, Listing listingToEdit) throws CommandException {
        if (sellerIndex.isPresent()) {
            int zeroBasedPerson = sellerIndex.get().getZeroBased();
            CommandUtils.handleInvalidPersonIndex(zeroBasedPerson, lastShownPersonList.size());

            return lastShownPersonList.get(zeroBasedPerson);
        } else {
            return listingToEdit.getSeller();
        }
    }

    /**
     * Checks if the unique identifiers of a listing, specifically the name or address,
     * have been modified in the edited version.
     *
     * @param listingToEdit The original listing before edits.
     * @param editedListing The listing with potential edits.
     * @return {@code true} if either the name or address of {@code editedListing} differs
     *         from {@code listingToEdit}, indicating that the identifiers have changed.
     *         Returns {@code false} otherwise.
     */
    private static boolean isIdentifierChanged(Listing listingToEdit, Listing editedListing) {
        return !listingToEdit.getAddress().equals(editedListing.getAddress())
                || !listingToEdit.getName().equals(editedListing.getName());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditListingCommand otherEditCommand)) {
            return false;
        }

        boolean hasSameListingIndex = listingIndex.equals(otherEditCommand.listingIndex);
        boolean hasSameDescriptor = editListingDescriptor.equals(otherEditCommand.editListingDescriptor);

        return hasSameListingIndex && hasSameDescriptor;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("listingIndex", listingIndex)
                .add("editListingDescriptor", editListingDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the listing with. Each non-empty field value will replace the
     * corresponding field value of the listing.
     */
    public static class EditListingDescriptor {
        private Name name;
        private Price price;
        private Area area;
        private Address address;
        private Region region;
        private Index sellerIndex;

        public EditListingDescriptor() {}

        /**
         * Copy constructor.
         * Creates an {@code EditListingDescriptor} by copying the details from another descriptor.
         * Each field is copied from the provided {@code toCopy} descriptor.
         *
         * @param toCopy the {@code EditListingDescriptor} to copy from.
         */
        public EditListingDescriptor(EditListingDescriptor toCopy) {
            setName(toCopy.name);
            setPrice(toCopy.price);
            setArea(toCopy.area);
            setAddress(toCopy.address);
            setRegion(toCopy.region);
            setSellerIndex(toCopy.sellerIndex);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, price, area, address, region, sellerIndex);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPrice(Price price) {
            this.price = price;
        }

        public Optional<Price> getPrice() {
            return Optional.ofNullable(price);
        }

        public void setArea(Area area) {
            this.area = area;
        }

        public Optional<Area> getArea() {
            return Optional.ofNullable(area);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setRegion(Region region) {
            this.region = region;
        }

        public Optional<Region> getRegion() {
            return Optional.ofNullable(region);
        }
        public void setSellerIndex(Index sellerIndex) {
            this.sellerIndex = sellerIndex;
        }

        public Optional<Index> getSellerIndex() {
            return Optional.ofNullable(sellerIndex);
        }


        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditListingDescriptor)) {
                return false;
            }

            EditListingDescriptor otherDescriptor = (EditListingDescriptor) other;

            boolean hasSameName = Objects.equals(name, otherDescriptor.name);
            boolean hasSamePrice = Objects.equals(price, otherDescriptor.price);
            boolean hasSameArea = Objects.equals(area, otherDescriptor.area);
            boolean hasSameAddress = Objects.equals(address, otherDescriptor.address);
            boolean hasSameRegion = Objects.equals(region, otherDescriptor.region);
            boolean hasSameSellerIndex = Objects.equals(sellerIndex, otherDescriptor.sellerIndex);

            return hasSameName && hasSamePrice && hasSameArea && hasSameAddress && hasSameRegion && hasSameSellerIndex;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("price", price)
                    .add("area", area)
                    .add("address", address)
                    .add("region", region)
                    .add("seller", sellerIndex)
                    .toString();
        }
    }
}
