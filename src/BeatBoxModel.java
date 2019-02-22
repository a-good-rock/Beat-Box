import javax.sound.midi.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class BeatBoxModel {

    ArrayList<JCheckBox> checkboxList;
    Sequencer sequencer;
    Sequence sequence;
    Track track;
    int[] instruments = {35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63};

    public BeatBoxModel() {
        checkboxList = new ArrayList<>();
        setUpMidi();
        createCheckBoxList();
    }

    public void createCheckBoxList() {
        for (int i = 0; i < 256; i++) {
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkboxList.add(c);
        }
    }

    public void setUpMidi() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(120);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buildTrackAndStart () {
        int[] trackList = null;

        sequence.deleteTrack(track);
        track = sequence.createTrack();

        for (int i = 0; i < 16; i++) {
            trackList = new int[16];

            int key = instruments[i];

            for (int j = 0; j < 16; j++) {
                JCheckBox jc = checkboxList.get(j + (16 * i));
                if (jc.isSelected()) {
                    trackList[j] = key;
                } else {
                    trackList[j] = 0;
                }
            }

            makeTracks(trackList);
            track.add(makeEvent(176, 1, 127, 0, 16));
        }

        track.add(makeEvent(192, 9, 1, 0, 15));
        try {

            sequencer.setSequence(sequence);
            sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
            sequencer.setTempoInBPM(120);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeTracks(int[] list) {
        for (int i = 0; i < 16; i++) {
            int key = list[i];

            if (key != 0) {
                track.add(makeEvent(144, 9, key, 100, i));
                track.add(makeEvent(128, 9, key, 100, i+1));
            }
        }
    }

    public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);

        } catch(Exception a) {a.printStackTrace(); }
        return event;
    }

    void stopSequencer() {
        sequencer.stop();
    }

   void createUpTempo() {
        float tempoFactor = sequencer.getTempoFactor();
        sequencer.setTempoFactor((float) (tempoFactor * 1.03));
    }

    void createDownTempo() {
        float tempoFactor = sequencer.getTempoFactor();
        sequencer.setTempoFactor((float) (tempoFactor * 0.97));
    }


    void serializePattern() {
        boolean[] checkBoxState = new boolean[256];

        for (int i = 0; i < 256; i++) {
            JCheckBox check = checkboxList.get(i);
            if (check.isSelected()) {
                checkBoxState[i] = true;
            }
        }
        try {
            FileOutputStream fileStream = new FileOutputStream(new File("Checkbox.ser"));
            ObjectOutputStream os = new ObjectOutputStream(fileStream);
            os.writeObject(checkBoxState);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    void loadPattern() {
        boolean[] checkboxState = null;
        try {
            FileInputStream fileIn = new FileInputStream(new File("Checkbox.ser"));
            ObjectInputStream is = new ObjectInputStream(fileIn);
            checkboxState = (boolean[]) is.readObject();

        } catch(Exception ex) { ex.printStackTrace(); }

        for (int i = 0; i < 256; i++) {
            JCheckBox check = checkboxList.get(i);
            if (checkboxState[i]) {
                check.setSelected(true);
            } else {
                check.setSelected(false);
            }
        }

        sequencer.stop();
        buildTrackAndStart();
    }
}
