package gmthread;

public class Barrack extends Thread {
    private String name;

    Barrack (String name){
        super();
        this.name=name;
    }

    @Override
    public  void run() {
        synchronized (this)      {
            int count = 5;
            while (Workers.miningWorkers() < 990) {
                count++;
                //   Thread.currentThread().setName(name + count);
                System.out.println(name + count);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}