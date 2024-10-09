import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
//Wersja do oceny
    static List<Panel> PanelLineLeftContent = new ArrayList<>();
    static List<Label> LeftLabelInfo = new ArrayList<>();
    static List<TextField> LeftTextField= new ArrayList<>();
    static JFrame RootWindow = new JFrame(Paramets.FrameApp);
    static JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

    static Panel focusPanel=new Panel(10,250,Paramets.PanelSizeXLeft-20,50,"#00000");
    static Panel controlPanel = new Panel(10,Paramets.FrameSizeY-220,100,170,"#00000");
    static Label focusLabel=new Label(0,0,100,50,"Wybierz id");
    static CheckBox focusCheckbox= new CheckBox();



    static Button add_button = new Button("Dodaj");
    static Button delete_button = new Button("Usun");
    static Button refresh_button = new Button("Odswiez");
    static Button reset_button = new Button("Reset");



    static Path path = Paths.get("zasoby.txt");


    static boolean IsChecked=false;
    static int SelectedId;

    static int Id=0;



    public static void main(String[] args) {
            LoadData();
            add_button.addActionListener(e -> AddItem());
            refresh_button.addActionListener(e -> LoadData());
            delete_button.addActionListener(e -> DeleteButton());
            reset_button.addActionListener(e -> Reset());
    }
    static void LoadData(){
        RootWindow.getContentPane().removeAll();
        int FileLine = Instruction.CountLineInFile(Paramets.NameofFile[0]);
        RootWindow.repaint();
        RootWindow.setPreferredSize(new Dimension(Paramets.FrameSizeX, Paramets.FrameSizeY));
        RootWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        RootWindow.setLayout(null);
        RootWindow.setResizable(false);
        RootWindow.setVisible(true);
        LoadLeftPanel(FileLine);
        LoadRightPanel(Paramets.NameofFile[0]);
        RootWindow.pack();
    }
    static void LoadLeftPanel(int LineInFile) {
        Panel leftpanel = new Panel(0, 0, Paramets.PanelSizeXLeft, Paramets.FrameSizeY, "#DF94DF");
        RootWindow.add(leftpanel);
        leftpanel.removeAll();
        leftpanel.setOpaque(false);
        leftpanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 5, Color.decode("#023395D")));

        ImageIcon originalIcon = new ImageIcon(Paramets.logo);
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(Paramets.logosize[0],Paramets.logosize[1], Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel imageLabel = new JLabel(scaledIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setBounds(0,200,100,100);
        leftpanel.add(imageLabel);

        leftpanel.add(focusPanel);
        focusPanel.setOpaque(false);
        focusPanel.setLayout((new GridLayout(1, 3)));
        focusPanel.add(focusLabel);
        focusPanel.add(spinner);
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(true);
        spinner.setModel(new SpinnerNumberModel(Id, 0, LineInFile, 1));
        if (Id != 0){
            spinner.setModel(new SpinnerNumberModel(Id, Id, Id, 0));
            ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);
        }
        focusPanel.add(focusCheckbox);
        focusCheckbox.addItemListener(e->CheckCheckBox());
        leftpanel.add(controlPanel);
        controlPanel.setLayout(new GridLayout(4, 1));
        controlPanel.add(add_button);
        controlPanel.add(delete_button);
        controlPanel.add(refresh_button);
        controlPanel.add(reset_button);
        for(int i=0;i!=Paramets.RightLabelInfo.length;i++){
            PanelLineLeftContent.add(new Panel(10,i*50,Paramets.PanelSizeXLeft-20,50,"#DF94DF"));
            PanelLineLeftContent.get(i).setOpaque(false);
            PanelLineLeftContent.get(i).setLayout(new GridLayout(2, 1));
            leftpanel.add(PanelLineLeftContent.get(i));
            LeftLabelInfo.add(new Label(0,0,100,50,Paramets.RightLabelInfo[i]));
            PanelLineLeftContent.get(i).add(LeftLabelInfo.get(i));
            LeftTextField.add(new TextField(100,0,100,20));
            PanelLineLeftContent.get(i).add(LeftTextField.get(i));
        }
        leftpanel.revalidate();
        leftpanel.repaint();
    }


    static void LoadRightPanel(String file){
        Panel rightpanel = new Panel(Paramets.PanelSizeXLeft,0,Paramets.PanelSizeXRight,Paramets.FrameSizeY,"#023395D");
        RootWindow.add(rightpanel);
        rightpanel.removeAll();
        Panel rightpanelListOfItem = new Panel(10,10,Paramets.ListOfItemsPanelX,Paramets.ListOfItemsPanelY,"#DF94DF");
        rightpanel.add(rightpanelListOfItem);



        Panel rightpanelListName = new Panel(20,0,Paramets.ListOfItemsPanelX,50,"#DF94DF");
        rightpanelListOfItem.add(rightpanelListName);
        rightpanelListName.setBounds(0, 0, Paramets.ListOfItemsPanelX, 50);
        rightpanelListName.setLayout(new GridLayout(1,Paramets.RightLabelInfo.length+1));
        int counter=Paramets.RightLabelInfo.length;
        rightpanelListName.add(new Label(0, 0, 0, 0, ""));
        for(int i=0; i<counter;i++) {
           rightpanelListName.add(new Label(0, 0, 0, 0, Paramets.RightLabelInfo[i]));
        }



        JPanel rightpanelItem = new JPanel();
        rightpanelItem.setLayout(null);
        rightpanelItem.setBackground(Color.decode("#DF94DF"));
        JScrollPane scrollPane = new JScrollPane(rightpanelItem);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(0, 50, Paramets.ListOfItemsPanelX, Paramets.ListOfItemsPanelY-50);
        rightpanelListOfItem.add(scrollPane);

        DoLabel(rightpanelItem,file);
        rightpanel.revalidate();
        rightpanel.repaint();

    }
    static void DoLabel(Container container,String File){
        int Line = Instruction.CountLineInFile(File);
        String[] LineRead = Instruction.FileReader(File);
        for (int i = 0; i < Line; i++) {
            JPanel rightpanelListName = new JPanel();
            rightpanelListName.setBounds(0, (i) * 50, Paramets.ListOfItemsPanelX, 50);
            rightpanelListName.setLayout(new GridLayout(1,Paramets.RightLabelInfo.length+1));
            String[] ary = LineRead[i].split(" ");
            JLabel label = new JLabel(" "+(i + 1) + ". ");
            rightpanelListName.add(label);
            int counter=Paramets.RightLabelInfo.length+1;
            for(int r=1; r<counter;r++) {
                rightpanelListName.add(new JLabel(ary[r-1]));
                rightpanelListName.setToolTipText(LineRead[i]);
            }
            rightpanelListName.setBackground(Color.decode("#023395D"));
            rightpanelListName.setOpaque(false);
            if(Id!=0&&(i+1)==Id){
                rightpanelListName.setBackground(Color.decode("#034efc"));
                rightpanelListName.setOpaque(true);
            }
            container.add(rightpanelListName);
        }

        container.setPreferredSize(new Dimension(Paramets.ListOfItemsPanelX, Line * 50));
    }

    static void AddItem(){
        boolean Empty=true;
        String[] information = new String[Paramets.RightLabelInfo.length];
        for(int i=0;i!=Paramets.RightLabelInfo.length;i++){
            if (LeftTextField.get(i).getText().equals("")){
                information[i]="?";
                Empty=true;
            }
            else{
                information[i]=LeftTextField.get(i).getText();
                Empty=false;
            }
        }
        if (Empty){
            WarningFrame(Paramets.ErrorList[0],information);
        }
        else {
            Instruction.FileSaver(information,Paramets.NameofFile[0]);
            LoadData();
        }
    }


   static void CheckCheckBox(){
        int ValueOfItem = ((Integer) spinner.getValue());
        boolean CheckBox = focusCheckbox.isSelected();
        if(ValueOfItem!=0 || Id!=0) {
            //Blocking text field
            if (CheckBox) {
                Id = Integer.parseInt(spinner.getValue().toString());
                IsChecked = true;
            } else {
                Id = 0;
                IsChecked = false;
            }
            LoadData();
        }
       else{
           // Odznaczenie checkboxa
            if (CheckBox) {
                IsChecked = false;
                focusCheckbox.setSelected(false);
                Error error = new Error(Paramets.ErrorList[1]);
                error.raport();
            }
       }
    }
   static void DeleteButton() {
        try {
            if (!IsChecked) {
                Error error = new Error(Paramets.ErrorList[1]);
                error.raport();
            } else {
                SelectedId = Integer.parseInt(spinner.getValue().toString());
                System.out.println(SelectedId);
                List<String> linie = Files.readAllLines(path);
                linie.remove(SelectedId - 1);
                Files.write(path, linie);
                IsChecked = false;
                focusCheckbox.setSelected(false);
                int newLineInFile = Instruction.CountLineInFile(Paramets.NameofFile[0]);
                spinner.setModel(new SpinnerNumberModel(1, 1, newLineInFile, 1));
                LoadData();
            }
        } catch (IOException e) {
            Instruction.FatalError(e);
        }
    }


    static void Reset(){
        Instruction.Reset();
        LoadData();
   }
   static void WarningFrame(String ErrorValue,String[] Info){
       JFrame WarningFrame = new JFrame("Error");

       WarningFrame.setLayout(new BoxLayout(WarningFrame.getContentPane(), BoxLayout.Y_AXIS));
       Label label=new Label(0,0,300,50,ErrorValue);
       WarningFrame.add(label);
       label=new Label(0,0,300,50,"Czy dodac produkt?");
       WarningFrame.add(label);
       JPanel buttonPanel = new JPanel();
       buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
       Button Add=new Button("Add",100,50);
       Button ReturnButton=new Button("Return",100,50);
       buttonPanel.add(Add);
       buttonPanel.add(ReturnButton);
       WarningFrame.add(buttonPanel);

       Add.addActionListener(e->DoIt(Info,WarningFrame));
       ReturnButton.addActionListener(e->Instruction.CloseWindow(WarningFrame));

       WarningFrame.setDefaultCloseOperation(WarningFrame.EXIT_ON_CLOSE);
       WarningFrame.setPreferredSize(new Dimension(400,200));
       WarningFrame.setVisible(true);
       WarningFrame.setResizable(false);
       WarningFrame.pack();

   }
    static void DoIt(String[] Info, Frame WarningFrame){
        Instruction.FileSaver(Info,Paramets.NameofFile[0]);
        Instruction.CloseWindow(WarningFrame);
        LoadData();
    }
}