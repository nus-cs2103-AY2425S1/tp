package seedu.address.storage;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.person.Transaction;

/**
 * Json for {@code Transaction}.
 */
public class JsonAdaptedTransaction {
    private final String description;
    private final double amount;
    private final String otherParty;
    private final LocalDate date;

    /**
     * Constructs a {@code JsonAdaptedTransaction} with the given {@code description},
     * {@code amount}, {@code otherParty} and {@code date}.
     */
    @JsonCreator
    public JsonAdaptedTransaction(@JsonProperty("description") String description, @JsonProperty("amount") int amount,
                          @JsonProperty("otherParty") String otherParty, @JsonProperty("date") LocalDate date) {
        this.description = description;
        this.amount = amount;
        this.otherParty = otherParty;
        this.date = date;
    }

    /**
     * Converts a given {@code Transaction} into this class for Jackson use.
     */
    public JsonAdaptedTransaction(Transaction source) {
        this.description = source.getDescription();
        this.amount = source.getAmount();
        this.otherParty = source.getOtherParty();
        this.date = source.getDate();
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("amount")
    public double getAmount() {
        return amount;
    }

    @JsonProperty("otherParty")
    public String getOtherParty() {
        return otherParty;
    }

    @JsonProperty("date")
    public LocalDate getDate() {
        return date;
    }

    /**
     * Converts this Jackson-friendly adapted transaction object into the model's {@code Transaction} object.
     */
    public Transaction toModelType() {
        return new Transaction(description, amount, otherParty, date);
    }
}
