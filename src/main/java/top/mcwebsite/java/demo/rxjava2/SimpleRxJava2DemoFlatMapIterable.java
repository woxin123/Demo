package top.mcwebsite.java.demo.rxjava2;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

import java.util.ArrayList;
import java.util.List;

public class SimpleRxJava2DemoFlatMapIterable {

    public static void main(String[] args) {
        Observable.just(1, 2, 3)
                .flatMapIterable(new Function<Integer, Iterable<Integer>>() {
                    @Override
                    public Iterable<Integer> apply(Integer integer) throws Exception {
                        List<Integer> list = new ArrayList<>();
                        for (int i = 0; i < 5; i++) {
                            list.add(integer * 5 * i);
                        }
                        return list;
                    }
                })
                .subscribe(new Observer<Integer>() {

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
                     * @param integer
                     */
                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("accept: " + integer);
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
