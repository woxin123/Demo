package top.mcwebsite.java.demo.rxjava2;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SimpleRxJava2DemoAmb {

    public static void main(String[] args) throws InterruptedException {
        List<Observable<Long>> observableList = new ArrayList<>();
        observableList.add(Observable.intervalRange(10, 20, 100, 100, TimeUnit.MILLISECONDS));
        observableList.add(Observable.intervalRange(21, 20, 100, 500, TimeUnit.MILLISECONDS));
        Observable.amb(observableList)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        System.out.println("accept " + aLong);
                    }
                });
        Thread.sleep(1000000);
    }

}
