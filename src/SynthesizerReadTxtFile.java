import javax.sound.midi.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class SynthesizerReadTxtFile {
    /* Идентифкаторы нот синтезатора.
     * Конструктор Класса ShortMessage принимает значение ноты в виде целых чисел.
     * Поэтому для читаемости и избавления от магических чисел мы как бы сопоставляем общепринятое наименование нот их
     * представлению в целых числах
     */
    private static final byte C = 60; // do
    private static final byte D = 62; // re
    private static final byte E = 64; // mi
    private static final byte F = 65; // fa
    private static final byte G = 67; // sol
    private static final byte A = 69; // la
    private static final byte B = 70; // si
/*
## Задание
 * [X] доработать программу, чтобы считывать несколько нот в одной строке и проигрывать их;
 * [X] - считывать ноты из текстового файла и проигрывать;
 * [X] - воспроизводить MIDI-файлы (в гугле легко находятся MIDI-файлы и код как их воспроизводить с помощью джавы)
 */

    public static void playTxtFile (String path) throws MidiUnavailableException, InvalidMidiDataException, InterruptedException, IOException {
        /*
         * ShortMessage myMsg = new ShortMessage();
         * //Play the note Middle C (60) moderately loud
         * //(velocity = 93)on channel 4 (zero-based).
         * myMsg.setMessage(ShortMessage.NOTE_ON, 4, 60, 93);
         * Synthesizer synth = MidiSystem.getSynthesizer();
         * Receiver synthRcvr = synth.getReceiver();
         * synthRcvr.send(myMsg, -1); // -1 means no time stamp
         */
        if (path.length() < 1) {
            System.out.println("usage: FileTest file");
            System.exit(-1);
        }

        Path file = Paths.get(path);
        Synthesizer synth = MidiSystem.getSynthesizer();  //конструируем синтезатор
        Receiver synthRcvr = synth.getReceiver();         //конструируем ресивер, для отправки сообщений синтезатору
        synth.open();                                     //синтезатор должен быть открытым, чтобы ресивер мог отправлять сообщения

        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                Enter a notes.
                You can enter a single note and press enter,
                or enter multiple notes separated by a space and press enter.
                Case doesn't matter
                To exit press q then enter""");

        String text = SynthesizerReadTxtFile.readNotesFromFile(file);
        String[] noteSequence = text.toUpperCase().split(" ");
        System.out.println(Arrays.toString(noteSequence));
        while (!text.equalsIgnoreCase("q")) {
            var i = 0;
            for (i = 0; i < noteSequence.length; i++) {
                byte noteID = convertToID(noteSequence[i]);
                playNote(synthRcvr, noteID);
            }
            text = scanner.nextLine().toUpperCase();
            noteSequence = text.split(" ");
        }
        synth.close();
        scanner.close();
    }

    private static void playNote(Receiver receiver, byte noteID) throws InvalidMidiDataException, InterruptedException {
        ShortMessage msg = new ShortMessage(ShortMessage.NOTE_ON, noteID, 100);
        receiver.send(msg, -1);
        Thread.sleep(500);
        msg = new ShortMessage(ShortMessage.NOTE_OFF, noteID, 100);
        receiver.send(msg, -1);
    }

    private static byte convertToID(String note) {
        switch (note.trim()) {
            case "A":
                return A;
            case "B":
                return B;
            case "C":
                return C;
            case "D":
                return D;
            case "E":
                return E;
            case "F":
                return F;
            case "G":
                return G;
            default:
                System.out.println("Entered incorrect note: " + note);
                return A;
        }
    }


    public static String readNotesFromFile(Path aFileArg) throws IOException {
        InputStream in = Files.newInputStream(aFileArg);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line = null;
        String textSequence = "";
        while ((line = reader.readLine()) != null) {
            textSequence = textSequence + line.trim() + " ";
        }
        return textSequence;
    }
}