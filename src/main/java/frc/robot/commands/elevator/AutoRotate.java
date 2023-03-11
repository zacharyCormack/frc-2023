package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ArmConstants;
import frc.robot.subsystems.Arm;

public class AutoRotate extends CommandBase {
    private final Arm arm;
    
    private double setpointAngle;

    private boolean setpointReached;

    /**
     * Autonomously rotates the arm to a set position for scoring. 
     * 
     * @param arm
     * @param angle in radians. 
     */
    public AutoRotate(Arm arm, double setpointAngle) {
        this.arm = arm; 
        this.setpointAngle = setpointAngle;
        this.setpointReached = false; 
        
        addRequirements(arm); 
    }

    @Override 
    public void initialize() {
        this.arm.resetEncoder();
    }

    @Override 
    public void execute() {
        if (!setpointReached) {
            this.arm.setMotor(arm.getPIDController().calculate(
                this.arm.getEncoder().getDistance(), setpointAngle
            ));

            if (Math.abs(this.arm.getEncoder().getDistance() - setpointAngle) < ArmConstants.ARM_TOLERANCE) {
                this.setpointReached = true; 
            }
        }

        SmartDashboard.putNumber("Hex Encoder", this.arm.getEncoder().getDistance()); 
    }

    @Override 
    public boolean isFinished() {
        return this.setpointReached;
    }

    @Override
    public void end(boolean interrupted) {
        this.arm.resetEncoder(); 
    }

}
