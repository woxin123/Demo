package top.mcwebsite.java.demo.rxjava2;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

public class SimpleRxJava2DemoCompose {

    public static void main(String[] args) {
        ObservableTransformer<Integer, String> transformer =  upstream -> {
            return upstream.filter(integer -> {
                return integer > 10;
            }).map(Object::toString);
        };

        System.out.println("Observable.just----");
        Observable.just(8, 9, 10, 11, 12, 13, 14, 15, 16)
                .compose(transformer)
                .subscribe(System.out::println);

        System.out.println("Observable.range-----");
        Observable.range(1, 20)
                .compose(transformer)
                .subscribe(System.out::println);
    }

}
