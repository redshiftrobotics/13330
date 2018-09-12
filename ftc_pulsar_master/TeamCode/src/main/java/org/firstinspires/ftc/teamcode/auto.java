package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Autonomous(name = "Auto")
public class auto extends LinearOpMode{ // comments written by Mr. Luca Sandoval

    // Don't even need to reference the motors lol.


    @Override
    public void runOpMode(){

        // We don't need anything here.

        waitForStart();
        while (opModeIsActive()){ // the auto instructions list from Robot.java

            Robot.Drive.drive(1, 1, 1000); // forward for 1 second.

            Robot.Drive.drive(1, 0.5, 1000); // right for 1 second.

        }

    }

}