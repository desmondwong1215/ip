package helperbot.task;

import helperbot.exception.HelperBotArgumentException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test <code>Deadline</code>.
 */
class DeadlineTest {

    @Test
    void fromInput_validInputWithTime_success() throws HelperBotArgumentException {
        String input = "deadline return book /by 2025-10-25 18:00";
        Deadline deadline = Deadline.fromInput(input);
        assertEquals("[D][ ] return book (by: Oct 25 2025, 18:00)", deadline.toString());
    }

    @Test
    void fromInput_validInputWithoutTime_success() throws HelperBotArgumentException {
        String input = "deadline complete report /by 2025-11-01";
        Deadline deadline = Deadline.fromInput(input);
        assertEquals("[D][ ] complete report (by: Nov 1 2025)", deadline.toString());
    }

    @Test
    void fromInput_missingByKeyword_exceptionThrown() {
        String input = "deadline submit assignment";
        HelperBotArgumentException thrown = assertThrows(HelperBotArgumentException.class, () -> {
            Deadline.fromInput(input);
        });
        assertEquals("Please enter the deadline after /by", thrown.getMessage());
    }

    @Test
    void fromInput_invalidDateFormat_exceptionThrown() {
        String input = "deadline pay bills /by 2025/12/30";
        assertThrows(HelperBotArgumentException.class, () -> {
            Deadline.fromInput(input);
        });
    }

    @Test
    void fromInput_incompleteInput_exceptionThrown() {
        String input = "deadline prepare for meeting /by";
        assertThrows(HelperBotArgumentException.class, () -> {
            Deadline.fromInput(input);
        });
    }
}
