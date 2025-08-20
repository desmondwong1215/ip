import java.util.Scanner;

public class HelperBot {

    public static final String LINE = "____________________________________________________________";
    public static final String NAME = "HelperBot";

    public void greet(String name) {
        System.out.println(HelperBot.LINE);
        System.out.printf("Hello! I'm %s.\n", name);
        System.out.println("What can I do for you?");
        System.out.println(HelperBot.LINE);
    }

    public void echo(String message) {
        System.out.println(HelperBot.LINE);
        System.out.println(">> " + message);
        System.out.println(HelperBot.LINE);
    }

    public void exit() {
        System.out.println(HelperBot.LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(HelperBot.LINE);
    }

    public void chat() {
        // greet the user
        this.greet(HelperBot.NAME);

        Scanner scanner = new Scanner(System.in);

        // chat with the user, exit when user enter 'bye'
        while (true) {
            System.out.println();
            String message = scanner.nextLine();

            // user want to exit
            if (message.equalsIgnoreCase("bye")) {
                this.exit();
                break;
            }

            // repeat the message entered by user
            this.echo(message);
        }
    }

    public static void main(String[] args) {
        HelperBot bot = new HelperBot();
        bot.chat();
    }
}
