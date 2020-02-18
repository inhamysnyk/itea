package cffactory;

import java.lang.reflect.Field;
import java.util.concurrent.*;

public class MainCFF {

    public static void main(String[] args) {
        Class clazz = Cat.class;

        ExecutorService ex= Executors.newFixedThreadPool(2);
        Future<String> f1;
        Future<String> f2;
        Future<String> f3;
        Future<String> f4;
        Future<String> f5;
        Future<String> f6;
        Future<String> f7;

        f1=ex.submit(new Cat("Vaska","Black",4));
        f2=ex.submit(new Cat("Ryzhik","Сarroty",3));
        f3=ex.submit(new Cat("Pushok","White",5));
        f4=ex.submit(new Cat("Garfild","Сarroty",5));
        f5=ex.submit(new Cat("Pirat","Black",7));
        f6=ex.submit(new HomelessCat());
        f7=ex.submit(new FatCat());

        try {
            System.out.println(f1.get());
            CatFarhFactory(clazz);
            System.out.println(f2.get());
            CatFarhFactory(clazz);
            System.out.println(f3.get());
            CatFarhFactory(clazz);
            System.out.println(f4.get());
            CatFarhFactory(clazz);
            System.out.println(f5.get());
            CatFarhFactory(clazz);
            System.out.println(f6.get());
            System.out.println("LuckyCat = true" + " - гуляет");
            System.out.println(f7.get());
            System.out.println("Paw = 2" + " - гуляет");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        ex.shutdown();
        System.out.println("End CatFarhFactory");

    }

    private static void CatFarhFactory(Class clazz) {
        Field[] fields=clazz.getFields();

            for (Field field : fields) {

               if (field.getName() == "name" ){
                if (field.isAnnotationPresent(Blochable.class) == false) {
                        System.out.println("Blochable = " + field.isAnnotationPresent(Blochable.class) + " - идет на фарш");
                   }
            }
            }

        }
 }


