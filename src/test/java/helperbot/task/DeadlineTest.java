package helperbot.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import helperbot.exception.HelperBotArgumentException;
import helperbot.exception.HelperBotFileException;

/**
 * Test <code>Deadline</code>.
 */
class DeadlineTest {

    @Test
    void fromUserInput_validInputWithTime_success() throws HelperBotArgumentException {
        String input = "deadline return book /by 2025-10-25 18:00";
        Deadline deadline = Deadline.fromUserInput(input);
        assertEquals("[D][ ] return book (by: Oct 25 2025, 18:00)", deadline.toString());
    }

    @Test
    void fromUserInput_validInputWithoutTime_success() throws HelperBotArgumentException {
        String input = "deadline complete report /by 2025-11-01";
        Deadline deadline = Deadline.fromUserInput(input);
        assertEquals("[D][ ] complete report (by: Nov 1 2025)", deadline.toString());
    }

    @Test
    void fromUserInput_missingByKeyword_exceptionThrown() {
        String input = "deadline submit assignment";
        HelperBotArgumentException thrown = assertThrows(HelperBotArgumentException.class, () -> {
            Deadline.fromUserInput(input);
        });
        assertEquals("Please enter the deadline after /by", thrown.getMessage());
    }

    @Test
    void fromUserInput_invalidDateFormat_exceptionThrown() {
        String input = "deadline pay bills /by 2025/12/30";
        assertThrows(HelperBotArgumentException.class, () -> {
            Deadline.fromUserInput(input);
        });
    }

    @Test
    void fromUserInput_incompleteInput_exceptionThrown() {
        String input = "deadline prepare for meeting /by";
        assertThrows(HelperBotArgumentException.class, () -> {
            Deadline.fromUserInput(input);
        });
    }

    @Test
    void of_incompleteData_exceptionThrown() {
        String[] data = {"D", "0", "deadline1"};
        assertThrows(HelperBotFileException.class, () -> {
            Event.of(data);
        });
    }

    @Test
    void of_validDataWithTime_success() throws HelperBotFileException {
        String[] data = {"D", "0", "deadline1", "2025-10-25", "10:00"};
        Deadline deadline = Deadline.of(data);
        assertEquals("[D][ ] deadline1 (by: Oct 25 2025, 10:00)",
                deadline.toString());
    }

    @Test
    void of_validDataWithoutTime_success() throws HelperBotFileException {
        String[] data = {"D", "0", "deadline1", "2025-10-25"};
        Deadline deadline = Deadline.of(data);
        assertEquals("[D][ ] deadline1 (by: Oct 25 2025)",
                deadline.toString());
    }

    @Test
    void toSaveFormat_withoutByTime_success() {
        Deadline deadline = new Deadline("deadline1", LocalDate.parse("2025-09-12"), null);
        assertEquals("D,0,deadline1,2025-09-12,", deadline.toSavaFormat());
    }

    @Test
    void toSaveFormat_havaByTime_success() {
        Deadline deadline = new Deadline("deadline1", LocalDate.parse("2025-09-12"),
                LocalTime.parse("21:30"));
        assertEquals("D,0,deadline1,2025-09-12,21:30", deadline.toSavaFormat());
    }

    @Test
    void update_updateDescription_success() throws HelperBotArgumentException {
        String input = "new description";
        Deadline deadline = new Deadline("deadline1", LocalDate.parse("2025-09-12"), null);
        assertEquals("[D][ ] new description (by: Sep 12 2025)", deadline.update(input).toString());
    }

    @Test
    void update_updateByDateAndByTime_success() throws HelperBotArgumentException {
        String input = "/by 2025-10-12 21:30";
        Deadline deadline = new Deadline("deadline1", LocalDate.parse("2025-09-12"), null);
        assertEquals("[D][ ] deadline1 (by: Oct 12 2025, 21:30)", deadline.update(input).toString());
    }

    @Test
    void update_updateAll_success() throws HelperBotArgumentException {
        String input = "new description /by 2025-10-12 21:30";
        Deadline deadline = new Deadline("deadline1", LocalDate.parse("2025-09-12"), null);
        assertEquals("[D][ ] new description (by: Oct 12 2025, 21:30)", deadline.update(input).toString());
    }
}
