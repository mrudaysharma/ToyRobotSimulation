package de.idealo.toyrobot.simulator;


import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author uysharma
 */
public enum ToyFaceDirection {

    NORTH(0), EAST(1), SOUTH(2), WEST(3);
    private static Map<Integer, ToyFaceDirection> toyDirectionMap = new HashMap<>();

    static {
        for (ToyFaceDirection directionEnum : ToyFaceDirection.values()) {
            toyDirectionMap.put(directionEnum.faceDirectionIndex, directionEnum);
        }
    }

    private int faceDirectionIndex;

    private ToyFaceDirection(int direction) {
        this.faceDirectionIndex = direction;
    }

    public static ToyFaceDirection valueOf(int directionNum) {
        return toyDirectionMap.get(directionNum);
    }

    public ToyFaceDirection leftDirection() {
        return rotate(-1);
    }

   
    public ToyFaceDirection rightDirection() {
        return rotate(1);
    }

    private ToyFaceDirection rotate(int step) {

        int newIndex = (this.faceDirectionIndex + step) < 0 ?
                toyDirectionMap.size() - 1 :
                (this.faceDirectionIndex + step) % toyDirectionMap.size();

        return ToyFaceDirection.valueOf(newIndex);
    }

}
