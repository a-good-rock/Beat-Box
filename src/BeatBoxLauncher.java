public class BeatBoxLauncher {
    public static void main(String[] args) {
        BeatBoxView view = new BeatBoxView();
        BeatBoxController controller = new BeatBoxController(view, new BeatBoxModel());
        controller.createBeatBox();
    }
}
