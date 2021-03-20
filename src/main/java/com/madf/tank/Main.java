package com.madf.tank;

import com.madf.net.Client;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        TankFrame tf = TankFrame.INSTANCE;

        /*int initTankCount = Integer.parseInt((String)PropertyMgr.get("initTankCount"));

        //初始化敌方坦克
        for (int i = 0; i < initTankCount; i++) {
            tf.enemyTanks.add(new Tank(50 + i*80, 200, Dir.DOWN, Group.BAD, tf));
        }*/

//        new Thread(() -> new Audio("audio/explode.wav").loop()).start();

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tf.repaint();
            }
        }).start();

        tf.setVisible(true);
        Client.INSTANCE.connect();
    }

}
