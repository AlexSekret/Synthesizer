import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException, IOException, InterruptedException {

//        SynthesizerConsoleSequence.play();

//        String path1 = "D:\\Programming\\Java\\projects\\hexlet-homework\\Synthesizer\\src\\notes.txt";
//        SynthesizerReadTxtFile.playTxtFile(path1); //чтение последовательности нот из текстового файла.
//
        String path2 = "D:\\Programming\\Java\\projects\\hexlet-homework\\Synthesizer\\src\\1.mid";
        SynthesizerReadMidi.play(path2);
    }
}