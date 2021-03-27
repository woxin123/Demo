package top.mcwebsite.java.demo.rxjava2;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class SimpleRxJava2DemoWindow {

    public static void main(String[] args) {
        Observable.range(1, 20)
                .window(5)
                .subscribe(new Observer<Observable<Integer>>() {

                    private Disposable mDisposable = null;
                    private int count = 1;

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
                     * @param integers
                     */
                    @Override
                    public void onNext(Observable<Integer> integers) {

                        integers.subscribe(new Consumer<Integer>() {
                            private int countIn = count;
                            @Override
                            public void accept(Integer integer) throws Exception {
                                System.out.println("group " + countIn + " accept: " + integer);
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
