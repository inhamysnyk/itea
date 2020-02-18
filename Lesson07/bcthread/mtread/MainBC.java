package mtread;

public class MainBC {
    public static void main(String[] args) {
        BC bc = new BC();

        Thread сashier = new Thread(new Cashier(bc));
        сashier.setName("Поток кассир");
        сashier.start();

        for (int i=1; i<=5; i++){
            Thread buyer = new Thread(new Buyer(bc));
            buyer.setName("Поток покупателей: " + i);
            buyer.start();
            }
    }
}
