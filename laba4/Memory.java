package com.company;

import java.util.ArrayList;

public class Memory {
    private int sizeDisk;
    private int sizeSector;
    private CellsClass[] cells;

    public Memory(int sizeDisc, int sizeSector) {
        this.sizeDisk = sizeDisc;
        this.sizeSector = sizeSector;
        cells = new CellsClass[sizeDisc / sizeSector];
        for (int i = 0; i < cells.length; i++) {
            cells[i] = new CellsClass(sizeSector);
        }
    }

    public CellsClass[] getCells() {
        return cells;
    }

    public void searchPlace(ManagerFile file) {
        int buf = -2;
        for (int i = 0, writeCells = 0; i < cells.length && writeCells != file.getSize(); i++) {
            if (cells[i].getStatus() == 0) {
                cells[i].setStatus(1);
                if (buf == -2) {
                    file.setIndexFirstCell(i);
                }
                else {
                    cells[buf].setIndex(i);
                }
                buf = i;
                writeCells++;
                if (writeCells == file.getSize()) {
                    cells[i].setIndex(-1);
                }
            }
        }
    }

    public void chooseFile(ManagerFile file) {
        notChooseFile();
        if (file.isFolder()) {
            for (int i = 0; i <file.getChild().size(); i++) {
                chooseFileWhenNotChoose(file.getChild().get(i));
            }
        }
        int index = file.getIndexFirstCell();
        for (int i = 0; i < file.getSize(); i++) {
            if(index==-1) {
                break;
            }
            cells[index].setStatus(2);
            index = cells[index].getIndex();
        }
    }
    public void chooseFileWhenNotChoose(ManagerFile file) {
        if (file.isFolder()) {
            for (int i = 0; i <file.getChild().size(); i++) {
                chooseFileWhenNotChoose(file.getChild().get(i));
            }
        }
        int index = file.getIndexFirstCell();
        for (int i = 0; i < file.getSize(); i++) {
            if(index==-1) {
                break;
            }
            cells[index].setStatus(2);
            index = cells[index].getIndex();
        }
    }

    public void notChooseFile() {
        for (int i = 0; i <cells.length ; i++) {
            if (cells[i].getStatus()==2) {
                cells[i].setStatus(1);
            }
        }
    }

    public void clearFile(ManagerFile file) {
        if (!file.isFolder()) {
            int index = file.getIndexFirstCell();
            for (int i = 0; i < file.getSize(); i++) {
                if(index ==-1) {
                    continue;
                }
                cells[index].setStatus(0);
                index = cells[index].getIndex();
            }
        }
        if (file.isFolder()) {
            ArrayList<ManagerFile> deletedChild = file.getChild();
            for (int i = 0; i < deletedChild.size(); i++) {
                clearFile(deletedChild.get(deletedChild.size()-1));
            }
            cells[file.getIndexFirstCell()].setStatus(0);
        }
    }
}