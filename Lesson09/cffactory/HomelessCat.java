package cffactory;

import java.util.concurrent.Callable;

public class HomelessCat implements Callable<String> {

    @Paw(pc=2)
    public void someMethod( ) {
    }

    @Override
    public String call() throws Exception {
        return "HomelessCat";
    }
}
