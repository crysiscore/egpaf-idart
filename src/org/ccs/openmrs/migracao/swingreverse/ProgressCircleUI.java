/*
 * Decompiled with CFR 0_114.
 */
package org.ccs.openmrs.migracao.swingreverse;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import javax.swing.JComponent;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;

class ProgressCircleUI
extends BasicProgressBarUI {
    ProgressCircleUI() {
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        Dimension d = super.getPreferredSize(c);
        int v = Math.max(d.width, d.height);
        d.setSize(v, v);
        return d;
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Insets b = this.progressBar.getInsets();
        int barRectWidth = this.progressBar.getWidth() - b.right - b.left;
        int barRectHeight = this.progressBar.getHeight() - b.top - b.bottom;
        if (barRectWidth <= 0 || barRectHeight <= 0) {
            return;
        }
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        double degree = 360.0 * this.progressBar.getPercentComplete();
        double sz = Math.min(barRectWidth, barRectHeight);
        double cx = (double)b.left + (double)barRectWidth * 0.5;
        double cy = (double)b.top + (double)barRectHeight * 0.5;
        double or = sz * 0.5;
        double ir = or * 0.5;
        Ellipse2D.Double inner = new Ellipse2D.Double(cx - ir, cy - ir, ir * 2.0, ir * 2.0);
        Ellipse2D.Double outer = new Ellipse2D.Double(cx - or, cy - or, sz, sz);
        Arc2D.Double sector = new Arc2D.Double(cx - or, cy - or, sz, sz, 90.0 - degree, degree, 2);
        Area foreground = new Area(sector);
        Area background = new Area(outer);
        Area hole = new Area(inner);
        foreground.subtract(hole);
        background.subtract(hole);
        g2.setPaint(new Color(14540253));
        g2.fill(background);
        g2.setPaint(this.progressBar.getForeground());
        g2.fill(foreground);
        g2.dispose();
        if (this.progressBar.isStringPainted()) {
            this.paintString(g, b.left, b.top, barRectWidth, barRectHeight, 0, b);
        }
    }
}

