import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * An {@link InputStream} which is used to read {@link Card} objects.
 *
 * @see Card
 *
 * @author DanielJudd
 */
public class CardInputStream extends InputStream {

    /**
     * The reader used to read the {@link InputStream} from the server.
     */
    private final BufferedReader reader;

    /**
     * Constructors a new CardInputStream and initialises a {@link BufferedReader} {@link CardInputStream#reader} using the given {@link InputStream}
     *
     * @param input     The {@link InputStream} where card data will be read from.
     */
    public CardInputStream(InputStream input) {
        this.reader = new BufferedReader(new InputStreamReader(input));
    }

    /**
     * A required method from {@link InputStream}.
     *
     * @return Always returns 0.
     */
    @Override
    public int read() {
        return 0;
    }

    /**
     * Reads a {@link Card} from the input stream.
     * It first checks that the next line is "CARD".
     * If so, it then reads the id, name, rank and price from the input stream, before returning a new Card with these values.
     * If the next line was "OK", return null.
     *
     * @return A new {@link Card}, or null if "OK" is read.
     */
    public Card readCard() {
        // Read header (Should be "CARD" or "OK")
        String newHeader = readResponse();
        if (newHeader.equals("CARD")) {
            // Next 4 lines will be card info
            long id = Long.parseLong(readResponse());
            String name = readResponse();
            Rank rank = Rank.valueOf(readResponse());
            long price = Long.parseLong(readResponse());

            // Return a new card with the read info
            return new Card(id, name, rank, price);
        }
        // If the header is not "CARD" or "OK" then inform user
        if (!newHeader.equals("OK")) System.out.println("Card input stream incorrectly formatted. Received {" + newHeader + "}");
        return null;
    }

    /**
     * Reads a line from the input stream. If an IO error occurs, inform user.
     * This method is used in {@link HollomonClient} to read from the socket.
     *
     * @return A string of the line read from the input stream. If error occurs, null.
     */
    public String readResponse() {
        try {
            // Read line from server
            return reader.readLine();
        } catch (Exception e) {
            System.out.println("Could not read response from server. " + e.getMessage());
            return null;
        }
    }

    /**
     * Closes the {@link BufferedReader} {@link CardInputStream#reader}.
     */
    @Override
    public void close() {
        try {
            reader.close();
        } catch (Exception e) {
            System.out.println("Failed to close CardInputStream. " + e.getMessage());
        }
    }
}
