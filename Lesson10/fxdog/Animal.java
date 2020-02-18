package fxdog;

import java.io.Serializable;

public class Animal implements Serializable {
    protected String name;
    protected String age;

    public Animal() {}

    public Animal(String name, String age) {
        this.name = name;
        this.age = age;
    }

        public Animal(Animal animal) {
            name=animal.getName();
            age=animal.getAge();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Dog [name=" + name + ", age=" + age + "]";
        }

    }

