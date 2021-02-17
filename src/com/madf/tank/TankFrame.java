package com.madf.tank;

import com.madf.abstractfactory.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankFrame extends Frame {

    Tank myTank = new Tank(200, 400, Dir.DOWN, Group.GOOD, this);

//    List<Tank> enemyTanks = new ArrayList<>();
//    List<Explode> explodes = new ArrayList<>();
//    List<Bullet> bullets = new ArrayList<>();
    //改为使用抽象工厂
    public List<Tank> enemyTanks = new ArrayList<>();
    public List<BaseExplode> explodes = new ArrayList<>();
    public List<BaseBullet> bullets = new ArrayList<>();

    GameFactory gameFactory = new DefaultFactory();

    public static final int GAME_WIDTH = 1080;
    public static final int GAME_HEIGHT = 960;

    public TankFrame() {
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setTitle("tank war");
        setVisible(true);

        //关闭窗口
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        addKeyListener(new MyKeyListener());
    }

    /**
     * 键盘监听器
     */
    class MyKeyListener extends KeyAdapter {

        //按键的状态
        boolean bL = false;
        boolean bR = false;
        boolean bU = false;
        boolean bD = false;

        //按键按下时调用
        @Override
        public void keyPressed(KeyEvent keyEvent) {
            int key = keyEvent.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    bL = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = true;
                    break;
                case KeyEvent.VK_UP:
                    bU = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = true;
                    break;
                default:
                    break;
            }

            setMainTankDir();
        }

        //按键抬起时调用
        @Override
        public void keyReleased(KeyEvent keyEvent) {
            int key = keyEvent.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    bL = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = false;
                    break;
                case KeyEvent.VK_UP:
                    bU = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = false;
                    break;
                case KeyEvent.VK_CONTROL:
                    myTank.fire();
                    break;
                default:
                    break;
            }

            setMainTankDir();
        }

        private void setMainTankDir() {
            if (!bL && !bU && !bR && !bD) {
                myTank.setMoving(false);
            } else {
                myTank.setMoving(true);
                if (bL) myTank.setDir(Dir.LEFT);
                if (bR) myTank.setDir(Dir.RIGHT);
                if (bU) myTank.setDir(Dir.UP);
                if (bD) myTank.setDir(Dir.DOWN);
            }
        }
    }

    //用双缓冲解决闪烁问题
    Image offScreenImage = null;
    @Override
    public void update(Graphics graphics) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color color = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(color);
        paint(gOffScreen);
        graphics.drawImage(offScreenImage, 0, 0, null);
    }

    @Override
    public void paint(Graphics graphics) {
        Color color = graphics.getColor();
        graphics.setColor(Color.WHITE);
        graphics.drawString("子弹的数量：" + bullets.size(), 10, 60);
        graphics.drawString("敌人的数量：" + enemyTanks.size(), 10, 80);
        graphics.drawString("爆炸的数量：" + explodes.size(), 10, 100);
        graphics.setColor(color);
        myTank.paint(graphics);
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(graphics);
        }
        for (int i = 0; i < enemyTanks.size(); i++) {
            enemyTanks.get(i).paint(graphics);
        }

        for (int i = 0; i < explodes.size(); i++) {
            explodes.get(i).paint(graphics);
        }

        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < enemyTanks.size(); j++) {
                bullets.get(i).collideWith(enemyTanks.get(j));
            }
        }
    }

}
