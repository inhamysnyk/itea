package mthread;

public class Loader implements Runnable {
    private static int load;
    private LTU ltu;

    public Loader(LTU ltu) {
        this.ltu = ltu;
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (Loader.getLoad() < 96) {
            try {
                    Thread.sleep(3000);
                    load += 6;
                    ltu.lLoader();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
        public static int  getLoad() {
            return load;
        }
}
