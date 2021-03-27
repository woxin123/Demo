package top.mcwebsite.java.demo.rxjava2.subject;

import io.reactivex.subjects.ReplaySubject;

public class SimpleReplaySubjectDemo {
    public static void main(String[] args) {
        ReplaySubject<Integer> rs = ReplaySubject.create();
        // 无论什么时候订阅都会受到 1, 2, 3
        rs.onNext(1);
        rs.onNext(2);
        rs.onNext(3);
        rs.onComplete();
        rs.subscribe(value -> {
            System.out.println("accept value = " + value);
        });
    }
}
