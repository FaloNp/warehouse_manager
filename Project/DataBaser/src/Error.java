import javax.swing.*;
import java.awt.*;

public class Error {

    JFrame ErrorFrame = new JFrame("Error");
    String ErrorValue;

    Error(String errorValue){
        ErrorValue = errorValue;
        Panel panel = new Panel(0, 0, 0, 0, "#515A2A");
        panel.setLayout(new BorderLayout());

        ImageIcon originalIcon = new ImageIcon(Paramets.path);
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(Paramets.errorInfoSize[0], Paramets.errorInfoSize[1], Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel imageLabel = new JLabel(scaledIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(imageLabel, BorderLayout.WEST);

        JLabel errorLabel = new JLabel(ErrorValue);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(errorLabel, BorderLayout.CENTER);

        Button returnButton = new Button("Wroc", 100, 50);
        panel.add(returnButton, BorderLayout.SOUTH);

        ErrorFrame.setBackground(Color.black);
        ErrorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ErrorFrame.setPreferredSize(new Dimension(Paramets.errorSize[0], Paramets.errorSize[1]));
        ErrorFrame.setResizable(false);
        ErrorFrame.toFront();
        ErrorFrame.requestFocus();
        ErrorFrame.setAlwaysOnTop(true);
        ErrorFrame.add(panel);
        errorLabel.setForeground(Color.BLACK);
        returnButton.addActionListener(e -> exitWindow());
        ErrorFrame.pack();
        ErrorFrame.setVisible(true);
    }
    void raport(){
        System.out.println("Error");
    }
    void exitWindow(){
        ErrorFrame.dispose();
    }
}
