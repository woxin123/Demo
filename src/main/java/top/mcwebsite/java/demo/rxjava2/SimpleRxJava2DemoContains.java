package top.mcwebsite.java.demo.rxjava2;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

public class SimpleRxJava2DemoContains {

    public static void main(String[] args) {
        Observable.just(1, 2, 3, 4, 5, 6)
                .contains(4)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        System.out.println("accept " + aBoolean);
                    }
                });
    }

}
