package helperbot.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import helperbot.exception.HelperBotArgumentException;
import helperbot.exception.HelperBotFileException;

/**
 * Test <code>ToDo</code>.
 */
class ToDoTest {

    @Test
    void fromInput_validInput_success() throws HelperBotArgumentException {
        String input = "todo read a book";
        ToDo toDo = ToDo.fromUserInput(input);
        assertEquals("[T][ ] read a book", toDo.toString());
    }

    @Test
    void fromInput_emptyDescription_exceptionThrown() {
        String input = "todo   ";
        HelperBotArgumentException thrown = assertThrows(HelperBotArgumentException.class, () -> {
            ToDo.fromUserInput(input);
        });
        assertEquals("Empty description.", thrown.getMessage());
    }

    @Test
    void of_unmarkedTask_success() throws HelperBotFileException {
        String[] message = {"T", "0", "buy groceries"};
        ToDo toDo = ToDo.of(message);
        assertEquals("[T][ ] buy groceries", toDo.toString());
    }

    @Test
    void of_markedTask_success() throws HelperBotFileException {
        String[] message = {"T", "1", "submit report"};
        ToDo toDo = ToDo.of(message);
        assertEquals("[T][X] submit report", toDo.toString());
    }

    @Test
    void of_invalidStatus_exceptionThrown() {
        String[] message = {"T", "2", "invalid task"};
        HelperBotFileException thrown = assertThrows(HelperBotFileException.class, () -> {
            ToDo.of(message);
        });
        assertEquals("Invalid status 2 for Task.", thrown.getMessage());
    }

    @Test
    void of_incompleteData_exceptionThrown() {
        String[] message = {"T", "0"};
        HelperBotFileException thrown = assertThrows(HelperBotFileException.class, () -> {
            ToDo.of(message);
        });
        assertEquals("Incomplete data for Task.", thrown.getMessage());
    }

    @Test
    void toStrInFile_validTask_correctFormat() {
        ToDo toDo = new ToDo("take out the trash");
        assertEquals("T,0,take out the trash", toDo.toSavaFormat());
    }
}
