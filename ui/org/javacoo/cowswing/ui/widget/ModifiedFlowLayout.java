/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.ui.widget;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;

/**
 * 自动换行FlowLayout
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-5-1 下午10:42:26
 * @version 1.0
 */
public class ModifiedFlowLayout extends FlowLayout{
	private static final long serialVersionUID = 1L;

	public ModifiedFlowLayout() {  
        super();  
    }  
  
    public ModifiedFlowLayout(int align) {  
        super(align);  
    }  
  
    public ModifiedFlowLayout(int align, int hgap, int vgap) {  
        super(align, hgap, vgap);  
    }  
  
    public Dimension minimumLayoutSize(Container target) {  
        return computeSize(target, false);  
    }  
  
    public Dimension preferredLayoutSize(Container target) {  
        return computeSize(target, true);  
    }  
  
    private Dimension computeSize(Container target, boolean minimum) {  
        synchronized (target.getTreeLock()) {  
            int hgap = getHgap();  
            int vgap = getVgap();  
            int w = target.getWidth();  
  
            if (w == 0) {  
                w = Integer.MAX_VALUE;  
            }  
  
            Insets insets = target.getInsets();  
            if (insets == null) {  
                insets = new Insets(0, 0, 0, 0);  
            }  
            int reqdWidth = 0;  
  
            int maxwidth = w - (insets.left + insets.right + hgap * 2);  
            int n = target.getComponentCount();  
            int x = 0;  
            int y = insets.top;  
            int rowHeight = 0;  
  
            for (int i = 0; i < n; i++) {  
                Component c = target.getComponent(i);  
                if (c.isVisible()) {  
                    Dimension d =  
                            minimum ? c.getMinimumSize() : c.getPreferredSize();  
                    if ((x == 0) || ((x + d.width) <= maxwidth)) {  
                        if (x > 0) {  
                            x += hgap;  
                        }  
                        x += d.width;  
                        rowHeight = Math.max(rowHeight, d.height);  
                    } else {  
                        x = d.width;  
                        y += vgap + rowHeight;  
                        rowHeight = d.height;  
                    }  
                    reqdWidth = Math.max(reqdWidth, x);  
                }  
            }  
            y += rowHeight;  
            return new Dimension(reqdWidth + insets.left + insets.right, y);  
        }  
    }  
}
