package cffactory;

import java.util.concurrent.Callable;

public abstract class Animal implements Callable<String> {
    public String name ;
    public String color;
    public int age;

    public Animal() {
    }

    public Animal(Animal animal) {
        name=animal.getName();
        color=animal.getColor();
        age=animal.getAge();
    }

    public Animal(String name, String color, int age) {
        this.name = name;
        this.color = color;
        this.age = age;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Cat [name=" + name + ", color =" + color + ", age=" + age + "]";
    }
}



