package mtread;

public class BC {
    volatile boolean free=true;

    public synchronized void cCashier() {
        if(free) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Кассир обслуживает покупателя");
        free=false;
        notify();
    }

    public synchronized void bBuyer() {
        if(!free) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName());
        free=true;
        notify();
    }

}
