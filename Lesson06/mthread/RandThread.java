package mthread;

import java.lang.*;

public class RandThread extends Thread {
    private int rand;
    private String name;

    RandThread(String name){
        super();
        this.name=name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            int randNum = (int) (Math.random() * 100);
            System.out.println(name + " (" + i +") = "+randNum);
            rand+=randNum;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int  getRand() {
        return rand;
    }
}