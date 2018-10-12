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

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;


@Autonomous(name = "Autonomous", group = "13330 Pulsar")
public class auto extends LinearOpMode {

    public double version = 0.05; // version name for organization.



    //this.instances of hardware, robot, and text
    private Hardware hardware;
    private Robot robot;
    private Console console;



    @Override
    public void runOpMode() {

        //makes an instance of hardware with this LinearOpMode as its context
        this.hardware = new Hardware(this);
        //makes in instance or robot with this hardware as context
        this.robot = new Robot(this.hardware, this);
        //makes an instance of Text with Hardware and Robot as its context
        this.console = new Console(this.hardware, this.robot, this);


        telemetry.addData("Action:", "waiting for IMU initialization");
        telemetry.update();


        //wait for the IMU to be initiated
        while (!hardware.imu.isGyroCalibrated()) {
            idle();
        }

        //makes sure the color sensor is enabled

        //prints out various statistics for debugging purposes
        console.displayStatistics(version, "Autonomous");

        //waits until the program is started
        waitForStart();

        robot.rotate(30, 1,0.1);
        sleep(100);
        robot.asamDrive(1000, 1000);

        robot.rotate(-120, 1,0.1);



        telemetry.addData("Auto Completed.", "");
        telemetry.update();

    }
}
