/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.idealo.toyrobot.model;

import java.util.Objects;

/**
 *
 * @author uysharma
 */
public class ReportModel {
    private int coordX;
    private int coordY;
    private String faceDirection;

    public ReportModel() {
    }
    
    

    public ReportModel(int coordX, int coordY, String faceDirection) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.faceDirection = faceDirection;
    }
    
    

    /**
     * @return the coordX
     */
    public int getCoordX() {
        return coordX;
    }

    /**
     * @param coordX the coordX to set
     */
    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    /**
     * @return the coordY
     */
    public int getCoordY() {
        return coordY;
    }

    /**
     * @param coordY the coordY to set
     */
    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    /**
     * @return the faceDirection
     */
    public String getFaceDirection() {
        return faceDirection;
    }

    /**
     * @param faceDirection the faceDirection to set
     */
    public void setFaceDirection(String faceDirection) {
        this.faceDirection = faceDirection;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.coordX;
        hash = 67 * hash + this.coordY;
        hash = 67 * hash + Objects.hashCode(this.faceDirection);
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
        final ReportModel other = (ReportModel) obj;
        if (this.coordX != other.coordX) {
            return false;
        }
        if (this.coordY != other.coordY) {
            return false;
        }
        return Objects.equals(this.faceDirection, other.faceDirection);
    }

    @Override
    public String toString() {
        return "{"+coordX+","+coordY+","+faceDirection+"}";
    }

    
    
    
}
