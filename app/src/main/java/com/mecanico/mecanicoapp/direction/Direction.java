package com.mecanico.mecanicoapp.direction;

public class Direction {
    String name, dirComplete;

    @Override
    public String toString() {
        return "Direction{" +
                "name='" + name + '\'' +
                ", dirComplete='" + dirComplete + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirComplete() {
        return dirComplete;
    }

    public void setDirComplete(String dirComplete) {
        this.dirComplete = dirComplete;
    }
}
