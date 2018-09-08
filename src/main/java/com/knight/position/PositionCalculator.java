package main.java.com.knight.position;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Responsible for defining the
 * position of the knight
 */
public class PositionCalculator {

    /**
     * Calculate the position of the knight figure
     * after executing all provided moves
     */
    public Position calculateLastPosition(InputStream input, Map<Integer, Position> validMoves) {
        if(input == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(input))) {
            Position lastPosition = new Position(0, 0);

             br.readLine()
                    .chars()
                    .mapToObj(c -> (char) c)
                    .map(ch -> Character.getNumericValue(ch))
                     .forEach(move -> {
                         changePosition(validMoves, lastPosition, move);
                     });
             return lastPosition;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Define all positions where x=y
     */
    public Set<Position> checkForEqualPositions(InputStream input, Map<Integer, Position> validMoves) {
        if(input == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(input))) {
            Set<Position> allEqualPositions = new HashSet<>();
            Position lastPosition = new Position(0, 0);

            br.readLine()
                    .chars()
                    .mapToObj(c -> (char) c)
                    .map(ch -> Character.getNumericValue(ch))
                    .forEach(move -> {
                        changePosition(validMoves, lastPosition, move);

                        if (lastPosition.getX() == lastPosition.getY()) {
                            allEqualPositions.add(new Position(lastPosition.getX(), lastPosition.getY()));
                        }
                    });
            return allEqualPositions;

        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptySet();
        }

    }

    /**
     * Calculate furthest point on the board from the initial position (0, 0)
     * that the figure has ever been while executing the list of instructions
     *
     * Sqrt((x1-x2)^2 + (y1 -y2)^2)
     */
    public  Position calculateFurthestPoint(InputStream input, Map<Integer, Position> validMoves) {
        if(input == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(input))) {
            Position furthestPosition = new Position(0, 0);
            Position lastPosition = new Position(0, 0);

            AtomicReference<Double> furthestDistance = new AtomicReference<Double>(0.0);

            br.readLine()
                    .chars()
                    .mapToObj(c -> (char) c)
                    .map(ch -> Character.getNumericValue(ch))
                    .forEach(move -> {
                        changePosition(validMoves, lastPosition, move);

                        double distanceBetweenPoints = Math.sqrt(Math.pow(lastPosition.getX(), 2) + Math.pow(lastPosition.getY(), 2));

                        if(furthestDistance.get() < distanceBetweenPoints) {
                            furthestDistance.set(distanceBetweenPoints);

                            furthestPosition.setX(lastPosition.getX());
                            furthestPosition.setY(lastPosition.getY()); }
                    });
            return furthestPosition;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Based on the next move calculate last position
     */
    private void changePosition(Map<Integer, Position> validMoves, Position position, Integer move) {
        Position nextPosition = validMoves.get(move);

        if (nextPosition == null) {
            throw new IllegalArgumentException("Next move is not valid: " + move);
        }

        position.setX(position.getX() + nextPosition.getX());
        position.setY(position.getY() + nextPosition.getY());
    }

}
