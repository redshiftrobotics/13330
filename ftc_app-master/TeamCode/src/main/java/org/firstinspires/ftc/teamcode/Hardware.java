/*
 * Copyright (c) 2018. RED SHIFT ROBOTICS. All rights reserved.
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

import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;


/**
 * The non-chassis specific hardware initializations.
 */

public class Hardware { // Here we get the DcMotors from the REV hub and assign their names.

    public DcMotor frontRightMotor;
    public DcMotor frontLeftMotor;
    public DcMotor backRightMotor;
    public DcMotor backLeftMotor;
    public DcMotor collector;
    public DcMotor collectorHinge;
    public DcMotor extenderWheel;

    public Servo mineralKicker1;
    public Servo mineralKicker2;
    public Servo flipServo1;
    public Servo flipServo2;
    public Servo sorterServo1;
    public Servo sorterServo2;
    public Servo arm2;
    public Servo depositor;
    public DcMotor lifter;


    public CameraName webcam;

    public TouchSensor limitSwitch;
    public DigitalChannel flipLimit;

    public DcMotor.ZeroPowerBehavior zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT;


    public Hardware(OpMode context) { // this class gets all the motors, sensors, and imu and hooks it up to the hardware map.

        //region servos
        //mineralKicker1 = context.hardwareMap.servo.get("mineralKicker1");
        //mineralKicker2 = context.hardwareMap.servo.get("mineralKicker2");

        //arm2 = context.hardwareMap.servo.get("arm2");
        //depositor = context.hardwareMap.servo.get("depositor");
        //endregion

        //region motors

        //arm1 = context.hardwareMap.dcMotor.get("arm1");
        //arm1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //arm1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        collectorHinge = context.hardwareMap.dcMotor.get("collectorHinge");
        collectorHinge.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        collector = context.hardwareMap.dcMotor.get("collector");
        collector.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lifter = context.hardwareMap.dcMotor.get("lifter");
        lifter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        flipServo1 = context.hardwareMap.servo.get("flipServo1");
        flipServo2 = context.hardwareMap.servo.get("flipServo2");

        extenderWheel = context.hardwareMap.dcMotor.get("extenderWheel");


        //endregion

        //region other
        webcam = context.hardwareMap.get(CameraName.class, "Webcam 1");

        //limitSwitch = context.hardwareMap.get(TouchSensor.class, "limitSwitch");
        //endregion


    }
}