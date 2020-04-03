Map<Integer, List<Location>> sectors = new HashMap();
        List<Location> coords = new ArrayList<>();
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                // 1/2/3 sector
                if (x < 5) {
                    if (y < 5) {
                        coords.add(new Location(y, x));
                        if (y == 4 && x == 4) {
                            ArrayList copy = new ArrayList(coords);
                            sectors.put(1, copy);
                            coords.clear();
                        }
                    }
                    else if (y > 4 && y < 10) {
                        coords.add(new Location(y, x));
                        if (y == 9 && x == 4) {
                            ArrayList copy = new ArrayList(coords);
                            sectors.put(2, copy);   
                            coords.clear();
                        }
                    }
                    else if (y > 9 && y < 15) {
                        coords.add(new Location(y,x));
                        if (y == 14 && x == 4) {
                            ArrayList copy = new ArrayList(coords);
                            sectors.put(3, copy);
                            coords.clear();
                        }
                    }
                }
                // 4/5/6 sector
                else if (x > 4 && x < 10) {
                   if (y < 5) {
                       coords.add(new Location(y,x));
                        if (y == 4) {
                            sectors.put(4, coords);
                            coords.clear();
                        }  
                    }
                    else if (y > 4 && y < 10) {
                        coords.add(new Location(y,x));
                        if (y == 9) {
                            sectors.put(5, coords);
                            coords.clear();
                        } 
                    }
                    else if (y > 9 && y < 15) {
                        coords.add(new Location(y,x));
                        if (y == 14) {
                            sectors.put(6, coords);
                            coords.clear();
                        } 
                    }
                }
                // 7/8/9 sector
                else if (x > 9 && x < 15) {
                   if (y < 5) {
                       coords.add(new Location(y,x));
                        if (y == 4) {
                            sectors.put(7, coords);
                            coords.clear();
                        }  
                    }
                    else if (y > 4 && y < 10) {
                        coords.add(new Location(y,x));
                        if (y == 9) {
                            sectors.put(8, coords);
                            coords.clear();
                        }  
                    }
                    else if (y > 9 && y < 15) {
                        coords.add(new Location(y,x));
                        if (y == 14) {
                            sectors.put(9, coords);
                            coords.clear();
                        }  
                    }
                }
            }
        }
