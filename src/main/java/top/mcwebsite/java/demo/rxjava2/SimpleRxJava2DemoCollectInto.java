package top.mcwebsite.java.demo.rxjava2;

import io.reactivex.Observable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class SimpleRxJava2DemoCollectInto {

    public static void main(String[] args) {
        // 把流中的 1, 2, 3, 4, 5, 6  collect 到集合中
        Observable.just(1, 2, 3, 4, 5, 6)
                .collectInto(new ArrayList<Integer>(), new BiConsumer<List<Integer>, Integer>() {
                    @Override
                    public void accept(List<Integer> integers, Integer integer) throws Exception {
                        integers.add(integer);
                    }
                })
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        System.out.println("accept: " + integers.toString());
                    }
                });
    }

}
