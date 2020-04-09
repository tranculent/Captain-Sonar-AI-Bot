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
    
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int width = in.nextInt();
        int height = in.nextInt();
        int myId = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        
        Map<Location, Character> islandLocations = storeMap(height, width, in);
        
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
                    
                    List<Location> temp = getAvailableTorpedoLocations(x, y, height, width, islandLocations);
                
                    numberOfMoves++;
                    if (numberOfMoves == 2) {
                        int randomTorpedoLocation = new Random().nextInt(temp.size());
                        Location finalTorpedoLocation = temp.get(randomTorpedoLocation);
    
                        System.out.println("MOVE " + validMoves.get(random) + " TORPEDO" + " | " +"TORPEDO " + finalTorpedoLocation.x + " " + finalTorpedoLocation.y);
                        numberOfMoves = 0;
                    }
                    else {
                        
                        System.out.println("MOVE " + validMoves.get(random) + " TORPEDO");
                    }
                }
                else {
                    System.out.println("SURFACE");
                    visitedLocations.clear();
                    visitedLocations.add(new Location(x, y));
                }
            }
        }
    }
    
    public static Map<Location, Character> storeMap(int height, int width, Scanner in) {
        Map<Location, Character> map = new HashMap<>();
        for (int i = 0; i < height; i++) {
            String line = in.nextLine();
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == 'x') {
                    map.put(new Location(j, i), 'x');
                }
                else {
                    map.put(new Location(j, i), '.');
                }
            }
        }
        return map;
    }
    
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
    
    public static List<Location> getAvailableTorpedoLocations(int x, int y, int height, int width, Map<Location, Character> islandLocations) {
        List<Location> availableTorpedoLocations = new ArrayList<Location>();
        int radius = 4;
                
        // handle NorthEast
        int copyX = x;
        for (int i = y - 5; i < y; i++) {
            int copyI = i;
            
            while (copyI++ < y) {
               Location newLocation = new Location(copyI, copyX);
               if (islandLocations.containsKey(newLocation))
               {
                   if (islandLocations.get(newLocation) != 'x') {
                        availableTorpedoLocations.add(newLocation);
                   }
               }
            }
            copyX--;
        }
        
        // handle NorthWest
        int copyX1 = x;
        for (int i = y + 5; i > y; i--) {
            int copyI = i;
            
            while (copyI-- > y) {
               Location newLocation = new Location(copyI, copyX1);
               if (islandLocations.containsKey(newLocation))
               {
                   if (islandLocations.get(newLocation) != 'x') {
                        availableTorpedoLocations.add(newLocation);
                   }
               }
            }
            copyX1--;
        }
        
        // handle SouthWest
        int copyX3 = x - 5;
        int counter = 0;
        for (int i = y; i < y + 5; i++) {
            int copyI = i;
            while (copyX3++ < x) {
                Location newLocation = new Location(copyX3, copyI);
                if (islandLocations.containsKey(newLocation))
               {
                   if (islandLocations.get(newLocation) != 'x') {
                        availableTorpedoLocations.add(newLocation);
                   }
               }
            }
            counter++;
            copyX3 = (x - 5) + counter;
        }
        
        // handle SouthEast
        int copyX2 = x;
        for (int i = y + 5; i > y; i--) {
            int copyI = i;
            
            while (copyI-- > y) {
                Location newLocation = new Location(copyI, copyX2);
                if (islandLocations.containsKey(newLocation))
                {
                   if (islandLocations.get(newLocation) != 'x') {
                        availableTorpedoLocations.add(newLocation);
                   }
                }
            }
            
            copyX2++;
        }
        
        return availableTorpedoLocations;
    }
}
