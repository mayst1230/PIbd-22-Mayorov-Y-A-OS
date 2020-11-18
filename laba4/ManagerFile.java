package com.company;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

public class ManagerFile {

    private ManagerFile rootFile = new ManagerFile("root", null, true, 1);
    private ManagerFile selected = rootFile;
    private ManagerFile fileForCopy;
    private Memory physMemory;
    private String name;
    private ManagerFile parent;
    private boolean folder;
    public ArrayList child;
    private int indexFirstCell;
    private int size = -1;

    private ManagerFile(Memory PhisMemory) {
        this.physMemory = PhisMemory;
        rootFile.setSize(1);
        PhisMemory.searchPlace(rootFile);
    }

    public ManagerFile(String name, ManagerFile parent, boolean folder, int size) {
        this.name = name;
        this.parent = parent;
        this.folder = folder;
        this.size = size;

        if (folder) {
            child = new ArrayList();
        }
    }

    public ManagerFile() {

    }

    public ManagerFile clone() {
        ManagerFile newFile = new ManagerFile();
        newFile.setSize(size);
        newFile.setName(name);
        newFile.setFolder(folder);
        if (folder) {
            ArrayList child = new ArrayList();
            for (ManagerFile managerFile : this.child) {
                child.add(managerFile.clone());
            }
            newFile.setChild(child);
        }
        return newFile;
    }

    public ManagerFile getRootFile() {
        return rootFile;
    }

    public ManagerFile getSelected() {
        return selected;
    }

    public void setSelectedFile(DefaultMutableTreeNode node) {
        this.selected = (ManagerFile) node.getUserObject();
    }

    public ManagerFile copy() {
        return fileForCopy = selected;
    }

    public void copyFilesCatalog(ManagerFile newFile) {
        for (ManagerFile file : newFile.getChild()) {
            physMemory.searchPlace(file);
            if (file.isFolder()) {
                copyFilesCatalog(file);
            }
        }
    }

    public boolean paste() {
        if (selected.isFolder()) {
            try {
                ManagerFile newFile = fileForCopy.clone();
                newFile.setParent(selected);
                selected.getChild().add(newFile);
                physMemory.searchPlace(newFile);
                if (newFile.isFolder()) {
                    copyFilesCatalog(newFile);
                }
            } catch (Exception eх) {
                eх.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean createFile(String nameFile, boolean folder, int size) {
        if (selected.isFolder()) {
            ManagerFile newFile = new ManagerFile(nameFile, selected, folder, size);
            if (folder) {
                newFile.setSize(1);
            } else {
                newFile.setSize(size);
            }
            physMemory.searchPlace(newFile);
            selected.getChild().add(newFile);
            return true;
        } else {
            return false;
        }
    }

    public boolean delete() {
        if (selected == rootFile) {
            return false;
        } else {
            selected.getParent().getChild().remove(selected);
            if (selected.isFolder()) {
                deleteFolder(selected.getChild());
            }
            physMemory.clearFile(selected);
        }
        return true;
    }

    public void deleteFolder(ArrayList<ManagerFile> files) {
        for (ManagerFile file : files) {
            if (file.isFolder()) {
                deleteFolder(file.getChild());
            }
            physMemory.clearFile(file);
        }
    }

    public void move() {
        for (int i = 0; i < selected.getParent().child.size(); i++) {
            if (selected.getParent().child.get(i) == selected) {
                selected.getParent().child.remove(i);
            }
        }
    }

    public String toString() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ManagerFile getParent() {
        return parent;
    }

    public void setParent(ManagerFile parent) {
        this.parent = parent;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isFolder() {
        return folder;
    }

    public void setFolder(boolean folder) {
        this.folder = folder;
    }

    public ArrayList getChild() {
        return child;
    }

    public void setChild(ArrayList child) {
        this.child = child;
    }

    public int getIndexFirstCell() {
        return indexFirstCell;
    }

    public void setIndexFirstCell(int indexFirstCell) {
        this.indexFirstCell = indexFirstCell;
    }
}