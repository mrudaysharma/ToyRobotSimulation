/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.idealo.toyrobot.service;

import de.idealo.toyrobot.exception.ToyRobotException;
import de.idealo.toyrobot.model.ReportModel;
import java.util.List;

/**
 *
 * @author uysharma
 */

public interface ToyRobotInterface {

    public void prepareTable(int x, int y);
    
    public List<String> stringCommandsToList(String command);
    
    public List<ReportModel> simulateToyRobot() throws ToyRobotException;

    public String performPlaceOperation(String command);

    public String performMoveOperation(String command);

    public String performLeftOperation(String command);

    public String performRightOperation(String command);

    public ReportModel generateReport(String command);

}
