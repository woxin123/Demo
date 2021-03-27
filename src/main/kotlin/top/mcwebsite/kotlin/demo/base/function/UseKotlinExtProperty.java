package top.mcwebsite.kotlin.demo.base.function;

public class UseKotlinExtProperty {
    public static void main(String[] args) {
        System.out.println(Property_extKt.getLastChar("kotlin"));
        System.out.println(Property_extKt.getLastChar(new StringBuilder("kotlin")));
        StringBuilder stringBuilder= new StringBuilder("kotlin");
        Property_extKt.setLastChar(stringBuilder, 'c');
        System.out.println(Property_extKt.getLastChar(stringBuilder));
    }
}
