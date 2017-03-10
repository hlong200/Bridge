// 
// Decompiled by Procyon v0.5.30
// 

package derp;

public enum Tool
{
    NONE("NONE", 0, "None"), 
    MEMBER("MEMBER", 1, "Member"), 
    NODE("NODE", 2, "Node"), 
    FIXED_NODE("FIXED_NODE", 3, "Fixed Node"), 
    ROLLING_NODE("ROLLING_NODE", 4, "Horizontal Rolling Node"),
    HIGHLIGHT("HIGHLIGHT", 5, "Highlight");
    
    private String value;
    
    private Tool(final String s, final int n, final String value) {
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }
}
