package fxdog;

import java.io.Serializable;

public class Owner implements Serializable{
    private String name;

    public Owner() {
    }

    public Owner(Owner owner) {
        name=owner.getName();
    }
    public Owner(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}