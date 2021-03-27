package top.mcwebsite.java.demo.rxjava2;

import io.reactivex.*;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SimpleRxJava2DemoFlatMap {
    public static void main(String[] args) throws InterruptedException {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 3; i++) {
                    emitter.onNext(i);
                }
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            /**
             * @param integer 接收到的发射的数据
             * @return ObservableSource<String> 被观察者
             * @throws Exception
             */
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I' am value " + integer);
                }
                int delayTime = (int) (Math.random() + 1) * 1000;
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
            }
        }).observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("flatMap : accept : " + s);
                    }
                });

        Thread.sleep(100000);
    }
}
