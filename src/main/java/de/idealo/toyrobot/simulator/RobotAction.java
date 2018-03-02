package de.idealo.toyrobot.simulator;

import de.idealo.toyrobot.exception.ToyRobotException;
import org.springframework.stereotype.Service;

/**
 *
 * @author uysharma
 */
@Service
public class RobotAction {

    private ToyCoordinatePosition coordinatePosition;

    public RobotAction() {
    }

    public RobotAction(ToyCoordinatePosition coordinatePosition) {
        this.coordinatePosition = coordinatePosition;
    }

    public boolean setPosition(ToyCoordinatePosition position) {
        if (position == null)
            return false;

        this.coordinatePosition = position;
        return true;
    }

    public boolean moveNext() throws ToyRobotException {
        return moveNext(coordinatePosition.getNextPosition());
    }

    /**
     * Move a robot 1 step in the facing faceDirection 
     *
     * @param newCoordinatePosition
     * @return 
     */
    public boolean moveNext(ToyCoordinatePosition newCoordinatePosition) {
        if (newCoordinatePosition == null)
            return false;

        // change coordinate coordinatePosition
        this.coordinatePosition = newCoordinatePosition;
        return true;
    }

    public ToyCoordinatePosition getCoordinatePosition() {
        return this.coordinatePosition;
    }

    /**
     * Robot getting rotate 90 degree in left faceDirection
     *
     * @return 
     */
    public boolean turnLeft() {
        if (this.coordinatePosition.faceDirection == null)
            return false;

        this.coordinatePosition.faceDirection = this.coordinatePosition.faceDirection.leftDirection();
        return true;
    }

    /**
     * Robot getting rotate 90 degree in right faceDirection
     *
     * @return 
     */
    public boolean turnRight() {
        if (this.coordinatePosition.faceDirection == null)
            return false;

        this.coordinatePosition.faceDirection = this.coordinatePosition.faceDirection.rightDirection();
        return true;
    }

}
