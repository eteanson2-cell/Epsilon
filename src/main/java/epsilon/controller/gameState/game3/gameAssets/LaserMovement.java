package epsilon.controller.gameState.game3.gameAssets;

import epsilon.model.entities.figures.Point;

public class LaserMovement{
    LaserBarrier laserBarrier;
    public boolean isVector;
    public Point movementA;
    public Point movementB;
    public Double thetaA;
    public Double thetaB;
    public Point rotationA;
    public Point rotationB;  
    public LaserMovement(Point movementA, Point movementB, boolean isVector, Point rotationA, Point rotationB, Double thetaA, Double thetaB) {
        this.movementA = movementA;
        this.movementB = movementB;
        this.isVector = isVector;
        this.rotationA = rotationA;
        this.rotationB = rotationB;
        this.thetaA = thetaA;
        this.thetaB = thetaB;
    }
    public void movePoints(double x, double y){
        if(isVector == false){
            if(movementA != null){
                movementA.move(x, y);
            }
            if(movementB != null){
                movementB.move(x, y);
            }
        }
        if(rotationA != null){
            rotationA.move(x, y);
        }
        if(rotationB != null){
            rotationB.move(x, y);
        }
    }
    public void fixToBenchmark(double benchMark){
        if(isVector == false){
            if(movementA != null){
                movementA.setY(benchMark-movementA.getY());
            }
            if(movementB != null){
                movementB.setY(benchMark-movementB.getY());
            }
        }
        if(rotationA != null){
            rotationA.setY(benchMark-rotationA.getY());
        }
        if(rotationB != null){
            rotationB.setY(benchMark-rotationB.getY());
        }
    }
    public void executeAll(LaserBarrier laserBarrier){
        if(laserBarrier != null){
            this.laserBarrier = laserBarrier;
            executeMovement();
            executeRotation();
        }
        this.laserBarrier = null;
    }
    private void executeMovement(){
        if(movementA != null){
            if(isVector == true){
                laserBarrier.moveAPoint(movementA.getX(), movementA.getY());
            }
            else{
                laserBarrier.moveAPointTo(movementA.getX(), movementA.getY());
            }
        }
        if(movementB != null){
            if(isVector == true){
                laserBarrier.moveBPoint(movementB.getX(), movementB.getY());
            }
            else{
                laserBarrier.moveBPointTo(movementB.getX(), movementB.getY());
            }
        }
    }
    private void executeRotation(){
        if(thetaA != null){
            if(rotationA != null){
                laserBarrier.rotateAfrom(rotationA.getX(), rotationA.getY(), thetaA);
            }
            else{
                laserBarrier.rotateAfromB(thetaA);
            }
        }
        if(thetaB != null){
            if(rotationB != null){
                laserBarrier.rotateBfrom(rotationB.getX(), rotationB.getY(), thetaB);
            }
            else{
                laserBarrier.rotateBfromA(thetaB);
            }
        }
    }

}