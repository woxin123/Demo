package top.mcwebsite.kotlin.demo.base.annotion.jvmoverloads

class Context

class AttributeSet

class ScrollerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
): View(context, attrs, defStyle) {
    // ...
}