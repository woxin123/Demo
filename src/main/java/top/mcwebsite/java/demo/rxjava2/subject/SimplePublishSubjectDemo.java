package top.mcwebsite.java.demo.rxjava2.subject;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class SimplePublishSubjectDemo {
    public static void main(String[] args) throws InterruptedException {
        PublishSubject<Integer> bs = PublishSubject.create();
        bs.onNext(1);
        bs.onNext(2);
        bs.onNext(3);
//        bs.onComplete();
        bs.subscribe(integer -> {
            System.out.println("accept: " + integer);
        });
        for (int i = 0; i < 100; i++) {
            bs.onNext(i);
        }
    }
}
