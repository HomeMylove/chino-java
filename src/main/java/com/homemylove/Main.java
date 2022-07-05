package com.homemylove;

import com.homemylove.core.load.RobotFactory;
import com.homemylove.core.load.RobotInter;

public class Main {
    public static void main(String[] args)  {
        RobotInter robot = RobotFactory.create();
        robot.start();
    }
}
