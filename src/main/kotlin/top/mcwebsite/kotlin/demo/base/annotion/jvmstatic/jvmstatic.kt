package top.mcwebsite.kotlin.demo.base.annotion.jvmstatic

class Data {
    companion object {
        @JvmStatic
        fun getDefaultDataName() = "default"
    }
}