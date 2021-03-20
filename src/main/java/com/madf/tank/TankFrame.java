package com.madf.tank;

import com.madf.net.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

public class TankFrame extends Frame {
    //设置TankFrame为单例
    public static final TankFrame INSTANCE = new TankFrame();

    static final int GAME_WIDTH = 800;
    static final int GAME_HEIGHT = 600;

    Random random = new Random();

    Tank myTank = new Tank(random.nextInt(GAME_WIDTH), random.nextInt(GAME_HEIGHT), Dir.DOWN, Group.GOOD, this);
    List<Bullet> bullets = new ArrayList<>();
    Map<UUID, Tank> tanks = new HashMap<>();
    List<Explode> explodes = new ArrayList<>();

    private TankFrame() {
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setTitle("tank war");
//        setVisible(true);

        //关闭窗口
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                Client.INSTANCE.send(new ClientCloseMsg(TankFrame.INSTANCE.getMainTank().getId()));
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
                    setMainTankDir();
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = true;
                    setMainTankDir();
                    break;
                case KeyEvent.VK_UP:
                    bU = true;
                    setMainTankDir();
                    break;
                case KeyEvent.VK_DOWN:
                    bD = true;
                    setMainTankDir();
                    break;
                default:
                    break;
            }

            new Thread(() -> new Audio("audio/tank_move.wav").play()).start();
        }

        //按键抬起时调用
        @Override
        public void keyReleased(KeyEvent keyEvent) {
            int key = keyEvent.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    bL = false;
                    setMainTankDir();
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = false;
                    setMainTankDir();
                    break;
                case KeyEvent.VK_UP:
                    bU = false;
                    setMainTankDir();
                    break;
                case KeyEvent.VK_DOWN:
                    bD = false;
                    setMainTankDir();
                    break;
                case KeyEvent.VK_CONTROL:
                    myTank.fire();
                    break;
                default:
                    break;
            }
        }

        private void setMainTankDir() {
            //记录之前的方向
            Dir dir = myTank.getDir();

            if (!bL && !bU && !bR && !bD) {
                myTank.setMoving(false);
                Client.INSTANCE.send(new TankStopMsg(getMainTank()));
            } else {
                if (bL) myTank.setDir(Dir.LEFT);
                if (bR) myTank.setDir(Dir.RIGHT);
                if (bU) myTank.setDir(Dir.UP);
                if (bD) myTank.setDir(Dir.DOWN);
                if (!myTank.isMoving()) Client.INSTANCE.send(new TankStartMovingMsg(getMainTank()));
                myTank.setMoving(true);
                if (dir != myTank.getDir()) {
                    Client.INSTANCE.send(new TankDirChangedMsg(myTank));
                }
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
        graphics.drawString("bullets：" + bullets.size(), 10, 60);
        graphics.drawString("tanks：" + tanks.size(), 10, 80);
        graphics.drawString("explodes：" + explodes.size(), 10, 100);
        graphics.setColor(color);
        myTank.paint(graphics);
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(graphics);
        }

        //java8 stream api
        tanks.values().forEach(c -> c.paint(graphics));

        for (int i = 0; i < explodes.size(); i++) {
            explodes.get(i).paint(graphics);
        }

        Collection<Tank> values = tanks.values();
        for (int i = 0; i < bullets.size(); i++) {
            for (Tank t : values) {
                bullets.get(i).collideWith(t);
            }
        }
    }

    public void addBullet(Bullet b) {
        bullets.add(b);
    }

    public void addTank(Tank t) {
        tanks.put(t.getId(), t);
    }

    public Tank findTankByUUID(UUID id) {
        return tanks.get(id);
    }

    public Bullet findBulletByUUID(UUID id) {
        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).getId().equals(id)) {
                return bullets.get(i);
            }
        }
        return null;
    }

    public Tank getMainTank() {
        return this.myTank;
    }

}
