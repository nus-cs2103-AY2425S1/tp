package seedu.address.ui;

/**
 * Represents the level of detail of a card.
 * For people, the simplified level shows only the name, tag and phone number,
 * while the detailed level shows all details.
 * For events, the simplified level shows only the name, celebrity, date and venue,
 * while the detailed level shows all details.
 */
public enum CommandDetailChange {
    SIMPLIFIED, DETAILED, NONE;
}
