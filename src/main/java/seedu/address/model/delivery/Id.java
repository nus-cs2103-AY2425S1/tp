package seedu.address.model.delivery;

public class Id {

    public static final String MESSAGE_CONSTRAINTS = "Id should be integer";
    private static int count = 0;

    public String value;

    public Id() {
        this.value = String.valueOf(Id.count);
        Id.count += 1;
    }

    public Id(String id) {
        this.value = id;
        int intId = Integer.parseInt(id);
        count = Math.max(intId, count);
    }

    public static boolean isValidId(String test) {
        try {
            int intId = Integer.parseInt(test);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Id: " + value;
    }
}
