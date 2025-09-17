package helperbot.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import helperbot.exception.HelperBotArgumentException;
import helperbot.task.Task;
import helperbot.task.TaskList;
import helperbot.task.ToDo;

/**
 * Test <code>Ui</code>.
 */
class ResponseTest {

    private Response response;
    private ToDo todo;

    @BeforeEach
    void setup() throws HelperBotArgumentException {
        this.response = new Response();
        this.todo = ToDo.fromUserInput("todo testing todo");
    }

    @Test
    void getGreetMessage_validOutput_success() {
        String output = this.response.getGreetMessage();
        String expectedOutput = "Hello! I'm HelperBot.\nWhat can I do for you?";
        assertEquals(expectedOutput, output);
    }

    @Test
    void getMarkCommandResponse_validTask_correctOutput() {
        Task mockTask = new Task("test task") {
            @Override
            public String toString() {
                return "[T][X] test task";
            }
        };
        String output = this.response.getMarkCommandResponse(true, new String[] {"1"},
                new String[] {mockTask.toString()});
        String expectedOutput = "Nice! I have marked HelperBot task 1 as done!\n\t[T][X] test task";
        assertEquals(expectedOutput, output);
    }

    @Test
    void getUnmarkCommandResponse_validTask_correctOutput() {
        Task mockTask = new Task("another test task") {
            @Override
            public String toString() {
                return "[T][ ] another test task";
            }
        };
        String output = this.response.getMarkCommandResponse(false, new String[] {"2"},
                new String[] {mockTask.toString()});
        String expectedOutput = "Nice! I have marked HelperBot task 2 as not done yet!\n\t[T][ ] another test task";
        assertEquals(expectedOutput, output);
    }

    @Test
    void getErrorMessage_validMessage_correctOutput() {
        String errorMessage = "Invalid command entered.";
        String output = this.response.getErrorMessage(errorMessage);
        String expectedOutput = "Error!\n" + errorMessage;
        assertEquals(expectedOutput, output);
    }

    @Test
    void getAddCommandResponse_validInput_correctOutput() {
        String output = this.response.getAddCommandResponse(this.todo, 1);
        String expectedOutput = "Got it. I've added this HelperBot task:\n\t"
                + this.todo.toString()
                + "\nYou now have 1 tasks in the list.";
        assertEquals(expectedOutput, output);
    }

    @Test
    void getDeleteCommandResponse_validInput_correctOutput() {
        String output = this.response.getDeleteCommandResponse(new String[]{this.todo.toString(), this.todo.toString()},
                3, new String[]{"3", "4"});
        String expectedOutput = "Nice! I have removed HelperBot task "
                + "3, 4"
                + "!\n\t"
                + this.todo.toString()
                + "\n\t"
                + this.todo.toString()
                + "\nYou now have "
                + 3
                + " tasks in the list.";
        assertEquals(expectedOutput, output);
    }

    @Test
    void getTaskListResponse_isMatchedAndEmptyList_correctOutput() {
        String output = this.response.getTaskListResponse(true, "");
        String expectedOutput = "You do not have any matching HelperBot task.";
        assertEquals(expectedOutput, output);
    }

    @Test
    void getTaskListResponse_isMatched_correctOutput() {
        TaskList taskList = new TaskList();
        taskList.add(this.todo);
        String output = this.response.getTaskListResponse(true, taskList.toString());
        String expectedOutput = "Here are the matching tasks in your list:" + taskList;
        assertEquals(expectedOutput, output);
    }

    @Test
    void getTaskListResponse_notMatched_correctOutput() {
        TaskList taskList = new TaskList();
        taskList.add(this.todo);
        String output = this.response.getTaskListResponse(false, taskList.toString());
        String expectedOutput = "Here are the tasks in your list:" + taskList;
        assertEquals(expectedOutput, output);
    }

    @Test
    void getTaskListResponse_notMatchedAndEmptyList_correctOutput() {
        String output = this.response.getTaskListResponse(false, "");
        String expectedOutput = "You do not have any HelperBot task.";
        assertEquals(expectedOutput, output);
    }

    @Test
    void getExitMessage_noError_correctOutput() {
        String output = this.response.getExitMessage();
        String expectedOutput = "Bye. Hope to see you again soon!";
        assertEquals(expectedOutput, output);
    }

    @Test
    void getExitErrorMessage_validMessage_correctOutput() {
        String errorMessage = "File could not be loaded.";
        String output = this.response.getExitErrorMessage(errorMessage);
        String expectedOutput = errorMessage + "\n"
                + "Bye. Hope to see you again soon!";
        assertEquals(expectedOutput, output);
    }
}
