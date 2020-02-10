package mthread;

public class MainLTU {
    public static void main( String[] args ){
        LTU ltu = new LTU();
        new Loader(ltu);
        new Transporter(ltu);
        new Unloader(ltu);
    }
}
