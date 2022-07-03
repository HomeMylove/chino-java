package com.homemylove;

import com.homemylove.core.load.RobotFactory;
import com.homemylove.core.load.RobotInter;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {

        RobotInter robot = RobotFactory.create();

        robot.start();
    }
}
