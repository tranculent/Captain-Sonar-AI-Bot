import java.util.*;
import java.io.*;
import java.math.*;

/*
Notes:
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
    // TODO:
    
    /* 
    Get the location of all cells in radius 4.
    */
    
    static List<Location> visitedLocations = new ArrayList<>();
    static int DIRECTIONS = 4;
    
    public static List<Character> validDirections(int startingX, int startingY, int height, int width, Map<Location, Character> islandLocations) {
        List<Character> directions = new ArrayList<>();
        Map<Character, Boolean> visitedDirection = new HashMap<>();
        visitedDirection.put('E', false);
        visitedDirection.put('W', false);
        visitedDirection.put('N', false);
        visitedDirection.put('S', false);

        for (int i = 0; i < DIRECTIONS; i++) {
            // Check for East
            if (islandLocations.get(new Location(startingX + 1 <= width - 1 ? startingX + 1 : width - 1, startingY)) != 'x' 
            && visitedDirection.get('E') == false
            && !visitedLocations.contains(new Location(startingX + 1 <= width - 1 ? startingX + 1 : width - 1, startingY))) {
                directions.add('E');
                visitedDirection.put('E', true);
            }
            // Check for West
            else if (islandLocations.get(new Location(startingX - 1 >= 0 ? startingX - 1: 0, startingY)) != 'x' 
            && visitedDirection.get('W') == false
            && !visitedLocations.contains(new Location(startingX - 1 >= 0 ? startingX - 1: 0, startingY))) {
                directions.add('W');
                visitedDirection.put('W', true);
            }
            // Check for North
            else if (islandLocations.get(new Location(startingX, startingY - 1 >= 0 ? startingY - 1 : 0)) != 'x'
            && visitedDirection.get('N') == false
            && !visitedLocations.contains(new Location(startingX, startingY - 1 >= 0 ? startingY - 1 : 0))) {
                directions.add('N');
                visitedDirection.put('N', true);
            }
            // Check for South
            else if (islandLocations.get(new Location(startingX, startingY + 1 <= height - 1 ? startingY + 1: height - 1)) != 'x'
            && visitedDirection.get('S') == false
            && !visitedLocations.contains(new Location(startingX, startingY + 1 <= height - 1 ? startingY + 1: height - 1))) {
                directions.add('S');
                visitedDirection.put('S', true);
            }
        }
        
        return directions;
    }
    
    public static List<Location> getTorpedoLocations(int x, int y, int height, int width, Map<Location, Character> islandLocations) {
        List<Location> locations = new ArrayList<Location>();
        int radius = 4;
        int east = x, west = x;
        int south = y, north = y;
        
        // 4 places to the left (west)/4 places to the right (east)/4 places to the top (north)/4 places to the bottom (south)
        // while (radius-- > 0) {
            // Location newEast = new Location((east++) + 1, y);
            // Location newWest = new Location((west--) - 1, y);
            // Location newNorth = new Location(x, (north--) - 1);
            // Location newSouth = new Location(x, (south++) + 1);
            
            
            int northRow = y > 0 ? y - 1 : 0;
            int eastRow = x < width - 1 ? x + 1 : width - 1;
            int westRow = x > 0 ? x - 1 : 0;
            int southRow = y < height - 1 ? y + 1 : height;
            
            // handle NorthEast
            // Start with: Location (y-1, x+1)
            for (int i = northRow; i > northRow - 4; i--) {
                System.err.println("In north east");
                for (int j = eastRow; j < eastRow + 4; j++) {
                    Location newLocation = new Location(y,x);
                    if (islandLocations.containsKey(newLocation)) {
                        if (islandLocations.get(newLocation) != 'x') {
                            locations.add(newLocation);
                        }
                    }
                }
            }
            
            // handle NorthWest
            // Start with: Location (y-1, x-1)
            for (int i = northRow; i > northRow - 4; i--) {
                System.err.println("In north west");
                for (int j = westRow; j > westRow - 4; j--) {
                    Location newLocation = new Location(y,x);
                    if (islandLocations.containsKey(newLocation)) {
                        if (islandLocations.get(newLocation) != 'x') {
                            locations.add(newLocation);
                        }
                    }
                }
            }
            
            // handle SouthEast
            for (int i = southRow; i < southRow + 4; i++) {
                System.err.println("In south east");
                for (int j = eastRow; j > eastRow - 4; j--) {
                    Location newLocation = new Location(y,x);
                    if (islandLocations.containsKey(newLocation)) {
                        if (islandLocations.get(newLocation) != 'x') {
                            locations.add(newLocation);
                        }
                    }
                }
            }
            
            // handle SouthWest
            for (int i = southRow; i < southRow + 4; i++) {
                System.err.println("In south west");
                for (int j = eastRow; j < eastRow + 4; j++) {
                    Location newLocation = new Location(y,x);
                    if (islandLocations.containsKey(newLocation)) {
                        if (islandLocations.get(newLocation) != 'x') {
                            locations.add(newLocation);
                        }
                    }
                }
            }
            
            // Check for an island 'x' mark.
            
            // if (islandLocations.containsKey(newWest)) {
            //     if (islandLocations.get(newWest) != 'x') {
            //         // Add west
            //         locations.add(newWest);
            //     }
            // }
            // if (islandLocations.containsKey(newNorth)) {
            //     if (islandLocations.get(newNorth) != 'x') {
            //         // Add north
            //         locations.add(newNorth);
            //     }
            // }
            // if (islandLocations.containsKey(newSouth)) {
            //     if (islandLocations.get(newSouth) != 'x') {
            //         // Add south
            //         locations.add(newSouth);
            //     }
            // }
        // }
        
        return locations;
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

        System.out.println("7 7");
        
        int numberOfMoves = 0;
        int spawn = 0;
        
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
            
            List<Character> validMoves = validDirections(x, y, height, width, islandLocations);
            
            if (spawn++ == 0) visitedLocations.add(new Location(x, y));
                // Run command based on the opponents order
            if (!sonarResult.equals("NA")) {
                
            } 
            else {
                int random;
                if (!validMoves.isEmpty()) {
                    random = new Random().nextInt(validMoves.size());
                    if (validMoves.get(random) == 'E') {
                       visitedLocations.add(new Location(x + 1, y));
                    }
                    else if (validMoves.get(random) == 'W') {
                        visitedLocations.add(new Location(x - 1, y));
                    }
                    else if (validMoves.get(random) == 'N') {
                        visitedLocations.add(new Location(x, y - 1));
                    }
                    else if (validMoves.get(random) == 'S') {
                        visitedLocations.add(new Location(x, y + 1));
                    }
                    
                    List<Location> temp = getTorpedoLocations(x, y, height, width, islandLocations);
                    
                    for (Location l : temp) {
                     System.err.println("x: " + l.x + " ; y:" + l.y);   
                    }
                    
                    System.out.println("MOVE " + validMoves.get(random) + " TORPEDO");
                }
                // If no moves are available
                else {
                    System.out.println("SURFACE");
                    visitedLocations.clear();
                    visitedLocations.add(new Location(x, y));
                }
                // If numberOfMoves < 3 (we can't fire a torpedo), move towards opponent
                // If numberOfMoves == 3 (we have torpedo), look for the opponent sector (by using the sonarResult)0
            }
        }
    }
}
