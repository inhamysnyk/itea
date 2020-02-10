package mthread;

public class Transporter implements Runnable {
    private LTU ltu;

    public Transporter(LTU ltu) {
        this.ltu = ltu;
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (Loader.getLoad() < 96) {
            try {
                Thread.sleep(3000);
                ltu.tTransporter();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
