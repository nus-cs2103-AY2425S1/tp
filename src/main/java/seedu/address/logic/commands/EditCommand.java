package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFORMATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENTS_SUPPLIED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Email;
import seedu.address.model.person.Information;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Supplier;
import seedu.address.model.product.Ingredient;
import seedu.address.model.product.IngredientCatalogue;
import seedu.address.model.product.Ingredients;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.Remark;


/**
 * Edits the details of an existing person, customer, or supplier in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "editContact";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the details of the person, customer or supplier identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_INFORMATION + "INFORMATION (for Customer only)] "
            + "[" + PREFIX_INGREDIENTS_SUPPLIED + "INGREDIENTS SUPPLIED (for Supplier only)] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_INVALID_INFORMATION_EDIT =
            "Cannot edit the information field for a non-customer.";
    public static final String MESSAGE_INVALID_INGREDIENTS_EDIT =
            "Cannot edit the ingredients supplied field for a non-supplier.";
    public static final String MESSAGE_INGREDIENT_NOT_FOUND =
            "Ingredient '%s' not found in the catalogue. Please add it using the addIngredient command.";


    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        // Check if the information field is being edited for a non-customer
        if (!(personToEdit instanceof Customer) && editPersonDescriptor.getInformation().isPresent()) {
            throw new CommandException(MESSAGE_INVALID_INFORMATION_EDIT);
        }

        // Check if the ingredients supplied field is being edited for a non-supplier
        if (!(personToEdit instanceof Supplier) && editPersonDescriptor.getIngredientsSupplied().isPresent()) {
            throw new CommandException(MESSAGE_INVALID_INGREDIENTS_EDIT);
        }
        // Retrieve the IngredientCatalogue from the model
        IngredientCatalogue ingredientCatalogue = model.getIngredientCatalogue();
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor, ingredientCatalogue);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor,
                                             IngredientCatalogue catalogue) throws CommandException {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Remark updatedRemark = personToEdit.getRemark(); // Edit command does not allow editing remarks
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        if (personToEdit instanceof Customer) {
            Information updatedInformation = editPersonDescriptor.getInformation()
                    .orElse(((Customer) personToEdit)
                    .getInformation());
            return new Customer(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedInformation,
                    updatedRemark, updatedTags);
        } else if (personToEdit instanceof Supplier) {
            Ingredients updatedIngredients = getUpdatedIngredients(editPersonDescriptor,
                    (Supplier) personToEdit, catalogue);
            return new Supplier(updatedName, updatedPhone, updatedEmail, updatedAddress,
                    updatedIngredients, updatedRemark, updatedTags);
        }

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedRemark, updatedTags);
    }
    /**
     * Retrieves and updates the ingredients supplied based on the provided ingredient names.
     */
    private static Ingredients getUpdatedIngredients(EditPersonDescriptor editPersonDescriptor, Supplier supplier,
                                                     IngredientCatalogue catalogue) throws CommandException {
        if (editPersonDescriptor.getIngredientsSupplied().isEmpty()) {
            return supplier.getIngredientsSupplied();
        }

        List<String> ingredientNames = editPersonDescriptor.getIngredientsSupplied().get().getIngredientNames();
        List<Ingredient> ingredientList = new ArrayList<>();

        for (String name : ingredientNames) {
            try {
                Ingredient ingredient = catalogue.getIngredientByName(name);
                ingredientList.add(ingredient);
            } catch (NoSuchElementException e) {
                throw new CommandException(String.format(MESSAGE_INGREDIENT_NOT_FOUND, name));
            }
        }

        return new Ingredients(ingredientList);
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
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Information information; // For Customer
        private Ingredients ingredientsSupplied; // For Supplier
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setInformation(toCopy.information);
            setIngredientsSupplied(toCopy.ingredientsSupplied);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, information, ingredientsSupplied, tags);
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
        public void setInformation(Information information) {
            this.information = information;
        }

        public Optional<Information> getInformation() {
            return Optional.ofNullable(information);
        }

        public void setIngredientsSupplied(Ingredients ingredientsSupplied) {
            this.ingredientsSupplied = ingredientsSupplied;
        }

        public Optional<Ingredients> getIngredientsSupplied() {
            return Optional.ofNullable(ingredientsSupplied);
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
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherDescriptor.name)
                    && Objects.equals(phone, otherDescriptor.phone)
                    && Objects.equals(email, otherDescriptor.email)
                    && Objects.equals(address, otherDescriptor.address)
                    && Objects.equals(information, otherDescriptor.information)
                    && Objects.equals(ingredientsSupplied, otherDescriptor.ingredientsSupplied)
                    && Objects.equals(tags, otherDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("tags", tags)
                    .toString();
        }
    }
}
