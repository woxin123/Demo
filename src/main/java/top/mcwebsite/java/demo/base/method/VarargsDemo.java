package top.mcwebsite.java.demo.base.method;

/**
 * 可变参数
 */
public class VarargsDemo {

    public static void main(String[] args) {
        // 正常调用
        prints("Hello", " ", "World", "\n");
        // 传递数组
        Integer[] integers = new Integer[]{1, 2, 3};
        prints(integers);
        System.out.println();
        // 传递数组 基本类型
        int[] nIntegers = new int[]{1, 2, 3};
        // 这里会把 nIntegers 认为是一个 Object 的元素，所以会打印 [I@12a3a380
        prints(nIntegers);
        System.out.println();
    }

    private static void prints(Object ...args) {
        for (int i = 0; i < args.length; i++) {
            System.out.print(args[i]);
        }
    }

}
