import java.util.List;

/**
 * Used to test the {@link HollomonClient} class. Tests successful and unsuccessful logins.
 *
 * @see HollomonClient
 *
 * @author DanielJudd
 */
public class HollomonClientTest {

    /**
     * The valid server address.
     */
    private static final String server = "netsrv.cim.rhul.ac.uk";
    /**
     * The valid port number.
     */
    private static final int port = 1812;

    /**
     * Calls {@link HollomonClientTest#testAllHollomonClientTests(String[])} to initiate testing.
     *
     * @param args              Command line arguments. The 1st argument should be a valid username, the second a valid password.
     */
    public static void main(String[] args) {
        // Assumption: args[0] and args[1] represent a valid username and password respectively.
        testAllHollomonClientTests(args);
    }

    /**
     * Calls all other methods in order to test the {@link HollomonClient} class.
     *
     * @param args              Command line arguments.
     */
    private static void testAllHollomonClientTests(String[] args) {
        // Test login details
        testSuccessfulLogin(args);
        testUnsuccessfulLogin();

        System.out.println("All HollomonClient tests passed!");
    }

    /**
     * Tests that after a successful login, the returned {@link List} of cards is not empty.
     *
     * @param args              Command line arguments.
     */
    private static void testSuccessfulLogin(String[] args) {
        // Create connection
        HollomonClient hollomonClient = new HollomonClient(server, port);
        // Attempt to log in with an invalid username and password
        List<Card> cards = hollomonClient.login(args[0], args[1]);
        hollomonClient.close();

        assert !cards.isEmpty();

        System.out.println("SuccessfulLogin test passed!");
    }

    /**
     * Tests that after an unsuccessful login, null is returned.
     */
    private static void testUnsuccessfulLogin() {
        // Create connection
        HollomonClient hollomonClient = new HollomonClient(server, port);
        // Attempt to log in with an invalid username and password
        List<Card> cards = hollomonClient.login("InvalidUsername", "InvalidPassword");
        hollomonClient.close();

        assert cards == null;

        System.out.println("UnsuccessfulLogin test passed!");
    }
}
