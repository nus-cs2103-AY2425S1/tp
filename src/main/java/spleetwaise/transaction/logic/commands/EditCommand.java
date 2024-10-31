package spleetwaise.transaction.logic.commands;

import static java.util.Objects.requireNonNull;
import static spleetwaise.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_DATE;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import spleetwaise.address.commons.core.index.Index;
import spleetwaise.address.commons.util.CollectionUtil;
import spleetwaise.address.commons.util.ToStringBuilder;
import spleetwaise.address.model.person.Person;
import spleetwaise.commons.logic.commands.Command;
import spleetwaise.commons.logic.commands.CommandResult;
import spleetwaise.commons.logic.commands.exceptions.CommandException;
import spleetwaise.commons.model.CommonModel;
import spleetwaise.transaction.logic.Messages;
import spleetwaise.transaction.model.TransactionBookModel;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Category;
import spleetwaise.transaction.model.transaction.Date;
import spleetwaise.transaction.model.transaction.Description;
import spleetwaise.transaction.model.transaction.Transaction;

/**
 * Edits the details of an existing transaction in the transaction book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "editTxn";

    /**
     * The message usage string that explains how to use this command.
     */
    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Edit a transaction.\n"
                    + "Parameters: INDEX (must be a positive integer) "
                    + "[" + PREFIX_PHONE + "CONTACT] "
                    + "[" + PREFIX_AMOUNT + "AMOUNT] "
                    + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
                    + "[" + PREFIX_DATE + "DATE] "
                    + "[" + PREFIX_CATEGORY + "FOOD]...\n"
                    + "Example: " + COMMAND_WORD + " 1 "
                    + PREFIX_PHONE + "88888888 "
                    + PREFIX_AMOUNT + "10.00 "
                    + PREFIX_DESCRIPTION + "Paid John for lunch "
                    + PREFIX_DATE + "23012024 "
                    + PREFIX_CATEGORY + "FOOD "
                    + PREFIX_CATEGORY + "DRINK ";


    public static final String MESSAGE_EDIT_TXN_SUCCESS = "Edited Transaction: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TXN = "This transaction already exists in the transaction book.";

    private final Index index;
    private final EditTransactionDescriptor editTransactionDescriptor;

    /**
     * @param index                     of the transaction in the filtered txn list to edit
     * @param editTransactionDescriptor details to edit the txn with
     */
    public EditCommand(Index index, EditTransactionDescriptor editTransactionDescriptor) {
        requireNonNull(index);
        requireNonNull(editTransactionDescriptor);

        this.index = index;
        this.editTransactionDescriptor = editTransactionDescriptor;
    }

    /**
     * Creates and returns a {@code Transaction} with the details of {@code transctionToEdit} edited with
     * {@code editTransactionDescriptor}.
     */
    private static Transaction createEditedTransaction(
            Transaction txnToEdit,
            EditTransactionDescriptor editTransactionDescriptor
    ) {
        requireNonNull(txnToEdit);

        String id = editTransactionDescriptor.getId().orElse(txnToEdit.getId());
        Person person = editTransactionDescriptor.getPerson().orElse(txnToEdit.getPerson());
        Amount amount = editTransactionDescriptor.getAmount().orElse(txnToEdit.getAmount());
        Description description = editTransactionDescriptor.getDescription().orElse(txnToEdit.getDescription());
        Date date = editTransactionDescriptor.getDate().orElse(txnToEdit.getDate());
        Set<Category> categories = editTransactionDescriptor.getCategories().orElse(txnToEdit.getCategories());

        return new Transaction(id, person, amount, description, date, categories);
    }

    public EditTransactionDescriptor getDescriptor() {
        return editTransactionDescriptor;
    }

    @Override
    public CommandResult execute() throws CommandException {
        CommonModel model = CommonModel.getInstance();
        List<Transaction> lastShownList = model.getFilteredTransactionList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
        }

        Transaction txnToEdit = lastShownList.get(index.getZeroBased());
        Transaction editedTxn = createEditedTransaction(txnToEdit, editTransactionDescriptor);

        if (!txnToEdit.equals(editedTxn) && model.hasTransaction(editedTxn)) {
            throw new CommandException(MESSAGE_DUPLICATE_TXN);
        }

        model.setTransaction(txnToEdit, editedTxn);
        model.updateFilteredTransactionList(TransactionBookModel.PREDICATE_SHOW_ALL_TXNS);
        return new CommandResult(String.format(MESSAGE_EDIT_TXN_SUCCESS, editedTxn));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditCommand otherEditCommand)) {
            return false;
        }

        return index.equals(otherEditCommand.index)
                && editTransactionDescriptor.equals(otherEditCommand.editTransactionDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("index", index).add(
                "editTransactionDescriptor",
                editTransactionDescriptor
        ).toString();
    }

    /**
     * Represents the data of a transaction that can be edited.
     */
    public static class EditTransactionDescriptor {
        private String id;
        private Person person;
        private Amount amount;
        private Description description;
        private Date date;
        private Set<Category> categories;

        public EditTransactionDescriptor() {
        }

        /**
         * Copy constructor. A defensive copy of {@code categories} is used internally.
         */
        public EditTransactionDescriptor(EditTransactionDescriptor toCopy) {
            setId(toCopy.id);
            setPerson(toCopy.person);
            setAmount(toCopy.amount);
            setDescription(toCopy.description);
            setDate(toCopy.date);
            if (toCopy.categories != null) {
                setCategories(new HashSet<>(toCopy.categories));
            }
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(person, amount, description, date, categories);
        }

        public Optional<String> getId() {
            return Optional.ofNullable(id);
        }

        public void setId(String id) {
            this.id = id;
        }

        public Optional<Person> getPerson() {
            return Optional.ofNullable(person);
        }

        public void setPerson(Person person) {
            this.person = person;
        }

        public Optional<Amount> getAmount() {
            return Optional.ofNullable(amount);
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        /**
         * Returns an unmodifiable category set, which throws {@code UnsupportedOperationException} if modification is
         * attempted. Returns {@code Optional#empty()} if {@code categories} is null.
         */
        public Optional<Set<Category>> getCategories() {
            return (categories != null) ? Optional.of(Collections.unmodifiableSet(categories)) : Optional.empty();
        }

        public void setCategories(Set<Category> categories) {
            this.categories = new HashSet<>(categories);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTransactionDescriptor otherEditTransactionDescriptor)) {
                return false;
            }

            return Objects.equals(id, otherEditTransactionDescriptor.id)
                    && Objects.equals(person, otherEditTransactionDescriptor.person)
                    && Objects.equals(amount, otherEditTransactionDescriptor.amount)
                    && Objects.equals(description, otherEditTransactionDescriptor.description)
                    && Objects.equals(date, otherEditTransactionDescriptor.date)
                    && Objects.equals(categories, otherEditTransactionDescriptor.categories);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("id", id)
                    .add("person", person)
                    .add("amount", amount)
                    .add("description", description)
                    .add("date", date)
                    .add("categories", categories)
                    .toString();
        }
    }


}
