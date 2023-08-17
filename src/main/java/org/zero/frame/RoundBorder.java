package org.zero.frame;

import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

//버튼 테두리 둥글게 만드는 클래스
class RoundBorder extends AbstractBorder {
    int radius;
    Color borderColor;
    float borderWidth;

    public RoundBorder(int radius, Color borderColor, float borderWidth) {
        this.radius = radius;
        this.borderColor = borderColor; // 테두리 색상 지정
        this.borderWidth = borderWidth;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(borderColor); // 테두리 색상 설정
        g2.setStroke(new BasicStroke(borderWidth));
        g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }

    public Shape getBorderShape(int x, int y, int width, int height) {
        return new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius);
    }
}
