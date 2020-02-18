package gmthread;

public class MainGM {
    public static void main( String[] args ) {

        gmthread.Workers  thread0 = new gmthread.Workers("Рабочий 1");
        gmthread.Workers  thread1 = new gmthread.Workers("Рабочий 2");
        gmthread.Workers  thread2 = new gmthread.Workers("Рабочий 3");
        gmthread.Workers  thread3 = new gmthread.Workers("Рабочий 4");
        gmthread.Workers  thread4 = new gmthread.Workers("Рабочий 5");
        gmthread.Barrack  thread5 = new gmthread.Barrack("Построен Рабочий ");

        thread0.start();
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
    }
}
