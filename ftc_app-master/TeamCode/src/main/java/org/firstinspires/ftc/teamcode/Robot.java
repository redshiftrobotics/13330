/* Copyright (c) 2017 FIRST. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification,
* are permitted (subject to the limitations in the disclaimer below) provided that
* the following conditions are met:
*
* Redistributions of source code must retain the above copyright notice, this list
* of conditions and the following disclaimer.
*
* Redistributions in binary form must reproduce the above copyright notice, this
* list of conditions and the following disclaimer in the documentation and/or
* other materials provided with the distribution.
*
* Neither the name of FIRST nor the names of its contributors may be used to endorse or
* promote products derived from this software without specific prior written permission.
*
* NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
* LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
* "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
* ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
* FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
* DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
* CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
* OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
* OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


public class Robot { //parent class

    private Hardware hardware;
    private LinearOpMode context;
    private Console console;


    //constructor that allows the Robot class to use opModes and hardware
    public Robot(Hardware hardware, LinearOpMode context) {
        this.hardware = hardware;
        this.context = context;
        this.console = new Console(hardware, this, context);
    }

    public boolean speedToggle = false;
    public double[] speed = {0.33, 1};


//DRIVE FUNCTIONS

    public void setPowerLeft(double power){
        hardware.back_left_motor.setPower(-power);
        hardware.front_left_motor.setPower(-power);
    }

    public void setPowerRight(double power){
        hardware.back_right_motor.setPower(-power);
        hardware.front_right_motor.setPower(-power);
    }

    public void setZeroPowerBehavior() {
        hardware.front_left_motor.setZeroPowerBehavior(hardware.zeroPowerBehavior);
        hardware.back_left_motor.setZeroPowerBehavior(hardware.zeroPowerBehavior);

        hardware.front_right_motor.setZeroPowerBehavior(hardware.zeroPowerBehavior);
        hardware.back_right_motor.setZeroPowerBehavior(hardware.zeroPowerBehavior);
    }

    //drives forward with time
    public void drive(double power, long time) {
        hardware.back_right_motor.setPower(-power + hardware.correction);
        hardware.front_right_motor.setPower(-power + hardware.correction);
        hardware.back_left_motor.setPower(-power);
        hardware.front_left_motor.setPower(-power);
        context.sleep(time);
        hardware.back_right_motor.setPower(0);
        hardware.front_right_motor.setPower(0);
        hardware.back_left_motor.setPower(0);
        hardware.front_left_motor.setPower(0);
    }

    //method that moves forward for the specified time and detects the gold block.
    public void senseColor(double power) {
        int numGold = 0;


        while (context.opModeIsActive()) {
            //go forward

            if (hardware.color_sensor_1.red() + hardware.color_sensor_1.green() + hardware.color_sensor_1.blue()/3 > 50){
                context.telemetry.addData("Mineral is Silver.", "");
                numGold = 0;

            } else if(hardware.color_sensor_1.red() + hardware.color_sensor_1.green() + hardware.color_sensor_1.blue()/3 < 50 && hardware.color_sensor_1.red() + hardware.color_sensor_1.green() + hardware.color_sensor_1.blue()/3 > 20) {
                context.telemetry.addData("Mineral is Gold.", "");
                numGold ++;

            } else {
                context.telemetry.addData("No Mineral seen.","");
                numGold = 0;
            }

            if(numGold == 3)
                break;


            context.telemetry.addData("Color", hardware.color_sensor_1.red() + hardware.color_sensor_1.green() + hardware.color_sensor_1.blue() / 3);
            context.telemetry.addData("NumGold", numGold);
            context.telemetry.update();
        }

        //hardware.mineralKicker.setPosition(90);

        context.telemetry.addData("Stopped", "");
        context.telemetry.update();

    }

//ENCODERS

    //resets encoders
    public void resetEncoders() {
        hardware.back_left_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.front_left_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        hardware.back_right_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.front_right_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        hardware.lowerArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.upperArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    //sets the target position of each motor
    public void setTargetPosition(int position) {
        hardware.back_left_motor.setTargetPosition(position);
        hardware.front_left_motor.setTargetPosition(position);

        hardware.back_right_motor.setTargetPosition(position);
        hardware.front_right_motor.setTargetPosition(position);
    }

    //sets a main runmode for each motor
    public void setRunMode(DcMotor.RunMode runMode) {
        hardware.back_left_motor.setMode(runMode);
        hardware.front_left_motor.setMode(runMode);

        hardware.back_right_motor.setMode(runMode);
        hardware.front_right_motor.setMode(runMode);
    }

    //averages the distance traveled to feet
    public double getDistanceTraveled() {
        int average = hardware.back_left_motor.getCurrentPosition() +
                hardware.front_left_motor.getCurrentPosition() +
                hardware.back_right_motor.getCurrentPosition() +
                hardware.front_right_motor.getCurrentPosition();

        average /= 4;

        return (average / hardware.GEAR_RATIO) / (360 / hardware.CIRCUMFERENCE);
    }

    public void setArmPos(int lowerPos, int upperPos, double power){
        hardware.lowerArm.setTargetPosition(lowerPos);
        hardware.upperArm.setTargetPosition(upperPos);

        hardware.lowerArm.setPower(power);
        hardware.upperArm.setPower(power);
    }

//SUPER FUNCTIONS

    //sets the angles to zero
    public void resetAngle() {
        hardware.oldAngle = hardware.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        hardware.globalAngle = 0;
    }

    //gets the angle
    public double getAngle() {

        //we determined that imu angles works in euler angles so
        hardware.angles = hardware.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double differenceAngle = hardware.angles.firstAngle - hardware.oldAngle.firstAngle;

        if (differenceAngle < -180)
            differenceAngle += 360;
        else if (differenceAngle > 180)
            differenceAngle -= 360;

        hardware.globalAngle += differenceAngle;

        hardware.oldAngle = hardware.angles;

        return hardware.globalAngle;
    }

    //returns a correction value to move in a straight line.
    public double checkDirection() {
        double correction, angle, gain = .10;

        angle = getAngle();

        if (angle == 0)
            correction = 0;             //no adjustment.
        else
            correction = -angle;        //reverse sign of angle for correction.

        correction = correction * gain;

        return correction;
    }

    //rotates the robot x degrees
    public void rotatePID(int degrees, double power, double stopThreashold) {
        double turnPower = 1;

        //makes the degrees between -359 and 359, zero is 360
        degrees = degrees % 360;

        //finds most efficient way to turn
        if (degrees < -180)
            degrees += 360;
        else if (degrees > 180)
            degrees -= 360;

        //restart imu movement tracking
        resetAngle();

        //turn right
        while (context.opModeIsActive() && Math.abs(turnPower) >= stopThreashold) {

            turnPower = PIDSeek(degrees, getAngle(), 0.00002, 0.00000001, 0.000009);

            setPowerLeft(turnPower * 100 * power);
            setPowerRight(-turnPower * 100 * power);
        }
        setPowerLeft(0);
        setPowerRight(0);

        //reset angle
        resetAngle();
    }

    public void rotate(int degrees, double power, double stopThreashold) {
        double turnPower = 1;
        double turnPercentage = 0;

        //makes the degrees between -359 and 359, zero is 360
        degrees = degrees % 360;

        //finds most efficient way to turn
        if (degrees < -180)
            degrees += 360;
        else if (degrees > 180)
            degrees -= 360;

        //restart imu movement tracking
        resetAngle();

        //turn right
        while (context.opModeIsActive() && turnPercentage > stopThreashold) {

            if(degrees > 0) {
                setPowerLeft(turnPower);
                setPowerRight(-turnPower);
            } else {
                setPowerLeft(-turnPower);
                setPowerRight(turnPower);
            }

            turnPercentage = getAngle()/degrees;
        }
        setPowerLeft(0);
        setPowerRight(0);

        //reset angle
        resetAngle();
    }

    //update config
    public void updateConfig(LinearOpMode context) {

        if (context.gamepad1.right_bumper)
            hardware.topSpeed = !hardware.topSpeed;
        if (hardware.topSpeed == false)
            hardware.speed = hardware.minSpeed;
        else
            hardware.speed = hardware.maxSpeed;

        console.Log("TopSpeed", hardware.topSpeed);


        if(context.gamepad2.dpad_left) {
            hardware.currentLowerArmValue = hardware.lowerArmValues[0];
            hardware.currentUpperArmValue = hardware.upperArmValues[0];
        }
        else if(context.gamepad2.dpad_up) {
            hardware.currentLowerArmValue = hardware.lowerArmValues[1];
            hardware.currentUpperArmValue = hardware.upperArmValues[1];
        }
        else if(context.gamepad2.dpad_right) {
            hardware.currentLowerArmValue = hardware.lowerArmValues[2];
            hardware.currentUpperArmValue = hardware.upperArmValues[2];
        }

        if(context.gamepad1.left_stick_y < 0.1 && context.gamepad1.left_stick_y > -0.1)
            context.gamepad1.left_stick_y = 0;

        if(context.gamepad2.left_stick_y < 0.1 && context.gamepad2.left_stick_y > -0.1)
            context.gamepad2.left_stick_y = 0;
    }

    //clamps a value
    public static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

//PID

    double integral;
    double lastProportional = 0;

    //given a current value and a seek value it can return incremental values in between given a p, i, d coefficient
    public double PIDSeek(double seekValue, double currentValue, double pCoeff, double iCoeff, double dCoeff) {

        double proportional = seekValue - currentValue;

        double derivative = (proportional - lastProportional);
        integral += proportional;
        lastProportional = proportional;

        //This is the actual PID formula. This gives us the value that is returned
        double value = pCoeff * proportional + iCoeff * integral + dCoeff * derivative;

        context.telemetry.addData("PID Value", value);
        context.telemetry.addData("porportional", proportional);
        context.telemetry.addData("intergral", integral);
        context.telemetry.addData("Angle", getAngle());
        context.telemetry.addData("Left Power", hardware.back_left_motor.getPower());
        context.telemetry.addData("Right Power", hardware.back_right_motor.getPower());
        context.telemetry.update();

        return value;

    }

//ASAM

    public long startTime;
    public long totalTime;
    public long elapsedTime;

    //test drive with ASAM implemented.
    public void asamDrive(long time, long accelTime) {
        startTime = System.currentTimeMillis();
        totalTime = time;
        elapsedTime = 0;
        while (elapsedTime < totalTime) {
            elapsedTime = System.currentTimeMillis() - startTime;

            setPowerLeft(-computeMotorPower(totalTime, (long) elapsedTime, 0, 1, accelTime));
            setPowerRight(-computeMotorPower(totalTime, (long) elapsedTime, 0, 1, accelTime));
        }
        setPowerRight(0);
        setPowerLeft(0);
    }

    /**
     * Compute the motor power for a given timestamp along an ASAM curve
     *
     * @param totalRunTime total time allocated to the movement
     * @param elapsedTime  time elapsed since the movement began
     * @param startSpeed   starting speed of the movement
     * @param endSpeed     ending speed of the movement
     * @param accelTime    amount of time to accelerate/decelerate
     * @return
     */
    public double computeMotorPower(long totalRunTime, long elapsedTime, float startSpeed, float endSpeed, long accelTime) {
        if (elapsedTime <= accelTime) {
            return computeAcceleration(totalRunTime, elapsedTime, startSpeed, endSpeed, accelTime);
        } else if (elapsedTime < (totalRunTime - accelTime)) {
            return endSpeed;
        } else {
            return computeDeceleration(totalRunTime, elapsedTime, startSpeed, endSpeed, accelTime);
        }
    }

    /**
     * Compute the acceleration section of the curve
     *
     * @param totalRunTime total move time
     * @param elapsedTime  total elapsed time
     * @param startSpeed   starting speed
     * @param endSpeed     ending speed
     * @param accelTime    amount of time to accelerate
     * @return current motor power
     */
    private double computeAcceleration(long totalRunTime, long elapsedTime, float startSpeed, float endSpeed, long accelTime) {

        return (
                ((startSpeed - endSpeed) / 2)
                        * Math.cos((elapsedTime * Math.PI) / accelTime)
                        + ((startSpeed + endSpeed) / 2)
        );
    }

    /**
     * Compute the deceleration section of the curve
     *
     * @param totalRunTime total move time
     * @param elapsedTime  total elapsed time
     * @param startSpeed   starting speed
     * @param endSpeed     ending speed
     * @param accelTime    amount of time to deceleration
     * @return current motor power
     */
    private double computeDeceleration(long totalRunTime, long elapsedTime, float startSpeed, float endSpeed, long accelTime) {

        return (
                ((endSpeed - startSpeed) / 2)
                        * Math.cos(
                        (Math.PI * (totalRunTime - accelTime - elapsedTime)) / accelTime
                )
                        + ((startSpeed + endSpeed) / 2)
        );
    }

}

