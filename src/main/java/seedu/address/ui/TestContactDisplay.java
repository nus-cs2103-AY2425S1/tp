package seedu.address.ui;

import seedu.address.model.person.Person;

/**
 * A simplified version of ContactDisplay used for testing purposes.
 * It stores the contact details in fields instead of actual UI components.
 */
public class TestContactDisplay {

    private String name;
    private String category;
    private String phone;
    private String email;
    private String address;
    private String tags;

    public TestContactDisplay() {
        clear();
    }

    /**
     * Simulates updating the contact details display with the given person's details.
     *
     * @param person The person whose details are to be displayed.
     */
    public void updateContactDetails(Person person) {
        this.name = "Name: " + person.getName().fullName;
        this.category = "Category: " + person.getCategoryDisplayName();
        this.phone = "Phone: " + person.getPhone().value;
        this.email = "Email: " + person.getEmail().value;
        this.address = "Address: " + person.getAddress().value;
        this.tags = person.getTags().toString();
    }

    /**
     * Simulates clearing the contact details.
     */
    public void clear() {
        this.name = "Name:";
        this.category = "Category:";
        this.phone = "Phone:";
        this.email = "Email:";
        this.address = "Address:";
        this.tags = "";
    }

    /**
     * Simulates showing the help message.
     */
    public void showHelpDisplay() {
        this.name = ContactDisplay.CONDENSED_HELP_MESSAGE;
        this.category = null;
        this.phone = null;
        this.email = null;
        this.address = null;
        this.tags = "";
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getTags() {
        return tags;
    }
}
