import java.util.Objects;

/**
 * Represents a card in the game.
 * Stores an {@link Card#id}, {@link Card#name}, {@link Card#rank} and {@link Card#price} associated with the card.
 * Supports comparison based on rank, name and id.
 *
 * @see Rank
 *
 * @author DanielJudd
 */
public class Card implements Comparable<Card> {
    /**
     * The numeric id used to uniquely identify the card.
     */
    private final long id;
    /**
     * The name of the card.
     */
    private final String name;
    /**
     * The rank of the card.
     */
    private final Rank rank;
    /**
     * The sale price of the card.
     */
    private final long price;

    /**
     * The constructor for a Card.
     *
     * @param id        A numeric id used to uniquely identify this card.
     * @param name      The name of the card.
     * @param rank      The rank of the card.
     * @param price     The sale price of the card.
     */
    public Card(long id, String name, Rank rank, long price) {
        this.id = id;
        this.name = name;
        this.rank = rank;
        this.price = price;
    }

    /**
     * The constructor for a new Card. This constructor sets the price to 0.
     *
     * @param id        A numeric id used to uniquely identify this card.
     * @param name      The name of the card.
     * @param rank      The rank of the card.
     */
    public Card(long id, String name, Rank rank) {
        this.id = id;
        this.name = name;
        this.rank = rank;
        this.price = 0;
    }

    /**
     * Returns a string representation of the card.
     * Includes the rank, name, id and price of the card.
     * Used for printing to the terminal.
     * <br>
     * Format: "['RANK'] 'NAME' {ID:'ID'} 'PRICE' credits"
     * <br>
     * Example: "[COMMON] Butler {ID:12345} 20 credits"
     *
     * @return A string representation of the card.
     */
    @Override
    public String toString() {
        return "[" + rank.name() + "] " + name + " {ID:" + id + "} " + price + " credits";
    }

    /**
     * Uses {@link Objects#hash(Object...)} to calculate the hash code of the card using its id, name and rank.
     *
     * @return The hash code value of the card.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, rank);
    }

    /**
     * Compares another object with this card to check equality.
     * Two cards are considered equal if and only if they have the same id, name and rank
     *
     * @param obj   The object to compare this card with.
     * @return true if this card is considered the same as the given object, otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        // Check if obj is null
        if (obj == null) return false;
        // Check if obj is a Card object
        if (obj.getClass() != getClass()) return false;

        Card card = (Card) obj;
        // Check that id, name and rank are all the same
        return card.id == id && card.name.equals(name) && card.rank == rank;
    }

    /**
     * Compares this card with another card.
     * Returns a negative integer if this card is less than the other card.
     * Returns zero if this card is equal to the other card.
     * Returns a positive integer if this card is greater than the other card.
     * Cards are ordered by rank, name then id.
     *
     * @param card      The card to compare this card with.
     * @return An integer based on this cards comparison with the given card.
     */
    @Override
    public int compareTo(Card card) {
        // Compare the ranks. If equal check name.
        if (rank.compareTo(card.rank) != 0) return rank.compareTo(card.rank);

        // Compare the names. If equal check id.
        if (name.compareTo(card.name) != 0) return name.compareTo(card.name);

        // Compare the ids.
        return Long.compare(id, card.id);
    }

    /**
     * Returns the card's id.
     *
     * @return the unique id of the card.
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the card's name.
     *
     * @return the name of the card.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the card's rank.
     *
     * @return the rank of the card.
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Returns the card's price.
     *
     * @return the price of the card.
     */
    public long getPrice() {
        return price;
    }
}