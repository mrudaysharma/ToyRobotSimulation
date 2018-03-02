package de.idealo.toyrobot.simulator;

import de.idealo.toyrobot.exception.ToyRobotException;

/**
 *
 * @author uysharma
 */
public class ToyCoordinatePosition {
    int coordX;
    int coordY;
    ToyFaceDirection faceDirection;

    public ToyCoordinatePosition(ToyCoordinatePosition toyCoordinatePosition) {
        this.coordX = toyCoordinatePosition.getCoordX();
        this.coordY = toyCoordinatePosition.getCoordY();
        this.faceDirection = toyCoordinatePosition.getFaceDirection();
    }

    public ToyCoordinatePosition(int x, int y, ToyFaceDirection direction) {
        this.coordX = x;
        this.coordY = y;
        this.faceDirection = direction;
    }

    public int getCoordX() {
        return this.coordX;
    }

    public int getCoordY() {
        return this.coordY;
    }

    public ToyFaceDirection getFaceDirection() {
        return this.faceDirection;
    }

    public void setFaceDirection(ToyFaceDirection faceDirection) {
        this.faceDirection = faceDirection;
    }

    public void change(int x, int y) {
        this.coordX = this.coordX + x;
        this.coordY = this.coordY + y;
    }

    public ToyCoordinatePosition getNextPosition() throws ToyRobotException {
        if (this.faceDirection == null)
            throw new ToyRobotException("Invalid robot direction");

        // new position to validate
        ToyCoordinatePosition newPosition = new ToyCoordinatePosition(this);
        switch (this.faceDirection) {
            case NORTH:
                newPosition.change(0, 1);
                break;
            case EAST:
                newPosition.change(1, 0);
                break;
            case SOUTH:
                newPosition.change(0, -1);
                break;
            case WEST:
                newPosition.change(-1, 0);
                break;
        }
        return newPosition;
    }
}
