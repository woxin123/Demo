package top.mcwebsite.java.demo.base.annotation;

import java.util.ArrayList;
import java.util.List;

class NatureNumber {
    private int i;

    public NatureNumber(int i) {
        this.i = i;
    }
}

class EvenNumber extends NatureNumber {
    public EvenNumber(int i) {
        super(i);
    }
}

public class TestWildcard {
    public static void main(String[] args) {
        List<EvenNumber> le = new ArrayList<>();
        List<? extends NatureNumber> ln = le;
//        ln.add(new NatureNumber(1));
    }
}
