package top.mcwebsite.java.demo.rxjava2;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import java.util.concurrent.TimeUnit;

public class SimpleRxJava2DemoSkip {

    public static void main(String[] args) throws InterruptedException {
        Observable.range(1, 100)
                .skip(20)
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

        Observable.just(1, 2, 3, 4, 5, 6, 7, 8)
                .delay(new Function<Integer, ObservableSource<Long>>() {
                    @Override
                    public ObservableSource<Long> apply(Integer integer) throws Exception {
                        return Observable.timer(integer, TimeUnit.SECONDS);
                    }
                })
                .skip(7, TimeUnit.SECONDS)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("accept integer: " + integer);
                    }
                });
        Thread.sleep(100000);
    }

}
