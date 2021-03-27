package top.mcwebsite.java.demo.rxjava2;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class SimpleRxJava2DemoDefaultIfEmpty {

    public static void main(String[] args) {
        Observable.empty()
                .defaultIfEmpty(10)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object integer) throws Exception {
                        System.out.println("accept: " + integer);
                    }
                });

    }

}
