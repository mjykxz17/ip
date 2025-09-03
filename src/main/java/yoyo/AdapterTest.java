package yoyo;

public class AdapterTest {

    public static void main(String[] args) {
        System.out.println("Testing YoyoAdapter...");

        YoyoAdapter adapter = new YoyoAdapter();

        // Test basic commands
        System.out.println("Response to 'list': " + adapter.respond("list"));
        System.out.println("Response to 'todo read book': " + adapter.respond("todo read book"));
        System.out.println("Response to 'list': " + adapter.respond("list"));
        System.out.println("Response to 'help': " + adapter.respond("help"));
        System.out.println("Response to 'bye': " + adapter.respond("bye"));

        System.out.println("YoyoAdapter test completed successfully!");
    }
}
