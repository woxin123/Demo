package top.mcwebsite.java.demo.rxjava2;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class SimpleRxJava2DemoIsEmpty {

    public static void main(String[] args) {
        Observable.empty()
                .isEmpty()
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public   void accept(Boolean aBoolean) throws Exception {
                        System.out.println("accept " + aBoolean);
                    }
                });

        Observable.just(1)
                .isEmpty()
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        System.out.println("accept " + aBoolean);
                    }
                });
    }

}
