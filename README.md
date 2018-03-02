# ToyRobotSimulation
Based on given requirement this document describes the tool requirement to run code, discussion on kind of restful services and manual & unit testing procedure

##Requirement
1.	IDE : Netbeans or eclipse
1.	JDK 1.8
1.	Apache Maven 3
1.	Important : PORT : 8080 

##Restful Services	
1.	http://localhost:8080/toyRobot/getSimulationReport?command= PLACE X,Y,F MOVE LEFT

###getSimulationReport

Implementation Notes
Simulate robot and get position report. Command should be like this PLACE X,Y,F MOVE LEFT RIGHT REPORT

2.	http://localhost:8080/toyRobot/postSimulationCommand

###postSimulationCommand

Implementation Notes
Give command and table dimension to toy robot simulation Ex. PLACE X,Y,F MOVE LEFT RIGHT REPORT
Request Format
{
  "matrixColumn": 0,
  "matrixRow": 0,
  "toyCommand": "PLACE X,Y,F MOVE LEFT RIGHT "
}

Note: matrixColumn and matrixRow by default set to 5


3.	http://localhost:8080/toyRobot/fileSimulationCommand

###fileSimulationCommand

Implementation Notes
This method receives file in the form of request and reads content of file and produce report. Command should be in the form of Ex. PLACE X,Y,F MOVE LEFT RIGHT REPORT

Request Format

Test.txt


PLACE X,Y,F MOVE LEFT RIGHT

