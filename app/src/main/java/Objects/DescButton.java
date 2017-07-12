package Objects;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Ofek on 07-Jul-17.
 * a button with extra attributes that represent the button text in the necessary types
 */

public class DescButton extends android.support.v7.widget.AppCompatButton{
    private int asInt;
    private String asString;
    public DescButton(Context context) {
        super(context);
    }

    public DescButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DescButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setDescription(String asString,int asInt) {
        this.asString=asString;
        this.asInt=asInt;
    }
    public void setDescription(String asString) {
        this.asString=asString;
        this.asInt=-1;
    }

    public int getAsInt() {
        return asInt;
    }

    public String getAsString() {
        return asString;
    }
}
