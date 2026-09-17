package epsilon.model.entities.audio;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import epsilon.model.dataStructure.linearStructure.statik.NumericArray;
import epsilon.model.entities.audio.wavFile.WavFile;
import epsilon.model.entities.audio.wavFile.WavFileException;
import static epsilon.utils.FunctionUtils.objectToDouble;

public class AudioTrack{
    private final long numFrames; 
    private final int numChannels; 
    private double[] ogBuffer;
    private NumericArray bufferArray;
    private URL url;
    private Clip clip;
    private WavFile wavFileRead;
    private double volume;
    private int currentFrame;
    public AudioTrack(String file){
        try {
            url = this.getClass().getResource("/" + file);
            File filed = new File(url.toURI());
            wavFileRead = WavFile.openWavFile(filed);
            clip = AudioSystem.getClip();
        } catch (WavFileException | IOException | URISyntaxException | LineUnavailableException ex) {
            System.err.println(ex);
        }
        currentFrame = 0;
        volume = 1;
        numChannels = wavFileRead.getNumChannels();
        numFrames = wavFileRead.getNumFrames();
    }
    public void readFile(){
        try {
            wavFileRead.display();

            ogBuffer = new double[(int) numFrames * numChannels];
            bufferArray = new NumericArray(ogBuffer.length);

            int framesRead;

            framesRead = wavFileRead.readFrames(ogBuffer, (int) numFrames);

            for (int s = 0; s < framesRead * numChannels; s++) {
                bufferArray.add(ogBuffer[s]);
            }

            wavFileRead.close();
        } catch (WavFileException | IOException e) {
            System.err.println(e);
        }
    }
    private byte[] extractBytes(){
        byte[] byteData = new byte[bufferArray.size()*2];
        for (int i = 0; i < bufferArray.size(); i++) {
            double sample = Math.max(-1.0, Math.min(1.0, objectToDouble(bufferArray.get(i))));
            short pcm = (short) Math.round(sample * 32767.0);
            byteData[2*i] = (byte) (pcm & 0xFF);
            byteData[(2*i)+1] = (byte) ((pcm >> 8) & 0xFF);
        }
        return byteData;
    }
    public void startTrack(){
        if(clip.isOpen()){
            clip.stop();
            clip.close();
        }
        try {
            AudioFormat format = AudioSystem.getAudioFileFormat(url).getFormat();
            byte[] data = extractBytes();
            clip.open(format, data, 0, data.length);
            clip.start();
            clip.setFramePosition(currentFrame);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
            System.err.println(ex);
        }
    }
    public void pauseTrack(){
        if(clip.isOpen() && isRunning()){
            clip.stop();
        }
    }
    public void resumeTrack(){
        if(clip.isOpen() && !isRunning()){
            clip.start();
        }
    }
    public void stopTrack(boolean storePos){
        if(clip.isOpen()){
            if(storePos){
                 currentFrame = clip.getFramePosition();
            }
            clip.close();
        }
    }
    public void stopTrack(){
        stopTrack(false);
    }
    public boolean isRunning(){
        return clip.isRunning();
    }
    public void setFramePosition(int currentFrame){
        if(isRunning()){
            clip.setFramePosition(currentFrame);
        }
        else{
            this.currentFrame = currentFrame;
        }
    }
    public void reverseTrack(){
        bufferArray.reverse();
    }
    public void invertAudio(){
        bufferArray.multiplyScalar(-1);
    }
    public void setVolume(double volume){
        bufferArray.divideScalar(this.volume);
        if(volume > 100){
            volume = 100;
        }
        else if(volume <= 0){
            volume = Double.MIN_NORMAL;
        }
        this.volume = volume;
        bufferArray.multiplyScalar(volume);
    }
}