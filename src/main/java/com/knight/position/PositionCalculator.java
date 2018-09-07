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

        List<String> allMoves = readFileOfMoves(input);

        Position lastPosition = new Position(0, 0);

        allMoves.forEach(move -> {
            Position nextPosition = validMoves.get(Integer.valueOf(move));

            if (nextPosition == null) {
                throw new IllegalArgumentException("Next move is not valid: " + move);
            }

            lastPosition.setX(lastPosition.getX() + nextPosition.getX());
            lastPosition.setY(lastPosition.getY() + nextPosition.getY());
        });

        return lastPosition;
    }

    public Set<Position> checkForEqualPositions(InputStream input, Map<Integer, Position> validMoves) {
        if(input == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        List<String> allMoves = readFileOfMoves(input);

        Set<Position> allEqualPositions = new HashSet<>();
        Position lastPosition = new Position(0, 0);

        allMoves.forEach(move -> {
            Position nextPosition = validMoves.get(Integer.valueOf(move));

            if (nextPosition == null) {
                throw new IllegalArgumentException("Next move is not valid: " + move);
            }

            lastPosition.setX(lastPosition.getX() + nextPosition.getX());
            lastPosition.setY(lastPosition.getY() + nextPosition.getY());

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

        List<String> allMoves = readFileOfMoves(input);

        Position furthestPosition = new Position(0, 0);
        Position lastPosition = new Position(0, 0);

        AtomicReference<Double> furthestDistance = new AtomicReference<Double>(0.0);

        allMoves.forEach(move -> {
            Position nextPosition = validMoves.get(Integer.valueOf(move));

            if (nextPosition == null) {
                throw new IllegalArgumentException("Next move is not valid: " + move);
            }

            lastPosition.setX(lastPosition.getX() + nextPosition.getX());
            lastPosition.setY(lastPosition.getY() + nextPosition.getY());

            double distanceBetweenPoints = Math.sqrt(Math.pow(lastPosition.getX(), 2) + Math.pow(lastPosition.getY(), 2));

            if(furthestDistance.get() < distanceBetweenPoints) {
                furthestDistance.set(distanceBetweenPoints);

                furthestPosition.setX(lastPosition.getX());
                furthestPosition.setY(lastPosition.getY());
            }
        });

        return furthestPosition;
    }

    private List<String> readFileOfMoves(InputStream input) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(input))) {

            return br.readLine()
                    .chars()
                    .mapToObj(c -> (char) c)
                    .map(ch -> String.valueOf(ch))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
            return Collections. <String> emptyList();
        }
    }
}
