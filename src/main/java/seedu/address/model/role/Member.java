package seedu.address.model.role;

/**
 * Member represents a special Role in address book.
 * Guarantees: immutable; name is always "Member".
 */
public class Member extends Role {
    public static final String MEMBER_ROLE = "Member";
    public Member() {
        super(MEMBER_ROLE);
    }

    /**
     * Check if other role is a member.
     * @param other
     * @return true if other is an instance of Member or an instance of Role with Member keyword
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof Member) {
            return true;
        } else if (other instanceof Role) {
            Role otherRole = (Role) other;
            return otherRole.equals(new Role(MEMBER_ROLE));
        }
        return false;
    }
}
