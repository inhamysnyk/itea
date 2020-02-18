package mthread;

public class MainRT {
    public static void main(String[] args) {

        mthread.RandThread thread1 = new mthread.RandThread("Поток А");
        mthread.RandThread thread2 = new mthread.RandThread("Поток В");

        thread1.start();
        thread2.start();

        while ( thread1.isAlive() || thread2.isAlive()) {
		}

        System.out.println("Поток А нарандомил в сумме " + thread1.getRand());
        System.out.println("Поток B нарандомил в сумме " + thread2.getRand());

        if (thread1.getRand() > thread2.getRand()) {
            System.out.println("Поток А нарандомил в сумме большее число = " + thread1.getRand());
        } if (thread1.getRand() < thread2.getRand()){
            System.out.println("Поток B нарандомил в сумме большее число = " + thread2.getRand());
        } if (thread1.getRand() == thread2.getRand()){
            System.out.println("Оба потока нарандомили в сумме одинаковое число = " + thread2.getRand());
        }

    }
}