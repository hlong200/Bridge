// 
// Decompiled by Procyon v0.5.30
// 

package derp;

import org.newdawn.slick.geom.Line;

public class GridLine extends Line
{
    LineType type;
    
    public GridLine(final float x, final float y, final float xx, final float yy) {
        super(x, y, xx, yy);
    }
    
    public GridLine(final float x, final float y, final float xx, final float yy, final LineType type) {
        super(x, y, xx, yy);
        this.type = type;
    }
}
