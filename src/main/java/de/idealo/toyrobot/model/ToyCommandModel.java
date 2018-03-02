/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.idealo.toyrobot.model;

import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author uysharma
 */
public class ToyCommandModel {
    
    @NotNull
    @NotEmpty
    @ApiModelProperty(value = "PLACE X,Y,F MOVE LEFT RIGHT REPORT",notes = "Toy Command should be like this PLACE X,Y,F MOVE LEFT RIGHT REPORT")
    private String toyCommand;
    private int matrixRow;
    private int matrixColumn;

    public ToyCommandModel() {
    }

    
    public ToyCommandModel(String toyCommand) {
        this.toyCommand = toyCommand;
    }
    
    /**
     * @return the toyCommand
     */
    public String getToyCommand() {
        return toyCommand;
    }

    /**
     * @param toyCommand the toyCommand to set
     */
    public void setToyCommand(String toyCommand) {
        this.toyCommand = toyCommand;
    }

    /**
     * @return the matrixRow
     */
    public int getMatrixRow() {
        return matrixRow;
    }

    /**
     * @param matrixRow the matrixRow to set
     */
    public void setMatrixRow(int matrixRow) {
        this.matrixRow = matrixRow;
    }

    /**
     * @return the matrixColumn
     */
    public int getMatrixColumn() {
        return matrixColumn;
    }

    /**
     * @param matrixColumn the matrixColumn to set
     */
    public void setMatrixColumn(int matrixColumn) {
        this.matrixColumn = matrixColumn;
    }

    @Override
    public String toString() {
        return "ToyCommandModel{" + "toyCommand=" + toyCommand + ", matrixRow=" + matrixRow + ", matrixColumn=" + matrixColumn + '}';
    }

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.toyCommand);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ToyCommandModel other = (ToyCommandModel) obj;
        return Objects.equals(this.toyCommand, other.toyCommand);
    }
    
    
}
