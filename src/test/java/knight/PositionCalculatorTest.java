package test.java.knight;

import main.java.com.knight.position.Position;
import main.java.com.knight.position.PositionCalculator;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PositionCalculatorTest {
    private PositionCalculator positionCalculator;

    private Position testLastPosition;
    private Position testFurtherPoint;

    private Map<Integer, Position> validMoves;

    @Before
    public void setup() {
        testLastPosition = new Position(-2,3);
        testFurtherPoint = new Position(0, 4);

        positionCalculator = new PositionCalculator();
        validMoves = populateValidMovesList();
    }

    @Test
    public void testDefineLastPosition() {
        InputStream validStream = PositionCalculatorTest.class.getResourceAsStream("/moves");
        InputStream validTestStream = PositionCalculatorTest.class.getResourceAsStream("/testmoves");

        Position lastPosition = positionCalculator.calculateLastPosition(validStream, validMoves);
        System.out.println("Last position is: " + lastPosition.toString());

        lastPosition = positionCalculator.calculateLastPosition(validTestStream, validMoves);

        assertEquals(testLastPosition, lastPosition);
    }

    @Test
    public void testEqualPositions() {
        InputStream validStream = PositionCalculatorTest.class.getResourceAsStream("/moves");

        Set<Position> equalsPositions = positionCalculator.checkForEqualPositions(validStream, validMoves);

        equalsPositions.forEach(System.out::println);

        assertTrue(equalsPositions.size() > 0);
    }

    @Test
    public void testFurthestPoint() {
        InputStream validStream = PositionCalculatorTest.class.getResourceAsStream("/moves");
        InputStream validTestStream = PositionCalculatorTest.class.getResourceAsStream("/testmoves");

        Position furthestPoint = positionCalculator.calculateFurthestPoint(validStream, validMoves);
        System.out.println("Furthest position is: " + furthestPoint.toString());

        furthestPoint = positionCalculator.calculateFurthestPoint(validTestStream, validMoves);

        assertEquals(testFurtherPoint, furthestPoint);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullInput() {
        positionCalculator.calculateLastPosition(null, validMoves);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInput() {
        InputStream invalidStream = PositionCalculatorTest.class.getResourceAsStream("/invalidmoves");

        positionCalculator.calculateLastPosition(invalidStream, validMoves);
    }

    /**
     * Define all possible moves of the knight
     */
    private Map<Integer, Position> populateValidMovesList() {
        HashMap<Integer, Position> validMoves = new HashMap<>();
        validMoves.put(1, new Position(1, 2));
        validMoves.put(2, new Position(2, 1));
        validMoves.put(3, new Position(2, -1));
        validMoves.put(4, new Position(1, -2));
        validMoves.put(5, new Position(-1, -2));
        validMoves.put(6, new Position(-2, -1));
        validMoves.put(7, new Position(-2, 1));
        validMoves.put(8, new Position(-1, 2));

        return Collections.unmodifiableMap(validMoves);
    }
}
