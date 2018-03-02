/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.idealo.toyrobot.inter;

import de.idealo.toyrobot.exception.ToyRobotException;
import de.idealo.toyrobot.model.ReportModel;
import java.util.List;

/**
 *
 * @author uysharma
 */
public interface SimulationInterface {
      public void prepareTable(int x, int y);
    
    public List<String> stringCommandsToList(String command);
    
    public List<ReportModel> simulateToyRobot() throws ToyRobotException; 
}
