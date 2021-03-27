package top.mcwebsite.kotlin.demo.base.annotion.jvmoverloads;

public class View {
    public View(Context context) {
        this(context, null, 0);
    }

    public View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public View(Context context, AttributeSet attrs, int defStyle) {

    }
}
