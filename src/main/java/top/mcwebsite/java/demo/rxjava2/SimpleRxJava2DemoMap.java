package top.mcwebsite.java.demo.rxjava2;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class SimpleRxJava2DemoMap {

    public static void main(String[] args) {

        StringBuilder rxOperatorsText = new StringBuilder();

        Observable.create(new ObservableOnSubscribe<Integer>() {
            /**
             *
             * @param emitter ObservableEmitter 的意思是发射器，用来发送数据
             * @throws Exception
             */
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                System.out.println("Observable emit 1");
                emitter.onNext(1);
                System.out.println("Observable emit 2");
                emitter.onNext(2);
                System.out.println("Observable emit 3");
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "This is result " + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                rxOperatorsText.append("accept : " + s + "\n");
            }
        });

        System.out.println(rxOperatorsText);

    }
}
