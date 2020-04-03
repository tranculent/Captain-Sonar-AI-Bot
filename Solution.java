import java.util.*;
import java.io.*;
import java.math.*;

/*
Thoughts:
x -> island
. -> water

Input Command Structure
<Command> <Direction> <Object>
MOVE N TORPEDO

Firing Torpedo Structure:
TORPEDO <x y>| <Command> <Direction>
TORPEDO 3 4|MOVE W

TOPEDO CAN BE FIRED AFTER YOU HAVE DONE 3 ACTIONS

xy 2 2 Target
xy 4 4 Target

Diagonal torpedo shot
- 2 possible ways (determined based on validity of move)
    - First way
        - increment/decrement y until it reaches the target's y
        - increment/decrement x until it reaches the target's x
    - Second way
        - increment/decrement x until it reaches the target's x
        - increment/decrement y until it reaches the target's y
        
        
Create a hashmap that contains all the x/y coordinates of the movements we have made so far. Then
when SURFACE is called, uncheck all the values from the keys of that hashmap.
*/


class Location {
    int x;
    int y;
    
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public int hashCode()
    {
        return x + y;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Location) {
            Location locationObject = (Location) o;
            
            return (locationObject.x == x) && (locationObject.y == y);
        }
        return false;
    }
}


/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {
    public static List<Character> validDirections(int startingX, int startingY, int height, int width, Map<Location, Character> islandLocations) {
        List<Character> directions = new ArrayList<>();
        Map<Character, Boolean> visitedDirection = new HashMap<>();
        visitedDirection.put('E', false);
        visitedDirection.put('W', false);
        visitedDirection.put('N', false);
        visitedDirection.put('S', false);

        for (int i = 0; i < 4; i++) {
            // Check for East
            if (islandLocations.get(new Location(startingX + 1 <= width - 1 ? startingX + 1 : width - 1, startingY)) != 'x' 
            && visitedDirection.get('E') == false
            && startingX + 1 <= width - 1) {
                directions.add('E');
                visitedDirection.put('E', true);
            }
            // Check for West
            else if (islandLocations.get(new Location(startingX - 1 >= 0 ? startingX - 1: 0, startingY)) != 'x' 
            && visitedDirection.get('W') == false
            && startingX - 1 >= 0) {
                directions.add('W');
                visitedDirection.put('W', true);
            }
            // Check for North
            else if (islandLocations.get(new Location(startingX, startingY - 1 >= 0 ? startingY - 1 : 0)) != 'x'
            && visitedDirection.get('N') == false
            && startingY - 1 >= 0) {
                directions.add('N');
                visitedDirection.put('N', true);
            }
            // Check for South
            else if (islandLocations.get(new Location(startingX, startingY + 1 <= height - 1 ? startingY + 1: height - 1)) != 'x'
            && visitedDirection.get('S') == false
            && startingY + 1 <= height - 1) {
                directions.add('S');
                visitedDirection.put('S', true);
            }
        }
        
        return directions;
    }
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int width = in.nextInt();
        int height = in.nextInt();
        int myId = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        
        Map<Location, Character> islandLocations = new HashMap<>();
        
        for (int i = 0; i < height; i++) {
            String line = in.nextLine();
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == 'x') {
                    islandLocations.put(new Location(j, i), 'x');
                }
                else {
                    islandLocations.put(new Location(j, i), '.');
                }
            }
        }

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        System.out.println("7 7");

        // game loop
        while (true) {
            int x = in.nextInt();
            int y = in.nextInt();
            int myLife = in.nextInt();
            int oppLife = in.nextInt();
            int torpedoCooldown = in.nextInt();
            int sonarCooldown = in.nextInt();
            int silenceCooldown = in.nextInt();
            int mineCooldown = in.nextInt();
            String sonarResult = in.next();
            if (in.hasNextLine()) {
                in.nextLine();
            }
            
            String opponentOrders = in.nextLine();
                
            List<Character> validMoves = validDirections(13, 3, height, width, islandLocations);
            
            for (Character c : validMoves) {
                System.err.println(c);   
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            
        }
    }
}
