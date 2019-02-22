import javax.swing.*;
import java.util.ArrayList;

public class BeatBoxController  implements ControllerInterface{

    BeatBoxView view;
    BeatBoxModel model;

    public BeatBoxController(BeatBoxView view, BeatBoxModel model) {
        this.view = view;
        this.model = model;
    }

    public void createBeatBox() {
        view.buildGUI();
    }

    public void modelCheckBoxEqualsView() {
        model.checkboxList = new ArrayList<JCheckBox>(view.checkBoxList);
    }

    public void viewCheckBoxEqualsModel() {
        view.checkBoxList = new ArrayList<JCheckBox>(model.checkboxList);
    }


   public void startWasPushed() {
        for (int i = 0; i < 256; i ++) {
            if (view.checkBoxList.get(i).isSelected()) {
                modelCheckBoxEqualsView();
                model.buildTrackAndStart();
                view.enableTempoButtons();
                view.enableStopButton();
                view.disableStartButton();
                break;
            }
        }
    }

    public void stopWasPushed() {
        view.disableStopButton();
        view.enableStartButton();
        view.disableTempoButtons();
        model.stopSequencer();
    }

    public void upTempoWasPushed() {
        model.createUpTempo();
    }

    public void downTempoWasPushed() {
        model.createDownTempo();
    }

    public void clearWasPushed() {
        view.clearPattern();
        if (model.sequencer.isRunning()) {
            model.sequencer.stop();
            stopWasPushed();
        }
    }

    public void serializeWasPushed() {
        modelCheckBoxEqualsView();
        model.serializePattern();
        view.enableLoadButton();
    }

    public void loadWasPushed() {
        model.loadPattern();
        viewCheckBoxEqualsModel();
    }
}
