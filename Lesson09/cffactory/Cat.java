package cffactory;

@Blochable(bc=true)
public class Cat extends Animal {
    boolean bc = true;

    public Cat(String name, String color, int age) {
        super.name = name;
        super.color = color;
        super.age = age;
    }

    @Override
    public String call() throws Exception {
        return "Cat [name=" + name + ", color=" + color + ", age=" + age + "]";
    }
}
