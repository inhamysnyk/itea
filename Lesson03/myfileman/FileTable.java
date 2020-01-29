package myfileman;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;
import java.io.File;

class FileTable extends AbstractTableModel {
    private File[] files;
    private FileSystemView fileSystemView;
    private String[] columns;

    FileTable() {
        this(new File[0]);
    }

    private FileTable(File[] files) {
        this.fileSystemView = FileSystemView.getFileSystemView();
        this.columns = new String[]{"Icon", "File", "Path/name"};
        this.files = files;
    }

    public Object getValueAt(int row, int column) {
        File file = this.files[row];
        switch(column) {
            case 0:
                return this.fileSystemView.getSystemIcon(file);
            case 1:
                return this.fileSystemView.getSystemDisplayName(file);
            case 2:
                return file.getPath();
            default:
                System.err.println("Logic Error");
                return "";
        }
    }

    public int getColumnCount() {
        return this.columns.length;
    }

    public Class<?> getColumnClass(int column) {
        switch(column) {
            case 0:
                return ImageIcon.class;
            case 1:
            case 2:
            default:
                return String.class;
            }
    }

    public String getColumnName(int column) {
        return this.columns[column];
    }

    public int getRowCount() {
        return this.files.length;
    }

    File getFile(int row) {
        return this.files[row];
    }

    void setFiles(File[] files) {
        this.files = files;
        this.fireTableDataChanged();
    }
}

