/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 *
 * @author Rohim
 */
public class GradientPanel extends Panel {

    private Color color1;
    private Color color2;

    public GradientPanel(Color color1, Color color2) {
        this.color1 = color1;
        this.color2 = color2;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int radius = getCornerRadius(); // akses dari class induk

        // Gambar gradient background
        GradientPaint gp = new GradientPaint(0, 0, color1, width, height, color2);
        g2.setPaint(gp);
        g2.fillRoundRect(0, 0, width, height, radius, radius);

        // Gambar border
        if (getBorderWidth() > 0) {
            g2.setColor(getBorderColor());
            g2.setStroke(new BasicStroke(getBorderWidth()));
            g2.drawRoundRect(getBorderWidth() / 2, getBorderWidth() / 2,
                    width - getBorderWidth(), height - getBorderWidth(),
                    radius, radius);
        }

        g2.dispose();
        // Jangan panggil super.paintComponent(g) kalau tidak ingin background putih dari Panel
    }
}
