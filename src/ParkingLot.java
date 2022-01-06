/**
 * Design a parking lot.
 *
 * see CC150 OO Design for details.
 *
 * n levels, each level has m rows of spots and each row has k spots.So each level has m x k spots.
 * The parking lot can park motorcycles, cars and buses
 * The parking lot has motorcycle spots, compact spots, and large spots
 * Each row, motorcycle spots id is in range [0,k/4)(0 is included, k/4 is not included), compact spots id is in range
 * [k/4,k/4*3)(k/4*3 is not included) and large spots id is in range [k/4*3,k)(k is not included).
 * A motorcycle can park in any spot
 * A car park in single compact spot or large spot
 * A bus can park in five large spots that are consecutive and within same row. it can not park in small spots
 */

import java.util.*;

// enum type for Vehicle
enum VehicleSize {
    Motorcycle,
    Compact,
    Large,
}

abstract class Vehicle {
    protected int spotNeed;
    protected VehicleSize vs;
    protected Level level;
    protected List<ParkingSpot> spots = new ArrayList<ParkingSpot>();

    public void setLevel(Level level) {
        this.level = level;
    }
    public int getSpot() {
        return this.spotNeed;
    }
    public VehicleSize getSize() {
        return this.vs;
    }
    public void park(ParkingSpot spot) {
        this.spots.add(spot);
    }
    public void clearSpots() {
        for (ParkingSpot spot : spots) {
            spot.removeVehicle();
        }
        level.freeSpot(this.spotNeed);
        level = null;
        spots.clear();
    }
    public abstract boolean canFit(ParkingSpot spot);
}

class Motorcycle extends Vehicle {
    public Motorcycle() {
        this.spotNeed = 1;
        this.vs = VehicleSize.Motorcycle;
    }
    public boolean canFit(ParkingSpot spot) {
        return true;
    }
}

class Car extends Vehicle {
    public Car() {
        this.spotNeed = 1;
        this.vs = VehicleSize.Compact;
    }
    public boolean canFit(ParkingSpot spot) {
        return spot.getSize() == VehicleSize.Compact || spot.getSize() == VehicleSize.Large;
    }
}

class Bus extends Vehicle {
    public Bus() {
        this.spotNeed = 5;
        this.vs = VehicleSize.Large;
    }
    public boolean canFit(ParkingSpot spot) {
        return spot.getSize() == VehicleSize.Large;
    }
}

class ParkingSpot {
    private Vehicle vehicle;
    private VehicleSize vs;

    public ParkingSpot(VehicleSize vs) {
        this.vs = vs;
    }

    public boolean canFit(Vehicle vehicle) {
        return this.vehicle == null && vehicle.canFit(this);
    }

    public boolean park(Vehicle vehicle) {
        if (canFit(vehicle)) {
            this.vehicle = vehicle;
            vehicle.park(this);
            return true;
        }
        return false;
    }

    public void removeVehicle() {
        this.vehicle = null;
    }

    public VehicleSize getSize() {
        return this.vs;
    }
}

/* Represents a level in a parking garage */
class Level {
    private ParkingSpot[][] spots;
    private int availableSpots = 0;

    public Level(int rows, int cols) {
        spots = new ParkingSpot[rows][cols];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                if (j < cols / 4) {
                    spots[i][j] = new ParkingSpot(VehicleSize.Motorcycle);
                } else if (j < cols / 4 * 3) {
                    spots[i][j] = new ParkingSpot(VehicleSize.Compact);
                } else {
                    spots[i][j] = new ParkingSpot(VehicleSize.Large);
                }
            }
        }
        this.availableSpots = rows * cols;
    }

    public boolean park(Vehicle vehicle) {
        int spotNeed = vehicle.getSpot();
        if (availableSpots < spotNeed) {
            return false;
        }
        for (int i = 0; i < spots.length; ++i) {
            for (int j = 0; j <= spots[0].length - spotNeed; ++j) {
                if (checkAndPark(spots, i, j, spotNeed, vehicle)) {
                    vehicle.setLevel(this);
                    availableSpots -= spotNeed;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkAndPark(ParkingSpot[][] spots, int x, int y, int size, Vehicle vehicle) {
        for (int i = y; i < y + size; ++i) {
            if (!spots[x][i].canFit(vehicle)) {
                return false;
            }
        }
        for (int i = y; i < y + size; ++i) {
            spots[x][i].park(vehicle);
        }
        return true;
    }

    public void freeSpot(int size) {
        availableSpots += size;
    }
}

public class ParkingLot {
    private Level[] level;
    // @param n number of leves
    // @param num_rows  each level has num_rows rows of spots
    // @param spots_per_row each row has spots_per_row spots
    public ParkingLot(int n, int num_rows, int spots_per_row) {
        level = new Level[n];
        for (int i = 0; i < n; ++i) {
            level[i] = new Level(num_rows, spots_per_row);
        }
    }

    // Park the vehicle in a spot (or multiple spots)
    // Return false if failed
    public boolean parkVehicle(Vehicle vehicle) {
        for (int i = 0; i < level.length; ++i) {
            if (level[i].park(vehicle)) {
                return true;
            }
        }
        return false;
    }

    // unPark the vehicle
    public void unParkVehicle(Vehicle vehicle) {
        vehicle.clearSpots();
    }
}