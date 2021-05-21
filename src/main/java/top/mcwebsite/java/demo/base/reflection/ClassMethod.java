package top.mcwebsite.java.demo.base.reflection;

public class ClassMethod {

    public static void main(String[] args) {
        Number a = Integer.valueOf(1);
        System.out.println(a.getClass().getCanonicalName());
    }

}
