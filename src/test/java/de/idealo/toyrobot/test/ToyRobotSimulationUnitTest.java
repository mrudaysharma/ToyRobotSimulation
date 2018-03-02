/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.idealo.toyrobot.test;

import de.idealo.toyrobot.controller.RestToyRobotController;
import de.idealo.toyrobot.exception.ToyRobotException;
import de.idealo.toyrobot.model.ReportModel;
import de.idealo.toyrobot.service.SquareBoardImpl;
import de.idealo.toyrobot.service.ToyRobotService;
import de.idealo.toyrobot.simulator.RobotAction;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author uysharma
 */
public class ToyRobotSimulationUnitTest {

    public static final Logger logger = LoggerFactory.getLogger(RestToyRobotController.class);

    private ToyRobotService toyRobotService;
    private SquareBoardImpl squareBoard;
    private RobotAction robotAction;

    @BeforeClass
    public static void onceExecutedBeforeAll() {

    }

    @Before
    public void executedBeforeEach() {
        squareBoard = new SquareBoardImpl(5, 5);
        robotAction = new RobotAction();
        toyRobotService = new ToyRobotService(squareBoard, robotAction);
    }

    /**
     * PLACE 0,0,NORTH MOVE REPORT Output: 0,1,NORTH
     */
    @Test
    public void afirstCommandToToyRobotTest() {
        try {

            String command = "PLACE 0,0,NORTH MOVE REPORT";
            
            if (!command.contains("PLACE")) {
                throw new ToyRobotException(("ROBOT MISSING"));
            }

            String listString = runSimulation(command);
            generateReport(command, listString, "FIRST REPORT");
        } catch (ToyRobotException ex) {
           logger.error(ex.getMessage());
        }

    }

    /**
     * PLACE 0,0,NORTH LEFT REPORT Output: 0,0,WEST
     */
    @Test
    public void bsecondCommandToToyRobotTest() {
        try {

            String command = "PLACE 0,0,NORTH LEFT REPORT";
            
            if (!command.contains("PLACE")) {
                throw new ToyRobotException(("ROBOT MISSING"));
            }


            String listString = runSimulation(command);

            generateReport(command, listString, "SECOND REPORT");

        } catch (ToyRobotException ex) {
           logger.error(ex.getMessage());
        }

    }

    /**
     * PLACE 1,2,EAST MOVE MOVE LEFT MOVE REPORT Output: 3,3,NORTH
     */
    @Test
    public void cthirdCommandToToyRobotTest() {
        try {

            String command = "PLACE 1,2,EAST MOVE MOVE LEFT MOVE REPORT";
            
            if (!command.contains("PLACE")) {
                throw new ToyRobotException(("ROBOT MISSING"));
            }


            String listString = runSimulation(command);

            generateReport(command, listString, "THIRD REPORT");

        } catch (ToyRobotException ex) {
           logger.error(ex.getMessage());
        }

    }

    /**
     * MOVE REPORT Output: ROBOT MISSING
     */
    @Test
    public void dfourthCommandToToyRobotTest() {
        try {

            String command = "MOVE REPORT";

            if (!command.contains("PLACE")) {
                throw new ToyRobotException(("ROBOT MISSING"));
            }

            String listString = runSimulation(command);

            generateReport(command, listString, "FOURTH REPORT");

        } catch (ToyRobotException ex) {
            logger.error(ex.getMessage());
        }

    }

    /**
     * MOVE REPORT PLACE 1,2,EAST PLACE 0,0,NORTH MOVE REPORT PLACE 0,0,NORTH
     * LEFT REPORT PLACE 1,2,EAST MOVE MOVE LEFT MOVE REPORT Output: 0,1,NORTH
     * Output: 0,0,WEST Output: 3,3,NORTH
     */
    @Test
    public void efifthCommandToToyRobotTest() {
        try {

            String command = "MOVE REPORT PLACE 1,2,EAST PLACE 0,0,NORTH MOVE REPORT PLACE 0,0,NORTH LEFT REPORT PLACE 1,2,EAST MOVE MOVE LEFT MOVE REPORT";

            if (!command.contains("PLACE")) {
                throw new ToyRobotException(("ROBOT MISSING"));
            }

            String listString = runSimulation(command);

            generateReport(command, listString, "FIFTH REPORT");

        } catch (ToyRobotException ex) {
            logger.error(ex.getMessage());
        }

    }

    /**
     * PLACE 0,0,NORTH MOVE MOVE MOVE MOVE MOVE MOVE MOVE MOVE MOVE MOVE MOVE
     * MOVE MOVE MOVE MOVE MOVE MOVE MOVE RIGHT MOVE REPORT Output: 1,5,EAST
     */
    @Test
    public void fsixthCommandToToyRobotTest() {
        try {

            String command = "PLACE 0,0,NORTH MOVE MOVE MOVE MOVE MOVE MOVE MOVE MOVE MOVE MOVE MOVE MOVE MOVE MOVE MOVE MOVE MOVE MOVE RIGHT MOVE REPORT ";

            if (!command.contains("PLACE")) {
                throw new ToyRobotException(("ROBOT MISSING"));
            }

            String listString = runSimulation(command);
            assertEquals("{1,5,EAST}",listString.trim());

            generateReport(command, listString, "SIXTH REPORT");

        } catch (ToyRobotException ex) {
            logger.error(ex.getMessage());
        }

    }

    /**
     * PLACE 0,southeast LEFT REPORT Output: Invalid command file
     */
    @Test
    public void gseventhCommandToToyRobotTest() {
        try {

            String command = "PLACE 0,southeast LEFT REPORT";

            if (!command.contains("PLACE")) {
                throw new ToyRobotException(("ROBOT MISSING"));
            }

            String listString = runSimulation(command);

            generateReport(command, listString, "SEVENTH REPORT");

        } catch (ToyRobotException ex) {
            logger.error("SEVENTH REPORT"+ex.getMessage());
        } catch(Exception e)
        {
             logger.error("SEVENTH REPORT COMMAND IS INCORRECT");
        }

    }

    private String runSimulation(String command) throws ToyRobotException {
        List<String> commandList = toyRobotService.stringCommandsToList(command);
        assertNotNull(commandList);
        List<ReportModel> reportModelList = toyRobotService.simulateToyRobot();
        String listString = "";
        listString = reportModelList.stream().map((reportModel) -> reportModel.toString()).reduce(listString, String::concat);
        return listString;
    }

    private void generateReport(String command, String listString, String reportNumber) {
        logger.info("\n************" + reportNumber + "****************");
        logger.info("\nCOMMAND IS " + command);
        logger.info("\nREPORT IS " + listString);
        logger.info("\n********************************************");
    }

}
