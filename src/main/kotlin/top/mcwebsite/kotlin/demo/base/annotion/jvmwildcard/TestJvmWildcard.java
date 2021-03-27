package top.mcwebsite.kotlin.demo.base.annotion.jvmwildcard;

import java.util.ArrayList;
import java.util.List;

public class TestJvmWildcard {
    public static void main(String[] args) {
        List<Number> list = new ArrayList<>();
        list.add(1);
        List<? extends Number> nList = JvmwildcardKt.transformList(list);
    }
}
