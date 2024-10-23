import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Used to communicate with the server.
 * Allows user to log in to view their credits and owned cards, view the cards on offer, as well as buy and sell cards.
 *
 * @see CardInputStream
 *
 * @author DanielJudd
 */
public class HollomonClient {

    /**
     * The server address.
     */
    private final String server;
    /**
     * The port number.
     */
    private final int port;

    /**
     * The socket into the server.
     */
    private Socket socket;
    /**
     * The writer used to write to the server.
     */
    private BufferedWriter writer;
    /**
     * Reads lines from the server. Can also load {@link Card} objects using {@link CardInputStream#readCard()}.
     */
    private CardInputStream cardReader;

    /**
     * Constructs a new HollomonClient with the given server and port.
     *
     * @param server    The server address.
     * @param port      The port number.
     */
    public HollomonClient(String server, int port) {
        this.server = server;
        this.port = port;
    }

    /**
     * Creates a new {@link Socket} using the {@link HollomonClient#server} and {@link HollomonClient#port}.
     * Attempts to log in using the given username and password.
     * If successful, retrieves the cards from the {@link InputStream}.
     *
     * @param username      The unique name of the user attempting to log in.
     * @param password      The password of the user attempting to log in.
     * @return              A {@link List} of {@link Card} objects if login successful, otherwise null.
     */
    public List<Card> login(String username, String password) {
        try {
            socket = new Socket(server, port);

            // Create reader amd writer
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            cardReader = new CardInputStream(socket.getInputStream());

            // Attempt login
            writer.write(username);
            writer.newLine();
            writer.write(password);
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            // Should only occur when the server is down
            System.out.println("Error when attempting to connect to server. " + e.getMessage());
            return null;
        }

        // Read response
        String response = cardReader.readResponse();
        // Check if response is the expected response
        if (response.equals("User " + username + " logged in successfully.")) {
            // Login successful
            return readCards();
        } else {
            return null;
        }
    }

    /**
     * Retrieves the number of credits the user currently has.
     * Checks that the response ends with "OK"
     *
     * @return              The number of credits
     */
    public long getCredits() {
        try {
            writer.write("CREDITS");
            writer.newLine();
            writer.flush();

            // Read response
            String creditResponse = cardReader.readResponse();
            // Check response is OK
            String okResponse = cardReader.readResponse();
            if (okResponse.equals("OK")) return Long.parseLong(creditResponse);
            throw new IOException("Response from server incorrect. Received: {" + creditResponse + ", " + okResponse + "}");
        } catch (Exception e) {
            System.out.println("Failed to retrieve credits. " + e.getMessage());
            return -1;
        }
    }

    /**
     * Retrieves the cards owned by the user.
     *
     * @return              A {@link List} of {@link Card} objects.
     */
    public List<Card> getCards() {
        try {
            writer.write("CARDS");
            writer.newLine();
            writer.flush();

            return readCards();
        } catch (Exception e) {
            System.out.println("Failed to get owned cards. " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves the cards on offer.
     *
     * @return              A {@link List} of {@link Card} objects.
     */
    public List<Card> getOffers() {
        try {
            writer.write("OFFERS");
            writer.newLine();
            writer.flush();

            return readCards();
        } catch (Exception e) {
            System.out.println("Failed to get offers. " + e.getMessage());
            return null;
        }
    }

    /**
     * Reads cards until {@link CardInputStream#readCard()} returns null.
     *
     * @return                  A {@link List} of {@link Card} objects.
     */
    private List<Card> readCards() {
        try {
            // Create a new List
            List<Card> cards = new ArrayList<>();
            // Loop until no more cards
            Card card;
            while ((card = cardReader.readCard()) != null) {
                // Add card to List
                cards.add(card);
            }
            // Sort the cards using compareTo function
            Collections.sort(cards);
            return cards;
        } catch(Exception e) {
            System.out.println("Failed to read all cards. " + e.getMessage());
            return null;
        }
    }

    /**
     * Asks the server to buy a given card. Will only attempt to buy the card if the user has enough credits.
     *
     * @param card              The card that the user wants to buy.
     * @return                  true if the card was bought successfully, otherwise false.
     */
    public boolean buyCard(Card card) {
        // Check that user can afford card
        long credits = getCredits();
        if (credits >= card.getPrice() && credits >= 0) {
            try {
                writer.write("BUY " + card.getId());
                writer.newLine();
                writer.flush();

                return cardReader.readResponse().equals("OK");
            } catch (Exception e) {
                System.out.println("Failed buy card. " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    /**
     * Asks the server to sell a given card.
     *
     * @param card              The card that the user wants to sell.
     * @param price             The price the user wants to sell the card for.
     * @return                  true if the card was sold successfully, otherwise false.
     */
    public boolean sellCard(Card card, long price) {
        try {
            writer.write("SELL " + card.getId() + " " + price);
            writer.newLine();
            writer.flush();

            return cardReader.readResponse().equals("OK");
        } catch (Exception e) {
            System.out.println("Could not sell card. " + e.getMessage());
            return false;
        }
    }

    /**
     * Closes the {@link HollomonClient#cardReader}, {@link HollomonClient#writer} and {@link HollomonClient#socket}.
     */
    public void close() {
        try {
            // Close reader and writer
            cardReader.close();
            writer.close();
            // Close socket
            socket.close();
        } catch (Exception e) {
            System.out.println("Failed to close resources. " + e.getMessage());
        }
    }
}
