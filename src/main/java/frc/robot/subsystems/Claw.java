package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import frc.robot.constants.ClawConstants;

public class Claw extends SubsystemBase {
    private final WPI_VictorSPX clawMotor;
    private final DigitalInput limitSwitchOpen = new DigitalInput(ClawConstants.LIMIT_SWITCH_OPEN);
    private final DigitalInput limitSwitchClose = new DigitalInput(ClawConstants.LIMIT_SWITCH_CLOSE);
    private boolean isOpen;

    public Claw(int deviceNumber) {
        // need motor id (and name of motor would be helpful)
        this.clawMotor = new WPI_VictorSPX(deviceNumber);
        this.clawMotor.setNeutralMode(NeutralMode.Brake);
        isOpen = true; // Claw has to start open, need to run code to close motor when initialized.
    }

    public boolean getIsOpen() {
        return isOpen;
    }

    public void startClosing() {
        if (isOpen && !limitSwitchClose.get()) {
            clawMotor.set(ControlMode.PercentOutput, ClawConstants.CLOSE_PERCENT_OUTPUT);
            isOpen = false;
        }
    }

    public void stopClosing() {
        if (limitSwitchClose.get()) {
            clawMotor.stopMotor();
        }
    }

    public void startOpening() {
        // Limit switch protected
        if (!isOpen && !limitSwitchOpen.get()) {
            clawMotor.set(ControlMode.PercentOutput, ClawConstants.OPEN_PERCENT_OUTPUT);
            isOpen = true;
        }
    }

    public void stopOpening() {
        // Needs more code once able. Will interact with motor limiter part.(unfinished)
        clawMotor.stopMotor(); // Not sure what this does but it probably cuts voltage to motor.
        // Other options are clawMotor.set(0.0) or clawMotor.set(ControlMode.PercentOutput, 0.0)
        // The documentation is pretty unclear as to what each of these do, so testing should be done.
    }
}