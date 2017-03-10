// 
// Decompiled by Procyon v0.5.30
// 

package derp;

import org.newdawn.slick.geom.Rectangle;

public class Button extends Rectangle
{
    private String text;
    
    public Button(final float x, final float y, final float width, final float height, final String text) {
        super(x, y, width, height);
        this.text = text;
    }
    
    public String getText() {
        return this.text;
    }
    
    public void setText(final String str) {
        this.text = str;
    }
}
