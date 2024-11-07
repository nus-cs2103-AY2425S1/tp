package seedu.address.ui;

import seedu.address.model.person.Person;

/**
 * Manages content generation for a {@code Person}'s detailed view,
 * including strings for fields, templates, and button labels.
 */
public class PersonDetailedViewContentManager {
    private final Person person;

    /**
     * Constructs a {@code PersonDetailedViewContentManager} with the specified {@code Person}.
     * @param person The person whose details are displayed.
     */
    public PersonDetailedViewContentManager(Person person) {
        this.person = person;
    }

    /**
     * Gets the full name of the person.
     * @return A formatted string representing the person's full name.
     */
    public String getName() {
        return person.getName().fullName;
    }

    /**
     * Gets the formatted phone number.
     * @return A formatted string representing the person's phone number.
     */
    public String getPhone() {
        return "+65 " + person.getPhone().value;
    }

    /**
     * Gets the formatted address.
     * @return A formatted string representing the person's address.
     */
    public String getAddress() {
        return "Address: " + person.getAddress().value;
    }

    /**
     * Gets the formatted birthday.
     * @return A formatted string representing the person's birthday.
     */
    public String getBirthday() {
        return "Birthday: " + person.getBirthday().value;
    }

    /**
     * Gets the age of the person as a string.
     * @return A formatted string representing the person's age.
     */
    public String getAge() {
        return "Age: " + person.getAge().value;
    }

    /**
     * Gets the email address.
     * @return A string representing the person's email address.
     */
    public String getEmail() {
        return person.getEmail().value;
    }

    /**
     * Gets the formatted paid status.
     * @return A formatted string representing the person's payment status.
     */
    public String getHasPaidStatus() {
        return "Paid status: " + (person.getHasPaid() ? "Paid" : "Not Paid");
    }

    /**
     * Gets the formatted policy renewal frequency.
     * @return A formatted string representing the person's policy renewal frequency.
     */
    public String getFrequency() {
        return "Policy Renewal Frequency: " + person.getFrequency().value + " month(s)";
    }

    /**
     * Returns the label text for the young adult template button.
     * @return The label text for the young adult template button.
     */
    public String getYoungAdultButtonText() {
        return "Young Adult";
    }

    /**
     * Returns the label text for the mid-career template button.
     * @return The label text for the mid-career template button.
     */
    public String getMidCareerButtonText() {
        return "Mid-Career";
    }

    /**
     * Returns the label text for the pre-retiree template button.
     * @return The label text for the pre-retiree template button.
     */
    public String getPreRetireeButtonText() {
        return "Pre-Retiree";
    }

    /**
     * Returns the template message for young adult clients.
     * @return A formatted message for young adults.
     */
    public String getYoungAdultMessage() {
        return "Hi " + person.getName().fullName + "! ☕\n\n"
                + "I’d love to grab a coffee with you sometime to "
                + "chat about ways to set up a strong financial foundation as "
                + "you start your career. No pressure—just a relaxed conversation to "
                + "answer any questions you might have about planning for the future.\n\n"
                + "Let me know if you're up for it, and we can pick a time that works for you!";
    }

    /**
     * Returns the template message for mid-career clients.
     * @return A formatted message for mid-career clients.
     */
    public String getMidCareerMessage() {
        return "Hi " + person.getName().fullName + ",\n\n"
                + "Hope all’s well! I thought this might be a good time to "
                + "check in and discuss ways to keep your financial goals on track. "
                + "Whether it’s planning for upcoming life changes or just staying ahead of things, "
                + "a quick chat could be helpful.\n\n"
                + "Let me know when you’re available, and we can set up a time that suits you best!";
    }

    /**
     * Returns the template message for pre-retiree clients.
     * @return A formatted message for pre-retirees.
     */
    public String getPreRetireeMessage() {
        return "Hello " + person.getName().fullName + ",\n\n"
                + "I’d love to meet up and go over any financial questions "
                + "you might have as you look toward retirement. Whether it’s planning travel, "
                + "helping family, or just managing things day-to-day, "
                + "there are a lot of options we can explore together.\n\n"
                + "Let me know if you’d like to catch up soon"
                + "—I’m happy to meet whenever it’s convenient for you!";
    }
}
