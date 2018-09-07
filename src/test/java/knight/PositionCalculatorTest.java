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
    private InputStream validStream;
    private InputStream invalidStream;
    private InputStream validTestStream;
    private PositionCalculator positionCalculator;

    private Position testLastPosition;

    private Map<Integer, Position> validMoves;

    @Before
    public void setup() {
        validStream = PositionCalculatorTest.class.getResourceAsStream("/moves");
        invalidStream = PositionCalculatorTest.class.getResourceAsStream("/invalidmoves");
        validTestStream = PositionCalculatorTest.class.getResourceAsStream("/testmoves");

        testLastPosition = new Position(0,4);

        positionCalculator = new PositionCalculator();
        validMoves = populateValidMovesList();
    }

    @Test
    public void testValidMoves() {
        Position lastPosition = positionCalculator.calculateLastPosition(validStream, validMoves);
        System.out.println("Last position is: " + lastPosition.toString());

        lastPosition = positionCalculator.calculateLastPosition(validTestStream, validMoves);

        assertTrue(testLastPosition.equals(lastPosition));
    }

    @Test
    public void testEqualPositions() {
        Set<Position> equalsPositions = positionCalculator.checkForEqualPositions(validStream, validMoves);

        equalsPositions.forEach(System.out::println);

        assertTrue(equalsPositions.size() > 0);
    }

    @Test
    public void testFurthestPoint() {
        Position furthestPoint = positionCalculator.calculateFurthestPoint(validStream, validMoves);

        System.out.println(furthestPoint.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullInput() {
        positionCalculator.calculateLastPosition(null, validMoves);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInput() {
        positionCalculator.calculateLastPosition(invalidStream, validMoves);
    }

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