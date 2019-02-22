import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BeatBoxView {

    JPanel mainPanel;
    JFrame theFrame;
    ArrayList<JCheckBox> checkBoxList;
    String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat",
            "Acoustic Snare", "Crash Cymbal", "Hand Clap", "High Tom", "Hi Bongo",
            "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap", "Low-mid Tom",
            "High Agogo", "Open Hi Conga"};
    BeatObserver observer;
    JButton start;
    JButton stop;
    JButton load;
    JButton upTempo;
    JButton downTempo;
    JButton clear;

    public void buildGUI() {
        theFrame = new JFrame("Cyber BeatBox");
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);
        background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Box buttonBox = new Box(BoxLayout.Y_AXIS);

        start = new JButton("Start");
        start.addActionListener(new MyStartListener());
        buttonBox.add(start);

        stop = new JButton("Stop");
        stop.addActionListener(new MyStopListener());
        buttonBox.add(stop);

        upTempo = new JButton("Tempo Up");
        upTempo.addActionListener(new MyUpTempoListener());
        buttonBox.add(upTempo);

        downTempo = new JButton("Tempo Down");
        downTempo.addActionListener(new MyDownTempoListener());
        buttonBox.add(downTempo);

        clear = new JButton("Clear Instruments");
        clear.addActionListener(new MyClearListener());
        buttonBox.add(clear);

        JButton save = new JButton("Save Pattern");
        save.addActionListener(new MySendListener());
        buttonBox.add(save);

        load = new JButton("Load Pattern");
        load.addActionListener(new MyReadInListener());
        buttonBox.add(load);

        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for (int i = 0; i < 16; i++) {
            nameBox.add(new Label(instrumentNames[i]));
        }


        background.add(BorderLayout.EAST, buttonBox);
        background.add(BorderLayout.WEST, nameBox);


        theFrame.getContentPane().add(background);

        GridLayout grid = new GridLayout(16, 16);
        grid.setVgap(1);
        grid.setHgap(2);
        mainPanel = new JPanel(grid);
        background.add(BorderLayout.CENTER, mainPanel);

        checkBoxList = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkBoxList.add(c);
            mainPanel.add(c);
        }

        theFrame.setBounds(50, 50, 300, 300);
        theFrame.pack();
        theFrame.setVisible(true);
        disableLoadButton();
        disableStopButton();
        disableTempoButtons();
    }

    public void disableStartButton() {
        start.setEnabled(false);
    }

    public void enableStartButton() {
        start.setEnabled(true);
    }

    public void disableStopButton() {
        stop.setEnabled(false);
    }

    public void enableStopButton() {
        stop.setEnabled(true);
    }

    public void disableTempoButtons() {
        upTempo.setEnabled(false);
        downTempo.setEnabled(false);
    }

    public void enableTempoButtons() {
        upTempo.setEnabled(true);
        downTempo.setEnabled(true);
    }

    public void disableLoadButton() {
        load.setEnabled(false);
    }

    public void enableLoadButton() {
        load.setEnabled(true);
    }

    void clearPattern() {
        for (JCheckBox item : checkBoxList) {
            item.setSelected(false);
        }
    }

    public class MyStartListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            observer.startWasPushed();
        }
    }
    public class MyStopListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            observer.stopWasPushed();
        }
    }
    public class MyUpTempoListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            observer.upTempoWasPushed();
        }
    }
    public class MyDownTempoListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            observer.downTempoWasPushed();
        }
    }
    public class MyClearListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            observer.clearWasPushed();
        }
    }
    public class MySendListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            observer.serializeWasPushed();
            }
    }

    public class MyReadInListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            observer.loadWasPushed();
        }
    }

    public void registerObserver(BeatObserver observer) {
        this.observer = observer;
    }
}
