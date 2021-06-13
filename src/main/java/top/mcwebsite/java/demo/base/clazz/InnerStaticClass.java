package top.mcwebsite.java.demo.base.clazz;

public class InnerStaticClass {

    static class StaticInner {

        void sayHello() {
            System.out.println("Hello World!");
        }

    }

    public static void main(String[] args) {
        new StaticInner().sayHello();
    }

}
