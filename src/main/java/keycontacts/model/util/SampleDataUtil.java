package keycontacts.model.util;

import java.util.HashSet;

import keycontacts.model.ReadOnlyStudentDirectory;
import keycontacts.model.StudentDirectory;
import keycontacts.model.lesson.CancelledLesson;
import keycontacts.model.lesson.Day;
import keycontacts.model.lesson.RegularLesson;
import keycontacts.model.lesson.Time;
import keycontacts.model.pianopiece.PianoPiece;
import keycontacts.model.student.Address;
import keycontacts.model.student.GradeLevel;
import keycontacts.model.student.Name;
import keycontacts.model.student.Phone;
import keycontacts.model.student.Student;

/**
 * Contains utility methods for populating {@code StudentDirectory} with sample data.
 */
public class SampleDataUtil {
    public static Student[] getSampleStudents() {
        return new Student[] {
            new Student(new Name("Alex Yeoh"), new Phone("87438807"), new Address("Blk 30 Geylang Street 29, #06-40"),
                    new GradeLevel("ABRSM 1"), PianoPiece.getPianoPieceSet("Canon in D"),
                    new RegularLesson(new Day("Monday"), new Time("14.00"), new Time("16:00")),
                    CancelledLesson.getCancelledLessonSet("14-10-2024"), new HashSet<>()),
            new Student(new Name("Bernice Yu"), new Phone("99272758"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new GradeLevel("RSL 2"),
                    PianoPiece.getPianoPieceSet("Moonlight Sonata"),
                    new RegularLesson(new Day("Tuesday"), new Time("14.00"), new Time("16:00")),
                    CancelledLesson.getCancelledLessonSet("15-10-2024"), new HashSet<>()),
            new Student(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new GradeLevel("AMEB 3"),
                    PianoPiece.getPianoPieceSet("Etude", "Moonlight Sonata"),
                    new RegularLesson(new Day("Wednesday"), new Time("14.00"), new Time("16:00")),
                    CancelledLesson.getCancelledLessonSet("16-10-2024"), new HashSet<>()),
            new Student(new Name("David Li"), new Phone("91031282"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new GradeLevel("RCM 1"),
                    PianoPiece.getPianoPieceSet("Howl's moving castle"),
                    new RegularLesson(new Day("Thursday"), new Time("14.00"), new Time("16:00")),
                    CancelledLesson.getCancelledLessonSet("17-10-2024"), new HashSet<>()),
            new Student(new Name("Irfan Ibrahim"), new Phone("92492021"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new GradeLevel("IMEB 2"),
                    PianoPiece.getPianoPieceSet("Moonlight Sonata"),
                    new RegularLesson(new Day("Friday"), new Time("14.00"), new Time("16:00")),
                    CancelledLesson.getCancelledLessonSet("18-10-2024"), new HashSet<>()),
            new Student(new Name("Roy Balakrishnan"), new Phone("92624417"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new GradeLevel("RSL 1"),
                    PianoPiece.getPianoPieceSet("Styx Helix"),
                    new RegularLesson(new Day("Saturday"), new Time("14.00"), new Time("16:00")),
                    CancelledLesson.getCancelledLessonSet("19-10-2024"), new HashSet<>()),
        };
    }

    public static ReadOnlyStudentDirectory getSampleStudentDirectory() {
        StudentDirectory sampleSd = new StudentDirectory();
        for (Student sampleStudent : getSampleStudents()) {
            sampleSd.addStudent(sampleStudent);
        }
        return sampleSd;
    }

}
