package top.mcwebsite.java.demo.rxjava2;

import io.reactivex.Observable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class SimpleRxJava2DemoCount {

    public static void main(String[] args) {
        // 把流中的 1, 2, 3, 4, 5, 6  collect 到集合中
        Observable.range(1, 10000)
                .count()
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        System.out.println("accept: " + aLong);
                    }
                });
    }

}
