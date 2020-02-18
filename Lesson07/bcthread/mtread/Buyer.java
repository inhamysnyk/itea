package mtread;

public class Buyer implements Runnable {
    private static int buyer;
    private BC bc;

    public Buyer(BC bc) {
        this.bc = bc;
    }

    @Override
    public void run() {
        while (Buyer.getBuyer() < 30) {
            try {
                Thread.sleep(3000);
            buyer++;
            bc.bBuyer();
            } catch (InterruptedException e) {
                e.printStackTrace();
                }
            }
        }

    public static int getBuyer() {
        return buyer;
    }
}