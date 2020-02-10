package mthread;

public class LTU {
    private int ltu;
    volatile boolean freeL=true;
    volatile boolean freeT=false;
    volatile boolean freeU=false;

    public synchronized void lLoader() {
        if(!freeL) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ltu = Loader.getLoad();
        System.out.println("Грузчик грузит телегу. Загрузил всего: " + ltu);
        freeT=false;
        freeT=true;
        freeU=false;
        notify();
    }

    public synchronized void tTransporter() {
        if(!freeT) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Транспортер везет телегу. Перевез всего: " + ltu) ;
        freeT=false;
        freeT=false;
        freeU=true;
        notify();
    }

    public synchronized void uUnloader() {
        if(!freeU) try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Разгрузчик разгружает телегу. Разгрузил всего: " + ltu);
        freeL=true;
        freeT=false;
        freeU=false;
        notify();
    }

}
