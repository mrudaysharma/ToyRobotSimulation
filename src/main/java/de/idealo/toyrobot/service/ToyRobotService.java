/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.idealo.toyrobot.service;

import de.idealo.toyrobot.exception.ToyRobotException;
import de.idealo.toyrobot.model.DefaultValues;
import de.idealo.toyrobot.model.ReportModel;
import de.idealo.toyrobot.simulator.RobotAction;
import de.idealo.toyrobot.simulator.SimulationCommand;
import de.idealo.toyrobot.simulator.ToyCoordinatePosition;
import de.idealo.toyrobot.simulator.ToyFaceDirection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author uysharma
 */
@Service
public class ToyRobotService implements ToyRobotInterface {

    public static final Logger logger = LoggerFactory.getLogger(ToyRobotService.class);

    private List<String> commandList;

    @Autowired
    SquareBoardImpl tableBoard;

    @Autowired
    RobotAction robot;

    public ToyRobotService() {
    }

    public ToyRobotService(SquareBoardImpl tableBoard, RobotAction robot) {
        this.tableBoard = tableBoard;
        this.robot = robot;
    }

    @Override
    public void prepareTable(int x, int y) {
        tableBoard.setRows(x);
        tableBoard.setColumns(y);
    }

    @Override
    public List<String> stringCommandsToList(String command) {
        String[] args = command.split(" ");
        List<String> argumentList = new CopyOnWriteArrayList<>(Arrays.asList(args));
        for (String arg : argumentList) {
            if (arg.contains("PLACE")) {
                break;
            }
            logger.info("ROBOT DID NOT PLACED ON TABLE SO NO " + arg);
            argumentList.remove(arg);
        }
        setCommandList(argumentList);
        return argumentList;
    }

    @Override
    public List<ReportModel> simulateToyRobot() throws ToyRobotException {
        String output = "";
        SimulationCommand command;
        List<ReportModel> reportModelList = new ArrayList<>();

        while (commandList.size() != 0 || !commandList.isEmpty()) {
            command = SimulationCommand.valueOf(commandList.get(DefaultValues.LIST_ZERO_INDEX));
            if (command == SimulationCommand.PLACE && commandList.size() < 2) {
                throw new ToyRobotException("INVALID COMMAND");
            }
            // validate PLACE params
            switch (command) {
                case PLACE:
                    performPlaceOperation(command.toString());
                    removeCommand(DefaultValues.LIST_FIRST_INDEX);
                    removeCommand(DefaultValues.LIST_ZERO_INDEX);
                    break;
                case MOVE:
                    performMoveOperation(command.toString());
                    //After reading MOVE command remove it from list
                    removeCommand(DefaultValues.LIST_ZERO_INDEX);
                    break;
                case LEFT:
                    performLeftOperation(command.toString());
                    //After reading LEFT command remove it from list
                    removeCommand(DefaultValues.LIST_ZERO_INDEX);

                    break;
                case RIGHT:
                    performRightOperation(command.toString());
                    //After reading LEFT command remove it from list
                    removeCommand(DefaultValues.LIST_ZERO_INDEX);
                    break;
                case REPORT:
                    reportModelList.add(generateReport(command.toString()));
                    logger.info("SIMULATION RESULT " + output);

                    //After reading REPORT command remove it from list
                    removeCommand(DefaultValues.LIST_ZERO_INDEX);
                    break;
                default:
                    throw new ToyRobotException("INVALID COMMAND");
            }
        }

        return reportModelList;
    }

    /**
     * Places the robot on the tableBoard on coordinate X,Y and facing direction
     * NORTH, SOUTH, EAST or WEST
     *
     * @param position RobotAction position
     * @return true if placed successfully
     * @throws ToyRobotException
     */
    public boolean placeToyRobot(ToyCoordinatePosition position) throws ToyRobotException {

        if (tableBoard == null) {
            throw new ToyRobotException("Invalid squareBoard object");
        }

        if (position == null) {
            throw new ToyRobotException("Invalid position object");
        }

        if (position.getFaceDirection() == null) {
            throw new ToyRobotException("Invalid direction value");
        }

        // validate the position
        if (!tableBoard.isValidCoordinatePosition(position)) {
            return false;
        }

        // if position is valid then assign values to fields
        robot.setPosition(position);
        return true;
    }

    @Override
    public String performPlaceOperation(String command) {
        String[] params;
        int coordX = 0;
        int coordY = 0;
        ToyFaceDirection commandDirection = null;
        try {

            params = commandList.get(1).split(",");
            if (params.length < 3 || params.length > 3) {
                throw new ToyRobotException("INVALID COMMAND");
            }
            if (!params[2].contains("NORTH") && !params[2].contains("SOUTH") && !params[2].contains("EAST") && !params[2].contains("WEST")) {
                throw new ToyRobotException("INVALID COMMAND");
            }
            try {
                coordX = Integer.parseInt(params[0]);
                coordY = Integer.parseInt(params[1]);
                commandDirection = ToyFaceDirection.valueOf(params[2]);
            } catch (Exception e) {
                throw new ToyRobotException("INVALID COMMAND");
            }

            String.valueOf(placeToyRobot(new ToyCoordinatePosition(coordX, coordY, commandDirection)));
            logger.info("ROBOT  ON COORDINATES X=" + coordX + " Y=" + coordY + " FACE DIRECTION=" + commandDirection);

        } catch (ToyRobotException ex) {
            return ex.getMessage();
        }

        return "ROBOT  ON COORDINATES X=" + coordX + " Y=" + coordY + " FACE DIRECTION=" + commandDirection;
    }

    @Override
    public String performMoveOperation(String command) {
        ToyCoordinatePosition newPosition = null;
        try {
            if (robot.getCoordinatePosition() == null) {
                throw new ToyRobotException(("ROBOT IS NOT PLACED"));
            }
            newPosition = robot.getCoordinatePosition().getNextPosition();
            if (!tableBoard.isValidCoordinatePosition(newPosition)) {
                String.valueOf(false);
                logger.info("ROBOT " + command + " ON COORDINATES X=" + newPosition.getCoordX() + " Y=" + newPosition.getCoordY() + " FACE DIRECTION=" + newPosition.getFaceDirection() + " NOT POSSIBLE");
            } else {
                logger.info("ROBOT " + command + " ON COORDINATES X=" + newPosition.getCoordX() + " Y=" + newPosition.getCoordY() + " FACE DIRECTION=" + newPosition.getFaceDirection());
                String.valueOf(robot.moveNext(newPosition));
            }
        } catch (ToyRobotException ex) {
            logger.error(ex.getMessage());
            // java.util.logging.Logger.getLogger(ToyRobotService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "ROBOT " + command + " ON COORDINATES X=" + newPosition.getCoordX() + " Y=" + newPosition.getCoordY() + " FACE DIRECTION=" + newPosition.getFaceDirection();
    }

    @Override
    public String performLeftOperation(String command) {
        String validateOutput = null;
        try {
            if (robot.getCoordinatePosition() == null) {

                throw new ToyRobotException(("ROBOT IS NOT PLACED"));
            }
            validateOutput = String.valueOf(robot.turnLeft());
            logger.info("ROBOT FACE " + command);
        } catch (ToyRobotException ex) {
            logger.error(ex.getMessage());
            // java.util.logging.Logger.getLogger(ToyRobotService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return validateOutput;
    }

    @Override
    public String performRightOperation(String command) {
        String validateOutput = String.valueOf(robot.turnRight());
        logger.info("ROBOT FACE " + command);
        return validateOutput;
    }

    @Override
    public ReportModel generateReport(String command) {
        if (robot.getCoordinatePosition() == null) {
            return null;
        }

        return new ReportModel(robot.getCoordinatePosition().getCoordX(), robot.getCoordinatePosition().getCoordY(), robot.getCoordinatePosition().getFaceDirection().toString());

    }

    /**
     * @return the commandList
     */
    public List<String> getCommandList() {
        return commandList;
    }

    /**
     * @param commandList the commandList to set
     */
    public void setCommandList(List<String> commandList) {
        this.commandList = commandList;
    }

    public void removeCommand(int index) {
        commandList.remove(index);
    }

}
