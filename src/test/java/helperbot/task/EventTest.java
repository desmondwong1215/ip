package helperbot.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import helperbot.exception.HelperBotArgumentException;
import helperbot.exception.HelperBotFileException;

/**
 * Test <code>Event</code>.
 */
class EventTest {

    @Test
    void fromInput_validInputWithTime_success() throws HelperBotArgumentException {
        String input = "event project meeting /from 2025-10-25 10:00 /to 2025-10-25 12:00";
        Event event = Event.fromUserInput(input);
        assertEquals("[E][ ] project meeting (from: Oct 25 2025, 10:00) (to: Oct 25 2025, 12:00)",
                event.toString());
    }

    @Test
    void fromInput_validInputWithoutTime_success() throws HelperBotArgumentException {
        String input = "event team building /from 2025-11-01 /to 2025-11-02";
        Event event = Event.fromUserInput(input);
        assertEquals("[E][ ] team building (from: Nov 1 2025) (to: Nov 2 2025)", event.toString());
    }

    @Test
    void fromInput_fromAfterTo_exceptionThrown() {
        String input = "event party /from 2025-12-01 /to 2025-11-30";
        HelperBotArgumentException thrown = assertThrows(HelperBotArgumentException.class, () -> {
            Event.fromUserInput(input);
        });
        assertEquals("From datetime must be before to datetime", thrown.getMessage());
    }

    @Test
    void fromInput_toBeforeFromOnSameDay_exceptionThrown() {
        String input = "event concert /from 2025-12-01 22:00 /to 2025-12-01 20:00";
        HelperBotArgumentException thrown = assertThrows(HelperBotArgumentException.class, () -> {
            Event.fromUserInput(input);
        });
        assertEquals("From datetime must be before to datetime", thrown.getMessage());
    }

    @Test
    void fromInput_missingFromKeyword_exceptionThrown() {
        String input = "event event_description /to 2025-12-01";
        HelperBotArgumentException thrown = assertThrows(HelperBotArgumentException.class, () -> {
            Event.fromUserInput(input);
        });
        assertEquals("Please enter start date and end data after /from and /to respectively",
                thrown.getMessage());
    }

    @Test
    void fromInput_toBeforeFromKeyword_exceptionThrown() {
        String input = "event event_description /to 2025-12-01 /from 2025-11-30";
        HelperBotArgumentException thrown = assertThrows(HelperBotArgumentException.class, () -> {
            Event.fromUserInput(input);
        });
        assertEquals("Please enter /from before entering /to", thrown.getMessage());
    }

    @Test
    void fromInput_invalidDateFormat_exceptionThrown() {
        String input = "event event_description /from 2025/11/01 /to 2025-11-02";
        assertThrows(HelperBotArgumentException.class, () -> {
            Event.fromUserInput(input);
        });
    }

    @Test
    void fromInput_incompleteInput_exceptionThrown() {
        String input = "event event_description /from 2025-11-01 /to";
        assertThrows(HelperBotArgumentException.class, () -> {
            Event.fromUserInput(input);
        });
    }

    @Test
    void of_validDataWithTime_success() throws HelperBotFileException {
        String[] data = {"E", "0", "buy birthday cake", "2025-10-25", "10:00", "2025-10-25", "11:00"};
        Event event = Event.of(data);
        assertEquals("[E][ ] buy birthday cake (from: Oct 25 2025, 10:00) (to: Oct 25 2025, 11:00)",
                event.toString());
    }

    @Test
    void of_validDataWithoutTime_success() throws HelperBotFileException {
        String[] data = {"E", "1", "attend conference", "2025-11-01", "", "2025-11-03"};
        Event event = Event.of(data);
        assertEquals("[E][X] attend conference (from: Nov 1 2025) (to: Nov 3 2025)", event.toString());
    }

    @Test
    void of_incompleteData_exceptionThrown() {
        String[] data = {"E", "0", "meeting"};
        assertThrows(HelperBotFileException.class, () -> {
            Event.of(data);
        });
    }

    @Test
    void of_fromAfterTo_exceptionThrown() {
        String[] data = {"E", "0", "invalid event", "2025-12-01", "", "2025-11-30"};
        HelperBotFileException thrown = assertThrows(HelperBotFileException.class, () -> {
            Event.of(data);
        });
        assertEquals("From datetime must be before to datetime", thrown.getMessage());
    }

    @Test
    void toStrInFile_eventWithTime_correctFormat() {
        Event event = new Event("go to the gym", LocalDate.of(2025, 11, 10),
                LocalTime.of(17, 30),
                LocalDate.of(2025, 11, 10), LocalTime.of(19, 0));
        assertEquals("E,0,go to the gym,2025-11-10,17:30,2025-11-10,19:00", event.toSavaFormat());
    }

    @Test
    void toStrInFile_eventWithoutTime_correctFormat() {
        Event event = new Event("take a trip", LocalDate.of(2025, 12, 20),
                    null,
                LocalDate.of(2025, 12, 25), null);
        assertEquals("E,0,take a trip,2025-12-20,,2025-12-25,", event.toSavaFormat());
    }
}
