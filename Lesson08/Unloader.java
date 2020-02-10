package mthread;

public class Unloader implements Runnable {
    private LTU ltu;

    public Unloader(LTU ltu) {
        this.ltu = ltu;
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (Loader.getLoad() < 96) {
            try {
                Thread.sleep(3000);
                ltu.uUnloader();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
