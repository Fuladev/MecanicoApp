package com.mecanico.mecanicoapp.recursos;

public class SpinnerDirections {
    private String DirId, DirName, Dir;

    public SpinnerDirections(String dirId, String dirName, String dir) {
        DirId = dirId;
        DirName = dirName;
        Dir = dir;
    }

    public String getDirId() {
        return DirId;
    }

    public void setDirId(String dirId) {
        DirId = dirId;
    }

    public String getDirName() {
        return DirName;
    }

    public void setDirName(String dirName) {
        DirName = dirName;
    }

    public String getDir() {
        return Dir;
    }

    public void setDir(String dir) {
        Dir = dir;
    }

    @Override
    public String toString() {
        return DirName;
    }
}
