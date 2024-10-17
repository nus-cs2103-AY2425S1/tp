package tahub.contacts.model.comment;

import static tahub.contacts.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import tahub.contacts.model.person.Person;


/**
 * Constructs a Comment object with the given commenter, receiver, and comment text
 * Each Person to potentially have two Arrays of Comment associated with them (to be implemented)
 * One Array to represent sent comments, one Array to represent received comments
 */
public class Comment {

    private Person commenter;
    private Person receiver;
    private String commentText;

    /**
     * Constructs a Comment object with the given commenter, receiver, and comment text.
     *
     * @param commenter The person who is leaving the comment.
     * @param receiver The person who is receiving the comment.
     * @param commentText The text content of the comment.
     */
    public Comment(Person commenter, Person receiver, String commentText) {
        requireAllNonNull(commenter, receiver, commentText);
        this.commenter = commenter;
        this.receiver = receiver;
        this.commentText = commentText;
    }

    /**
     * Retrieves the commenter of this Comment.
     *
     * @return The Person who left the comment.
     */
    public Person getCommenter() {
        return this.commenter;
    }

    /**
     * Returns the receiver of this comment.
     *
     * @return The receiver of this comment.
     */
    public Person getReceiver() {
        return this.receiver;
    }

    /**
     * Retrieves the text content of the comment.
     *
     * @return The text content of the comment.
     */
    public String getCommentText() {
        return this.commentText;
    }

    @Override
    public String toString() {
        return String.format("Comment by %s to %s: %s",
                this.commenter.getName(), this.receiver.getName(), this.commentText);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Comment)) {
            return false;
        }

        Comment otherComment = (Comment) other;
        return commenter.equals(otherComment.commenter)
                && receiver.equals(otherComment.receiver)
                && commentText.equals(otherComment.commentText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.commenter, this.receiver, this.commentText);
    }
}
