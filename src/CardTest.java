import java.util.HashSet;
import java.util.TreeSet;

/**
 * Used to test the {@link Card} class.
 * Tests all the methods of {@link Card}.
 * Tests the use of {@link Card} objects in {@link HashSet} and {@link TreeSet}.
 *
 * @see Card
 *
 * @author DanielJudd
 */
public class CardTest {

    /**
     * Calls {@link CardTest#testAllCardTests()} to initiate testing.
     *
     * @param args  Command line arguments.
     */
    public static void main(String[] args) {
        testAllCardTests();
    }

    /**
     * Calls all other methods in order to test the {@link Card} class.
     */
    private static void testAllCardTests() {
        // Test Card class methods
        testCardConstructors();
        testCardHashCode();
        testCardEquals();
        testCardCompareTo();

        // Test Card class with HashSet and TreeSet
        testHashSet();
        testTreeSet();

        System.out.println("All Card tests passed!");
    }

    /**
     * Tests {@link Card#Card(long, String, Rank)} and {@link Card#Card(long, String, Rank, long)}
     * Creates one card without a given price and one card with a given price.
     * Checks that the prices are as expected.
     */
    private static void testCardConstructors() {
        // Check default constructor
        Card defaultPriceCard = new Card(12345, "Butler", Rank.COMMON);
        assert defaultPriceCard.getPrice() == 0;

        // Check custom price constructor
        Card customPriceCard = new Card(12345, "Butler", Rank.COMMON, 20);
        assert customPriceCard.getPrice() == 20;

        System.out.println("Constructor tests passed!");
    }

    /**
     * Tests the {@link Card#hashCode()} method.
     * Asserts that two different Card objects with the same values, have the same hashCode.
     * Asserts that a different Card object with different values, has a different hashCode from the alike Cards.
     */
    private static void testCardHashCode() {
        // Check alike cards
        Card alikeCard1 = new Card(12345, "Butler", Rank.COMMON);
        Card alikeCard2 = new Card(12345, "Butler", Rank.COMMON);
        assert alikeCard1.hashCode() == alikeCard2.hashCode();

        // Check different card with alike cards
        Card differentCard = new Card(54321, "Gate Lodge", Rank.COMMON);
        assert alikeCard1.hashCode() != differentCard.hashCode();
        assert alikeCard2.hashCode() != differentCard.hashCode();

        System.out.println("HashCode tests passed!");
    }

    /**
     * Tests the {@link Card#equals(Object)} method.
     * Asserts that two different Card objects with the same values are considered equal.
     * Asserts that a different Card object with different values is not considered equal.
     */
    private static void testCardEquals() {
        // Check alike cards
        Card alikeCard1 = new Card(12345, "Butler", Rank.COMMON);
        Card alikeCard2 = new Card(12345, "Butler", Rank.COMMON);
        assert alikeCard1.equals(alikeCard2);
        assert alikeCard2.equals(alikeCard1);

        // Check different card with alike cards
        Card differentCard = new Card(54321, "Gate Lodge", Rank.COMMON);
        assert !alikeCard1.equals(differentCard);
        assert !alikeCard2.equals(differentCard);

        System.out.println("Equals tests passed!");
    }

    /**
     * Tests that the {@link Card#compareTo(Card)} method correctly compares two cards.
     * Asserts equality along with different ranks, names and ids.
     */
    private static void testCardCompareTo() {
        // Create a new card which will be compared to.
        Card comparisonCard = new Card(12345, "Butler", Rank.COMMON);

        // Check that an alike card returns 0
        Card equalityCard = new Card(12345, "Butler", Rank.COMMON);
        assert comparisonCard.compareTo(equalityCard) == 0;
        assert equalityCard.compareTo(comparisonCard) == 0;

        // Check that a higher rank returns positive
        Card differentRankCard = new Card(12345, "Butler", Rank.RARE);
        assert comparisonCard.compareTo(differentRankCard) > 0;
        assert differentRankCard.compareTo(comparisonCard) < 0;

        // Check that an alphabetically lower name returns negative
        Card differentNameCard = new Card(12345, "Gate Lodge", Rank.COMMON);
        assert comparisonCard.compareTo(differentNameCard) < 0;
        assert differentNameCard.compareTo(comparisonCard) > 0;

        // Check that a bigger id returns negative
        Card differentIdCard = new Card(54321, "Butler", Rank.COMMON);
        assert comparisonCard.compareTo(differentIdCard) < 0;
        assert differentIdCard.compareTo(comparisonCard) > 0;

        System.out.println("CompareTo tests passed!");
    }

    /**
     * Asserts that alike cards are not added twice to a {@link HashSet}.
     * Asserts that a different card is added.
     */
    private static void testHashSet() {
        // Create new HashSet and two alike cards
        HashSet<Card> hashSet = new HashSet<>();
        Card alikeCard1 = new Card(12345, "Butler", Rank.COMMON);
        Card alikeCard2 = new Card(12345, "Butler", Rank.COMMON);

        // Attempt to add both cards
        hashSet.add(alikeCard1);
        hashSet.add(alikeCard2); // Should NOT be added
        assert hashSet.size() == 1;

        // Create a different card
        Card differentCard = new Card(54321, "Gate Lodge", Rank.RARE);
        // Attempt to add different card
        hashSet.add(differentCard); // Should be added
        assert hashSet.size() == 2;

        System.out.println("HashSet tests passed!");
    }

    /**
     * Asserts that cards are added to a {@link TreeSet} correctly.
     */
    private static void testTreeSet() {
        // Create new TreeSet and four cards with varying attributes
        TreeSet<Card> treeSet = new TreeSet<>();
        Card card1 = new Card(34567, "Butler", Rank.COMMON);
        Card card2 = new Card(23456, "Gate Lodge", Rank.COMMON);
        Card card3 = new Card(12345, "Butler", Rank.COMMON);
        Card card4 = new Card(45678, "Butler", Rank.UNIQUE);

        // Add all cards
        treeSet.add(card1);
        treeSet.add(card2);
        treeSet.add(card3);
        treeSet.add(card4);

        // Expected order: card4, card3, card1, card2
        assert treeSet.first().equals(card4);
        assert treeSet.last().equals(card2);

        System.out.println("TreeSet tests passed!");
    }
}
