package top.mcwebsite.java.demo.rxjava2;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;

public class SimpleRxJava2DemoReduce {

    public static void main(String[] args) {
        Observable.just(1, 2, 3, 4, 5, 6)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        return integer * integer2;
                    }
                })
                .subscribe((integer) -> {
                    System.out.println("accept " + integer);
                });
    }

}
