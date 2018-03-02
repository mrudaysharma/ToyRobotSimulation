package de.idealo.toyrobot.controller;

import de.idealo.toyrobot.exception.ToyRobotException;
import de.idealo.toyrobot.model.DefaultValues;
import de.idealo.toyrobot.model.ReportModel;
import de.idealo.toyrobot.model.ToyCommandModel;
import de.idealo.toyrobot.service.ToyRobotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author uysharma
 */
@RestController
@RequestMapping("/toyRobot")
@Api(value = "ToyRobotRestController", description = "Rest Controller which takes command by using GET, POST and MULTIPART FILE upload")
public class RestToyRobotController {

    public static final Logger logger = LoggerFactory.getLogger(RestToyRobotController.class);

    @Autowired
    ToyRobotService toyRobotServie;

    @ApiOperation(notes = "Simulate robot and get position report. Command should be like this \n PLACE X,Y,F MOVE LEFT RIGHT REPORT", value = "getSimulationReport", response = String.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved report")
    }
    )
    @RequestMapping(value = "/getSimulationReport", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSimulationReport(@RequestParam("command") String robotCommand) {
        String outputVal = null;
        List<ReportModel> reportModelList = null;
        try {
            ToyCommandModel commandModel = new ToyCommandModel(robotCommand);
            reportModelList = runSimulator(commandModel);
        } catch (ToyRobotException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(reportModelList, HttpStatus.OK);
    }

    @ApiOperation(value = "postSimulationCommand", notes = "Give command and table dimension to toy robot simulation \n Ex. PLACE X,Y,F MOVE LEFT RIGHT REPORT")
    @RequestMapping(value = "/postSimulationCommand", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postSimulationCommand(@RequestBody ToyCommandModel robotCommand, UriComponentsBuilder ucBuilder) {
        String outputVal = null;
        List<ReportModel> reportModelList = null;
        try {
            reportModelList = runSimulator(robotCommand);
        } catch (ToyRobotException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(reportModelList, HttpStatus.OK);
    }

    @ApiOperation(value = "fileSimulationCommand", notes = "This method receives file in the form of request and reads content of file and produce report. Command should be in the form of \n Ex. PLACE X,Y,F MOVE LEFT RIGHT REPORT")
    @ApiImplicitParams(value = {
        @ApiImplicitParam(dataType = "file", name = "file", required = true, paramType = "form")})
    @RequestMapping(value = "/fileSimulationCommand", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fileSimulationCommand(@ApiParam(required = true) @RequestParam("file") MultipartFile file) {
        String completeData = null;
        String outputVal = null;
        List<ReportModel> reportModelList = null;
        try {
            if (!file.isEmpty()) {
                byte[] bytes = file.getBytes();
                completeData = new String(bytes);
                ToyCommandModel commandModel = new ToyCommandModel(completeData);
                reportModelList = runSimulator(commandModel);
                logger.info("File content  " + completeData);
            } else {
                throw new ToyRobotException("File should not be empty");
            }
        } catch (ToyRobotException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        } catch (IOException ex) {
            return new ResponseEntity<>("File not uploaded try once again", HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(reportModelList, HttpStatus.OK);
    }

    private List<ReportModel> runSimulator(ToyCommandModel robotCommand) throws ToyRobotException {
        List<ReportModel> reportModelList = null;
        String command = null;
        logger.info("Toy Command ", robotCommand.getToyCommand());

        if (robotCommand.getToyCommand().contains("\r") || robotCommand.getToyCommand().contains("\n")) {
            command = robotCommand.getToyCommand().replaceAll("\r", "");
            command = robotCommand.getToyCommand().replaceAll("\n", " ");
        } else {
            command = robotCommand.getToyCommand();
        }

        String outputVal = null;
        if (robotCommand.getMatrixRow() == 0) {
            robotCommand.setMatrixRow(DefaultValues.DEFAUTL_RAW);
        }
        if (robotCommand.getMatrixColumn() == 0) {
            robotCommand.setMatrixColumn(DefaultValues.DEFAUTL_COLUMN);
        }

        if (command == null || command.isEmpty()) {
            logger.error("Please provide command");
            throw new ToyRobotException("Invalid Command");
        }
        if (!command.contains("PLACE") || command.split(" ").length < 2) {
            throw new ToyRobotException("Invalid Command");
        }

        toyRobotServie.prepareTable(robotCommand.getMatrixRow(), robotCommand.getMatrixColumn());
        toyRobotServie.stringCommandsToList(command);
        reportModelList = toyRobotServie.simulateToyRobot();
        logger.debug("ROBOT SIMULATION REPORT: " + outputVal);

        return reportModelList;
    }

}
