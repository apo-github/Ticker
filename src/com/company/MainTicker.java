package com.company;

import javax.swing.*;
import java.awt.*;

public class MainTicker extends JFrame {

    public static void main(String[] args) {

        MainTicker ticker = new MainTicker();
        Containts containts = new Containts(ticker);
        ticker.setAlwaysOnTop(true);

        ticker.addMouseListener(containts);//フレームを移動できるようにする
        ticker.addMouseMotionListener(containts);//フレームを移動できるようにする
        ticker.add(containts);
        ticker.setDefaultCloseOperation(MainTicker.EXIT_ON_CLOSE);
        ticker.setUndecorated(true);//枠を消す
        ticker.setBackground(new Color(0, 0, 0, 10));//背景を透明にする
        ticker.setLocationRelativeTo(null);
        ticker.pack();
        ticker.setVisible(true);
    }


}
