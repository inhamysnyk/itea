package myfileman;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.Desktop.Action;
import java.io.File;
import java.nio.file.*;
import java.util.List;
import java.util.Objects;

import static java.awt.FlowLayout.LEADING;
import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;

public class MyFileMan extends JFrame {
    private static Desktop desktop;
    private static FileSystemView fileSystemView;
    private static File currentFile;
    private static JPanel mPanel;
    private static JTree tree;
    private static DefaultTreeModel treeModel;
    private static JTable table;
    private static JProgressBar progressBar;
    private static FileTable fileTable;
    private static ListSelectionListener listSelectionListener;
    private static boolean cellSizesSet = false;
    private static int rowIconPadding = 6;
    private static JRadioButton isDirectory;
    private static JRadioButton isFile;
    private static JPanel newFilePanel;
    private static JRadioButton newTypeFile;
    private static JTextField name;

    public static void main(String[] args) {
        new MyFileMan();
    }

    private MyFileMan() {
        super("My FileManager");
        setSize(800, 800);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setBounds(dimension.width / 2, 200, dimension.height / 2, 200);
        setDefaultCloseOperation(super.EXIT_ON_CLOSE);
        MyFileManGUI();
        setVisible(true);
    }

    private void MyFileManGUI() {
        mPanel = new JPanel(new BorderLayout(3, 3));
        mPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        fileSystemView = FileSystemView.getFileSystemView();
        desktop = Desktop.getDesktop();
        JPanel detailView = new JPanel(new BorderLayout(3, 3));
        table = new JTable();
        table.setSelectionMode(0);
        table.setAutoCreateRowSorter(true);
        table.setShowVerticalLines(false);
        listSelectionListener = lse -> {
            int row = table.getSelectionModel().getLeadSelectionIndex();
            setFileDetails(((FileTable) table.getModel()).getFile(row));
        };
        table.getSelectionModel().addListSelectionListener(listSelectionListener);
        JScrollPane tableScroll = new JScrollPane(table);
        Dimension d = tableScroll.getPreferredSize();
        tableScroll.setPreferredSize(new Dimension((int) d.getWidth(), (int) d.getHeight() / 2));
        detailView.add(tableScroll, "Center");
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        treeModel = new DefaultTreeModel(root);
        TreeSelectionListener treeSelectionListener = tse -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tse.getPath().getLastPathComponent();
            showChildren(node);
            setFileDetails((File) node.getUserObject());
        };
        File[] roots = fileSystemView.getRoots();
        int count;
        int ii;
        for (File fileSystemRoot : roots) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(fileSystemRoot);
            root.add(node);
            File[] files = fileSystemView.getFiles(fileSystemRoot, true);
            count = files.length;

            for (ii = 0; ii < count; ++ii) {
                File file = files[ii];
                if (file.isDirectory()) {
                    node.add(new DefaultMutableTreeNode(file));
                }
            }
        }
        tree = new JTree(treeModel);
        tree.setRootVisible(false);
        tree.addTreeSelectionListener(treeSelectionListener);
        tree.expandRow(0);
        JScrollPane treeScroll = new JScrollPane(tree);
        tree.setVisibleRowCount(15);
        Dimension preferredSize = treeScroll.getPreferredSize();
        Dimension widePreferred = new Dimension(200, (int) preferredSize.getHeight());
        treeScroll.setPreferredSize(widePreferred);
        JPanel fileMainDetails = new JPanel(new BorderLayout(4, 2));
        fileMainDetails.setBorder(new EmptyBorder(0, 6, 0, 6));
        JPanel fileDetailsLabels = new JPanel(new GridLayout(0, 1, 2, 2));
        fileMainDetails.add(fileDetailsLabels, "West");
        JPanel fileDetailsValues = new JPanel(new GridLayout(0, 1, 2, 2));
        fileMainDetails.add(fileDetailsValues, "Center");
        fileDetailsLabels.add(new JLabel("", SwingConstants.TRAILING));
        JPanel flags = new JPanel(new FlowLayout(LEADING, 4, 0));
        isDirectory = new JRadioButton("Directory");
        isDirectory.setEnabled(false);
        flags.add(isDirectory);
        isFile = new JRadioButton("File");
        isFile.setEnabled(false);
        flags.add(isFile);
        fileDetailsValues.add(flags);
        count = fileDetailsLabels.getComponentCount();

        for (ii = 0; ii < count; ++ii) {
            fileDetailsLabels.getComponent(ii).setEnabled(false);
        }
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        JButton openFile = new JButton("Open");
        openFile.setMnemonic('o');
        openFile.addActionListener(ae -> {
            try {
                if (currentFile == null) showErrorMessage("No file selected to open.", "Select File");
                else {
                    desktop.open(currentFile);
                }
            } catch (Throwable var3) {
                showThrowable(var3);
            }
            mPanel.repaint();
        });
        toolBar.add(openFile);
        JButton editFile = new JButton("Edit");
        editFile.setMnemonic('e');
        editFile.addActionListener(ae -> {
            try {
                if (currentFile == null) showErrorMessage("No file selected to edit.", "Select File");
                else {
                    desktop.edit(currentFile);
                }
            } catch (Throwable var3) {
                showThrowable(var3);
            }

        });
        toolBar.add(editFile);
        openFile.setEnabled(desktop.isSupported(Action.OPEN));
        editFile.setEnabled(desktop.isSupported(Action.EDIT));
        toolBar.addSeparator();
        JButton newFile = new JButton("New");
        newFile.setMnemonic('n');
        newFile.addActionListener(ae -> newFile());
        toolBar.add(newFile);
        JButton renameFile = new JButton("Rename");
        renameFile.setMnemonic('r');
        renameFile.addActionListener(ae -> renameFile());
        toolBar.add(renameFile);
        JButton copyFile = new JButton("Copy");
        copyFile.setMnemonic('c');
        copyFile.addActionListener(ae -> copyFile());
        toolBar.add(copyFile);
        JButton deleteFile = new JButton("Delete");
        deleteFile.setMnemonic('d');
        deleteFile.addActionListener(ae -> deleteFile());
        toolBar.add(deleteFile);
        JPanel fileView = new JPanel(new BorderLayout(3, 3));
        fileView.add(toolBar, "North");
        fileView.add(fileMainDetails, "Center");
        detailView.add(fileView, "South");
        JSplitPane splitPane = new JSplitPane(HORIZONTAL_SPLIT, treeScroll, detailView);
        mPanel.add(splitPane, "Center");
        JPanel simpleOutput = new JPanel(new BorderLayout(3, 3));
        progressBar = new JProgressBar();
        simpleOutput.add(progressBar, "East");
        progressBar.setVisible(false);
        mPanel.add(simpleOutput, "South");

        getContentPane().add(mPanel);
        setLocationByPlatform(true);
        setMinimumSize(getSize());
        pack();
        setVisible(true);
    }

    private static TreePath findTreePath(File find) {
        for (int ii = 0; ii < tree.getRowCount(); ++ii) {
            TreePath treePath = tree.getPathForRow(ii);
            Object object = treePath.getLastPathComponent();
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) object;
            File nodeFile = (File) node.getUserObject();
            if (nodeFile == find) {
                return treePath;
            }
        }
        return null;
    }

    private static void  copyFile() {
        if (currentFile == null) showErrorMessage("No directory/file selected to copy.", "Select Directory/File");
        else {
            String copyTo = JOptionPane.showInputDialog(mPanel, "New Name");
            if (copyTo != null) {
                try {
                    TreePath parentPath = findTreePath(currentFile.getParentFile());
                    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) Objects.requireNonNull(parentPath).getLastPathComponent();
                    boolean directory = currentFile.isDirectory();
                    Path path = Paths.get(String.valueOf(currentFile));
                    Path copyPath = Paths.get(currentFile.getParentFile() +"\\"+copyTo);
                    Files.createFile(copyPath);
                    Files.copy(path, copyPath, StandardCopyOption.REPLACE_EXISTING);
                       if (directory) {
                            Path pathDir = Paths.get(currentFile.getParentFile() +"\\"+copyTo);
                            Path Dir = Files.createDirectories(pathDir);
                            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(Dir);
                            treeModel.insertNodeInto(newNode, parentNode, parentNode.getChildCount());
                        }
                       showChildren(parentNode);
                } catch (Throwable var8) {
                    showThrowable(var8);
                }
                mPanel.repaint();
            }
        }
    }

    private static void renameFile() {
        if (currentFile == null) showErrorMessage("No file selected to rename.", "Select File");
        else {
            String renameTo = JOptionPane.showInputDialog(mPanel, "New Name");
            if (renameTo != null) {
                try {
                    boolean directory = currentFile.isDirectory();
                    TreePath parentPath = findTreePath(currentFile.getParentFile());
                    DefaultMutableTreeNode parentNode = null;
                    if (parentPath != null) {
                        parentNode = (DefaultMutableTreeNode)parentPath.getLastPathComponent();
                    }
                    boolean renamed = currentFile.renameTo(new File(currentFile.getParentFile(), renameTo));
                    if (renamed) {
                        if (directory) {
                            TreePath currentPath = findTreePath(currentFile);
                            assert currentPath != null;
                            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)currentPath.getLastPathComponent();
                            treeModel.removeNodeFromParent(currentNode);
                        }
                        showChildren(parentNode);
                    } else {
                        String msg = "The file '" + currentFile + "' could not be renamed.";
                        showErrorMessage(msg, "Rename Failed");
                    }
                } catch (Throwable var8) {
                    showThrowable(var8);
                }
            }
            mPanel.repaint();
        }
    }

    private static void deleteFile() {
        if (currentFile == null) {
            showErrorMessage("No file selected for deletion.", "Select File");
        } else {
            int result = JOptionPane.showConfirmDialog(mPanel, "Are you sure you want to delete this file?", "Delete File", JOptionPane.YES_NO_OPTION);
            if (result == 0) {
                try {
                    TreePath parentPath = findTreePath(currentFile.getParentFile());
                    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) Objects.requireNonNull(parentPath).getLastPathComponent();
                    boolean directory = currentFile.isDirectory();
                    boolean deleted = currentFile.delete();
                    if (deleted) {
                        if (directory) {
                            TreePath currentPath = findTreePath(currentFile);
                            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) Objects.requireNonNull(currentPath).getLastPathComponent();
                            treeModel.removeNodeFromParent(currentNode);
                          }
                        showChildren(parentNode);
                    } else {
                        String msg = "The file '" + currentFile + "' could not be deleted.";
                        showErrorMessage(msg, "Delete Failed");
                    }
                } catch (Throwable var8) {
                    showThrowable(var8);
                }
            }
            mPanel.repaint();
        }
    }

    private static void newFile() {
        if (currentFile == null) {
            showErrorMessage("No location selected for new file.", "Select Location");
        } else {
            if (newFilePanel == null) {
                newFilePanel = new JPanel(new BorderLayout(3, 3));
                JPanel southRadio = new JPanel(new GridLayout(1, 0, 2, 2));
                newTypeFile = new JRadioButton("File", true);
                JRadioButton newTypeDirectory = new JRadioButton("Directory");
                ButtonGroup bg = new ButtonGroup();
                bg.add(newTypeFile);
                bg.add(newTypeDirectory);
                southRadio.add(newTypeFile);
                southRadio.add(newTypeDirectory);
                name = new JTextField(15);
                newFilePanel.add(new JLabel("Name"), "West");
                newFilePanel.add(name);
                newFilePanel.add(southRadio, "South");
            }

            int result = JOptionPane.showConfirmDialog(mPanel, newFilePanel, "Create File", JOptionPane.OK_CANCEL_OPTION);
            if (result == 0) {
                try {
                    File parentFile = currentFile;
                    if (!parentFile.isDirectory()) {
                        parentFile = parentFile.getParentFile();
                    }
                    File file = new File(parentFile, name.getText());
                    boolean created;
                    if (newTypeFile.isSelected()) {
                        created = file.createNewFile();
                    } else {
                        created = file.mkdir();
                    }
                    if (created) {
                        TreePath parentPath = findTreePath(parentFile);
                        DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) Objects.requireNonNull(parentPath).getLastPathComponent();
                        if (file.isDirectory()) {
                            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(file);
                            treeModel.insertNodeInto(newNode, parentNode, parentNode.getChildCount());
                        }
                        showChildren(parentNode);
                    } else {
                        String msg = "The file '" + file + "' could not be created.";
                        showErrorMessage(msg, "Create Failed");
                    }
                } catch (Throwable var10) {
                    showThrowable(var10);
                }
            }
            mPanel.repaint();
        }
    }

    private static void showErrorMessage(String errorMessage, String errorTitle) {
        JOptionPane.showMessageDialog(mPanel, errorMessage, errorTitle, JOptionPane.ERROR_MESSAGE);
    }

    private static void showThrowable(Throwable t) {
        t.printStackTrace();
        JOptionPane.showMessageDialog(mPanel, t.toString(), t.getMessage(), JOptionPane.ERROR_MESSAGE);
        mPanel.repaint();
    }

    private static void setTableData(final File[] files) {
        SwingUtilities.invokeLater(() -> {
            if (fileTable == null) {
                fileTable = new FileTable();
                table.setModel(fileTable);
            }
            table.getSelectionModel().removeListSelectionListener(listSelectionListener);
            fileTable.setFiles(files);
            table.getSelectionModel().addListSelectionListener(listSelectionListener);
            if (!cellSizesSet) {
                Icon icon = fileSystemView.getSystemIcon(files[0]);
                table.setRowHeight(icon.getIconHeight() + rowIconPadding);
                setColumnWidth(-1);
                cellSizesSet = true;
            }
        });
    }

    private static void setColumnWidth(int width) {
        TableColumn tableColumn = table.getColumnModel().getColumn(0);
        if (width < 0) {
            JLabel label = new JLabel((String)tableColumn.getHeaderValue());
            Dimension preferred = label.getPreferredSize();
            width = (int)preferred.getWidth() + 14;
        }
        tableColumn.setPreferredWidth(width);
        tableColumn.setMaxWidth(width);
        tableColumn.setMinWidth(width);
    }

    private static void showChildren(final DefaultMutableTreeNode node) {
        tree.setEnabled(false);
        progressBar.setVisible(true);
        progressBar.setIndeterminate(true);
        SwingWorker<Void, File> worker = new SwingWorker<Void, File>() {
            public Void doInBackground() {
                File file = (File)node.getUserObject();
                if (file.isDirectory()) {
                    File[] files = fileSystemView.getFiles(file, true);
                    if (node.isLeaf()) for (File child : files) if (child.isDirectory()) publish(child);
                    setTableData(files);
                }
                return null;
            }

            protected void process(List<File> chunks) {
                for (File child : chunks) {
                    node.add(new DefaultMutableTreeNode(child));
                }
            }

            protected void done() {
                progressBar.setIndeterminate(false);
                progressBar.setVisible(false);
                tree.setEnabled(true);
            }
        };
        worker.execute();
    }

    private static void setFileDetails(File file) {
        currentFile = file;
        isDirectory.setSelected(file.isDirectory());
        isFile.setSelected(file.isFile());
        JFrame f = (JFrame)mPanel.getTopLevelAncestor();
        if (f != null) {
            f.setTitle("My FileManager :: " + fileSystemView.getSystemDisplayName(file));
        }
        mPanel.repaint();
    }
}
