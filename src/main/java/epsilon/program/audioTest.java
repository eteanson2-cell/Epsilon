package epsilon.program;

import javax.swing.JOptionPane;

import epsilon.model.entities.audio.AudioTrack;

public class audioTest{
    public static void main(String[] args) {
        AudioTrack track = new AudioTrack("test.wav");
        track.readFile();
        track.invertAudio();
        track.startTrack();
        JOptionPane.showMessageDialog(null, "Click OK to stop the music.");
        track.pauseTrack();
        JOptionPane.showMessageDialog(null, "Click OK to resume the music.");
        track.resumeTrack();
        JOptionPane.showMessageDialog(null, "Click OK to stop the music.");
        track.stopTrack();
    }
}