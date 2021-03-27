package top.mcwebsite.java.demo.rxjava2;

import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class SimpleRxJava2DemoElementAtOrError {

    public static void main(String[] args) {
//        Observable.range(50, 100)
//                .elementAtOrError(9)
//                .subscribe(new MaybeObserver<Integer>() {
//
//                    private Disposable disposable;
//
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        this.disposable = d;
//                        System.out.println("Maybe subscribe");
//                    }
//
//                    @Override
//                    public void onSuccess(Integer integer) {
//                        System.out.println("success integer: " + integer);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        System.out.println("onError");
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        System.out.println("onComplete");
//                    }
//                });

        Observable.range(40, 101)
                .elementAtOrError(100)
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        System.out.println("accept: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                    }
                });
    }

}
