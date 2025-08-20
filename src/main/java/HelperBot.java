public class HelperBot {

    public static final String LINE = "____________________________________________________________";

    public static void greet(String name) {
        System.out.println(HelperBot.LINE);
        System.out.printf("Hello! I'm %s.\n", name);
        System.out.println("What can I do for you?");
        System.out.println(HelperBot.LINE);
    }

    public static void exit() {
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(HelperBot.LINE);
    }

    public static void main(String[] args) {
        String name = "HelperBot";
        greet(name);
        exit();
    }
}
