/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.idealo.toyrobot.service;

import de.idealo.toyrobot.simulator.ToyCoordinatePosition;
import org.springframework.stereotype.Service;

/**
 *
 * @author uysharma
 */
@Service
public class SquareBoardImpl implements SquareBoard{
    
    private int columns;
     private int rows;

    public SquareBoardImpl() {
    }

    public SquareBoardImpl(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
    }
    

    @Override
    public boolean isValidCoordinatePosition(ToyCoordinatePosition toyCoordinatePosition) {
        return !(
                toyCoordinatePosition.getCoordX() > this.columns || toyCoordinatePosition.getCoordX() < 0 ||
                        toyCoordinatePosition.getCoordY() > this.rows || toyCoordinatePosition.getCoordY() < 0
        );
    }

    /**
     * @return the columns
     */
    public int getColumns() {
        return columns;
    }

    /**
     * @param columns the columns to set
     */
    public void setColumns(int columns) {
        this.columns = columns;
    }

    /**
     * @return the rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(int rows) {
        this.rows = rows;
    }
    
}
