public interface BeatObserver {
    void startWasPushed();
    void stopWasPushed();
    void upTempoWasPushed();
    void downTempoWasPushed();
    void clearWasPushed();
    void serializeWasPushed();
    void loadWasPushed();
}
