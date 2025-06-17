/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.main;

import java.awt.*;
import javax.swing.JPanel;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;

/**
 *
 * @author Rohim
 */
public class GradientPanel extends JPanel {

    public GradientPanel() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        int w = getWidth();
        int h = getHeight();

        float[] dist = {0.0f, 0.3f, 0.6f, 1.0f};
        Color[] colors = {
            new Color(255, 255, 255), // #FFFFFF
            new Color(255, 205, 210), // #FFCDD2
            new Color(239, 154, 154), // #EF9A9A
            new Color(144, 202, 249) // #90CAF9
        };

        LinearGradientPaint p = new LinearGradientPaint(
                0, 0, w, h, dist, colors, MultipleGradientPaint.CycleMethod.NO_CYCLE
        );

        g2d.setPaint(p);
        g2d.fillRect(0, 0, w, h);
        g2d.dispose();

        super.paintComponent(g);
    }
}
