import javax.sound.midi.*;
import java.io.*;

public class SynthesizerReadMidi {
    public static void play(String path2) throws MidiUnavailableException, InvalidMidiDataException, InterruptedException, IOException {
        /*
         * ShortMessage myMsg = new ShortMessage();
         * //Play the note Middle C (60) moderately loud
         * //(velocity = 93)on channel 4 (zero-based).
         * myMsg.setMessage(ShortMessage.NOTE_ON, 4, 60, 93);
         * Synthesizer synth = MidiSystem.getSynthesizer();
         * Receiver synthRcvr = synth.getReceiver();
         * synthRcvr.send(myMsg, -1); // -1 means no time stamp
         */
        Synthesizer synth = MidiSystem.getSynthesizer();  //конструируем синтезатор
        Receiver synthRcvr = synth.getReceiver();         //конструируем ресивер, для отправки сообщений синтезатору
        synth.open();                                    //синтезатор должен быть открытым, чтобы ресивер мог отправлять сообщения

        Sequencer mySequencer = MidiSystem.getSequencer(); //конструируем секвенсор
        if (mySequencer == null) {
            // Error -- mySequencer device is not supported.
            // Inform user and return...
            System.out.println("Проблемы с секвенсором");
        } else {
            // Acquire resources and make operational.
            mySequencer.open();
        }


        playMidiFile(mySequencer, path2);
        synth.close();
    }

    private static void playMidiFile(Sequencer mySequencer, String path) throws InvalidMidiDataException, IOException, InterruptedException {
        // Get default mySequencer.
        String file = path; //"D:\\Programming\\Java\\projects\\hexlet-homework\\Synthesizer\\src\\1.mid"
        File myMidiFile = new File(path);
        // Construct a Sequence object, and
        // load it into my mySequencer.
        Sequence mySeq = MidiSystem.getSequence(myMidiFile);
        var seqLength = mySeq.getMicrosecondLength();
        mySequencer.setSequence(mySeq);
        mySequencer.setLoopStartPoint(0);
        mySequencer.start();
        Thread.sleep(seqLength);
        mySequencer.stop();
        mySequencer.close();
    }
}