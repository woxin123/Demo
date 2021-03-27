package top.mcwebsite.java.demo.rxjava2;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;

import java.util.ArrayList;
import java.util.List;

public class SimpleRxJava2DemoGroupby {

    public static void main(String[] args) {
        Observable.concat(Observable.range(1, 5), Observable.range(1, 6))
                .groupBy(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        return integer + 1;
                    }
                })
                .subscribe(new Observer<GroupedObservable<Integer, Integer>>() {

                    private int count = 1;

                    private Disposable mDisposable = null;
                    /**
                     * 订阅的时候调用
                     * @param d
                     */
                    @Override
                    public void onSubscribe(Disposable d) {
                        // init
                        mDisposable = d;
                    }

                    /**
                     * 获取数据的时候调用
                     * @param integerObservable
                     */
                    @Override
                    public void onNext(GroupedObservable<Integer, Integer> integerObservable) {
                        integerObservable.subscribe(new Observer<Integer>() {

                            private int countIn = count;
                            private List<String> list = new ArrayList<>();
                            private int key;

                            @Override
                            public void onSubscribe(Disposable d) {
                                key = integerObservable.getKey();
                                System.out.println("第 " + countIn + " 组 subscribe");
                            }

                            @Override
                            public void onNext(Integer integer) {
                                list.add("group " + countIn + " accept integer: " + integer);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                System.out.println("group " + countIn + " complete");
                                System.out.println("group " + countIn + " key = " + key);
                                list.forEach((value) -> {
                                    System.out.println(value);
                                });
                            }
                        });
                        count++;
                    }

                    /**
                     * 当出错的时候调用
                     * @param e
                     */
                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                        e.printStackTrace();
                    }

                    /**
                     * 当数据接收完成的时候调用，但是当调用 Disposable 的 dispose() 方法切断数据流的时候不会调用此方法
                     */
                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }

}
