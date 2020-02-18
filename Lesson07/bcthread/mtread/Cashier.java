package mtread;

public class Cashier implements Runnable{
    private BC bc;

    public Cashier (BC bc) {
        this.bc = bc;
    }

    @Override
    public void run() {
        while (Buyer.getBuyer() < 30) {
            try {
            Thread.sleep(3000 + (int) (Math.random() * 2000));
            bc.cCashier();
        } catch (InterruptedException e) {
                 e.printStackTrace();
        }
        }
    }
}

