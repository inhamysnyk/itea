package cffactory;

import java.util.concurrent.Callable;

public class FatCat implements Callable<String> {
    @LuckyCat (lc=true)
    boolean lc = true;

    protected void someMethod() {
    }

    @Override
    public String call() throws Exception {
        return "FatCat";
    }
}
