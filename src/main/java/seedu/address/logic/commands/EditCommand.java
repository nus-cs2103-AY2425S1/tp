package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RESTAURANTS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.restaurant.Address;
import seedu.address.model.restaurant.Email;
import seedu.address.model.restaurant.Name;
import seedu.address.model.restaurant.Phone;
import seedu.address.model.restaurant.Rating;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing restaurant in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the restaurant identified "
            + "by the index number used in the displayed restaurant list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_RATING + "RATING] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_RESTAURANT_SUCCESS = "Edited Restaurant: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_RESTAURANT = "This restaurant already exists in the address book.";

    private final Index index;
    private final EditRestaurantDescriptor editRestaurantDescriptor;

    /**
     * @param index of the restaurant in the filtered restaurant list to edit
     * @param editRestaurantDescriptor details to edit the restaurant with
     */
    public EditCommand(Index index, EditRestaurantDescriptor editRestaurantDescriptor) {
        requireNonNull(index);
        requireNonNull(editRestaurantDescriptor);

        this.index = index;
        this.editRestaurantDescriptor = new EditRestaurantDescriptor(editRestaurantDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Restaurant> lastShownList = model.getFilteredRestaurantList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX);
        }

        Restaurant restaurantToEdit = lastShownList.get(index.getZeroBased());
        Restaurant editedRestaurant = createEditedRestaurant(restaurantToEdit, editRestaurantDescriptor);

        if (!restaurantToEdit.isSameRestaurant(editedRestaurant) && model.hasRestaurant(editedRestaurant)) {
            throw new CommandException(MESSAGE_DUPLICATE_RESTAURANT);
        }

        model.setRestaurant(restaurantToEdit, editedRestaurant);
        model.updateFilteredRestaurantList(PREDICATE_SHOW_ALL_RESTAURANTS);
        return new CommandResult(String.format(MESSAGE_EDIT_RESTAURANT_SUCCESS, Messages.format(editedRestaurant)));
    }

    /**
     * Creates and returns a {@code Restaurant} with the details of {@code restaurantToEdit}
     * edited with {@code editRestaurantDescriptor}.
     */
    private static Restaurant createEditedRestaurant(Restaurant restaurantToEdit,
                                                     EditRestaurantDescriptor editRestaurantDescriptor) {
        assert restaurantToEdit != null;

        Name updatedName = editRestaurantDescriptor.getName().orElse(restaurantToEdit.getName());
        Phone updatedPhone = editRestaurantDescriptor.getPhone().orElse(restaurantToEdit.getPhone());
        Email updatedEmail = editRestaurantDescriptor.getEmail().orElse(restaurantToEdit.getEmail());
        Address updatedAddress = editRestaurantDescriptor.getAddress().orElse(restaurantToEdit.getAddress());
        Rating updatedRating = editRestaurantDescriptor.getRating().orElse(restaurantToEdit.getRating());
        Set<Tag> updatedTags = editRestaurantDescriptor.getTags().orElse(restaurantToEdit.getTags());

        return new Restaurant(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedRating, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editRestaurantDescriptor.equals(otherEditCommand.editRestaurantDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editRestaurantDescriptor", editRestaurantDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the restaurant with. Each non-empty field value will replace the
     * corresponding field value of the restaurant.
     */
    public static class EditRestaurantDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Rating rating;
        private Set<Tag> tags;

        public EditRestaurantDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditRestaurantDescriptor(EditRestaurantDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setRating(toCopy.rating);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setRating(Rating rating) {
            this.rating = rating;
        }

        public Optional<Rating> getRating() {
            return Optional.ofNullable(rating);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditRestaurantDescriptor)) {
                return false;
            }

            EditRestaurantDescriptor otherEditRestaurantDescriptor = (EditRestaurantDescriptor) other;
            return Objects.equals(name, otherEditRestaurantDescriptor.name)
                    && Objects.equals(phone, otherEditRestaurantDescriptor.phone)
                    && Objects.equals(email, otherEditRestaurantDescriptor.email)
                    && Objects.equals(address, otherEditRestaurantDescriptor.address)
                    && Objects.equals(rating, otherEditRestaurantDescriptor.rating)
                    && Objects.equals(tags, otherEditRestaurantDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("rating", rating)
                    .add("tags", tags)
                    .toString();
        }
    }
}
