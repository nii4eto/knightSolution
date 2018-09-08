package main.java.com.knight.position;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class PositionCalculator {

    public Position calculateLastPosition(InputStream input, Map<Integer, Position> validMoves) {
        if(input == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        List<Integer> allMoves = readFileOfMoves(input);

        Position lastPosition = new Position(0, 0);

        allMoves.forEach(move -> {
            changePosition(validMoves, lastPosition, move);
        });

        return lastPosition;
    }

    public Set<Position> checkForEqualPositions(InputStream input, Map<Integer, Position> validMoves) {
        if(input == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        List<Integer> allMoves = readFileOfMoves(input);

        Set<Position> allEqualPositions = new HashSet<>();
        Position lastPosition = new Position(0, 0);

        allMoves.forEach(move -> {
            changePosition(validMoves, lastPosition, move);

            if (lastPosition.getX() == lastPosition.getY()) {
                allEqualPositions.add(new Position(lastPosition.getX(), lastPosition.getY()));
            }
        });

        return allEqualPositions;
    }

    public  Position calculateFurthestPoint(InputStream input, Map<Integer, Position> validMoves) {
        if(input == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        List<Integer> allMoves = readFileOfMoves(input);

        Position furthestPosition = new Position(0, 0);
        Position lastPosition = new Position(0, 0);

        AtomicReference<Double> furthestDistance = new AtomicReference<Double>(0.0);

        allMoves.forEach(move -> {
            changePosition(validMoves, lastPosition, move);

            double distanceBetweenPoints = Math.sqrt(Math.pow(lastPosition.getX(), 2) + Math.pow(lastPosition.getY(), 2));

            if(furthestDistance.get() < distanceBetweenPoints) {
                furthestDistance.set(distanceBetweenPoints);

                furthestPosition.setX(lastPosition.getX());
                furthestPosition.setY(lastPosition.getY());
            }
        });

        return furthestPosition;
    }

    private List<Integer> readFileOfMoves(InputStream input) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(input))) {

            return br.readLine()
                    .chars()
                    .mapToObj(c -> (char) c)
                    .map(ch -> Character.getNumericValue(ch))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private void changePosition(Map<Integer, Position> validMoves, Position position, Integer move) {
        Position nextPosition = validMoves.get(move);

        if (nextPosition == null) {
            throw new IllegalArgumentException("Next move is not valid: " + move);
        }

        position.setX(position.getX() + nextPosition.getX());
        position.setY(position.getY() + nextPosition.getY());
    }
}
