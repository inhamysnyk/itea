package gmthread;

public class Workers extends Thread {
    private String name;
    private static int mining;

    Workers (String name){
        super();
        this.name=name;
    }

    @Override
    public void run() {
            while (Workers.miningWorkers() < 990) {
                try {
                    Thread.sleep(1000);
                   // Thread.currentThread().getName());
                    mining += 3;
                    System.out.println("Рабочие намайнили " + mining);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    public static int miningWorkers() {
        return mining;
    }
}




