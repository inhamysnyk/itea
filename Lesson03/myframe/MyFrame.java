package myframe;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyFrame extends JFrame {

    private JTextField textFieldUs;
    private JTextField textFieldFr;
    private JTextField textFieldDe;

    public static void main(String[] args) {
        new MyFrame();
    }

    private MyFrame() {
        super("My Frame");
        setSize(400, 200);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setBounds(dimension.width / 2, 250, dimension.height / 2, 250);
        setDefaultCloseOperation(super.EXIT_ON_CLOSE);
        createGUI();
        setVisible(true);
    }

    private void createGUI() {

        //устанавливаем компоненты на форме
        Dimension labelSize = new Dimension(80, 80);
        Border solidBorder = BorderFactory.createLineBorder(Color.BLACK, 2);

        // создаем основную панель на форме
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // создаем панель на форме
        JPanel textPanel = new JPanel();

        textFieldUs = new JTextField();
        textFieldUs.setColumns(20);
        textPanel.add(textFieldUs);

        textFieldFr = new JTextField();
        textFieldFr.setColumns(20);
        textPanel.add(textFieldFr);

        textFieldDe = new JTextField();
        textFieldDe.setColumns(20);
        textPanel.add(textFieldDe);

        // создаем  панель
        JPanel textBottomPanel = new JPanel();

        // читаем текст из пропертей
        Locale[] locales;
        locales = new Locale[]{new Locale("us", "US"),
                new Locale("fr", "Fr"),
                new Locale("de", "De")};

        String nameUs = null, nameFr = null, nameDe = null;

        for (Locale locale : locales) {
            ResourceBundle bundle =
                    ResourceBundle.getBundle("MessageBundle", locale);
            if (locale.getCountry().equals("US")) nameUs = bundle.getString("name");
            if (locale.getCountry().equals("FR")) nameFr = bundle.getString("name");
            if (locale.getCountry().equals("DE")) nameDe = bundle.getString("name");
        }

        String finalNameUs = nameUs;
        String finalNameFr = nameFr;
        String finalNameDe = nameDe;

        // создаем кнопки
        try {
            BufferedImage imageUsa = ImageIO.read(new File("flag_usa.png"));
            Icon iconUsa = new ImageIcon(resize(imageUsa, 35, 35));
            JButton buttonUsa = new JButton("");
            buttonUsa.setIcon(iconUsa);
            buttonUsa.addActionListener((e) -> textFieldUs.setText(finalNameUs));

            BufferedImage imageFrf = ImageIO.read(new File("flag_frf.png"));
            Icon iconFrf = new ImageIcon(resize(imageFrf, 35, 35));
            JButton buttonFrf = new JButton("");
            buttonFrf.setIcon(iconFrf);
            buttonFrf.addActionListener((e) -> textFieldFr.setText(finalNameFr));

            BufferedImage imageDem = ImageIO.read(new File("flag_dem.png"));
            Icon iconDem = new ImageIcon(resize(imageDem, 35, 35));
            JButton buttonDem = new JButton("");
            buttonDem.setIcon(iconDem);
            buttonDem.addActionListener((e) -> textFieldDe.setText(finalNameDe));

            // выводим на панель
            textBottomPanel.add(buttonUsa);
            textBottomPanel.add(buttonFrf);
            textBottomPanel.add(buttonDem);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //выводим панель на форму
        mainPanel.add(textBottomPanel, BorderLayout.NORTH);

        // создаем панель для Legion_Commander_icon
        JPanel pPanel = new JPanel();

        try {
            BufferedImage imageP = ImageIO.read(new File("Legion_Commander_icon.png"));
            BufferedImage image = resize(imageP, 80, 80);
            JLabel areaP = new JLabel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(image, 0, 0, null);
                }
            };
            areaP.setVerticalAlignment(JLabel.TOP);
            areaP.setHorizontalAlignment(JLabel.LEFT);
            areaP.setPreferredSize(labelSize);
            areaP.setBorder(solidBorder);
            areaP.setForeground(Color.BLACK);
            pPanel.add(areaP);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //выводим панель на форму
        mainPanel.add(pPanel, BorderLayout.WEST);

        //выводим панель на форму
        mainPanel.add(textPanel, BorderLayout.CENTER);

        // создаем панель для кнопок
        JPanel bottomPanel = new JPanel();

        //создаем кнопки
        JButton button1 = new JButton("SAVE");
        JButton button2 = new JButton("LOAD");

        button1.setBackground(new Color(32, 164, 117, 205));
        button1.setForeground(Color.WHITE);
        button1.setBounds(100, 100, 85, 30);

        // сохраняем в файл
        button1.addActionListener(e -> {
            Saved saved = new Saved(textFieldUs.getText(), textFieldFr.getText(), textFieldDe.getText());
            //создаем 2 потока для сериализации объекта и сохранения его в файл
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream("save.ser");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            ObjectOutputStream objectOutputStream = null;
            try {
                objectOutputStream = new ObjectOutputStream(outputStream);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // сохраняем игру в файл
            try {
                objectOutputStream.writeObject(saved);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            //закрываем поток и освобождаем ресурсы
            try {
                objectOutputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        //загружаем из файла
        button2.addActionListener(e -> {

            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream("save.ser");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            ObjectInputStream objectInputStream = null;
            try {
                objectInputStream = new ObjectInputStream(fileInputStream);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                Saved saved = (Saved) objectInputStream.readObject();
                String loadT = String.valueOf(saved);

                String loadUs = loadT.substring(loadT.indexOf("Us=")+3, loadT.indexOf("Fr="));
                String loadFr = loadT.substring(loadT.indexOf("Fr=")+4,loadT.indexOf("e="));
                String loadDe = loadT.substring(loadT.indexOf("De=")+3);

                textFieldUs.setText(loadUs);
                textFieldFr.setText(loadFr);
                textFieldDe.setText(loadDe);

            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }

            try {
                objectInputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        //добавляем кнопки на панель вывода кнопок
        bottomPanel.add(button1);
        bottomPanel.add(button2);

        //выодим панель с кнопками
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        setPreferredSize(new Dimension(350, 350));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // уменьшаем картинку
    private static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return dimg;
    }
}

