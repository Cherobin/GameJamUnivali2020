package apiPS;
/*
 * Created on 05/10/2005
 * Atualizado Para Verção 1.0
 * Desenvolvido Por Dennis Kerr Coelho
 * PalmSoft Tecnologia
 */

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.IOException;

import javax.sound.midi.Soundbank;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class PS_Som implements LineListener{
    //AudioClip clip;
    //JavaSound
    //boolean tocando = false;
    Clip clip;
    public PS_Som(String fnm){

        AudioInputStream stream;
        try {
            stream = AudioSystem.getAudioInputStream(
            getClass().getResource(fnm) );

            AudioFormat format = stream.getFormat();
            //         convert ULAW/ALAW formats to PCM format

            if((format.getEncoding()==AudioFormat.Encoding.ULAW)||(format.getEncoding()==AudioFormat.Encoding.ALAW)){

                AudioFormat newFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,format.getSampleRate(),format.getSampleSizeInBits()*2,format.getChannels(),format.getFrameSize()*2,format.getFrameRate(), true); // big endian
                //         update stream and format details
                stream = AudioSystem.getAudioInputStream(newFormat, stream);
                Console.debug("Converted Audio format: " + newFormat);
                format = newFormat;
            }
            DataLine.Info info = new DataLine.Info(Clip.class, format);
    //         make sure the sound system supports this data line
            if (!AudioSystem.isLineSupported(info)) {
                Console.debug("Unsupported Clip File: " + fnm);
                System.exit(0);
            }

            clip = (Clip) AudioSystem.getLine(info);

            clip.addLineListener(this);
            clip.open(stream);

    //         duration (in secs) of the clip
            double duration = clip.getBufferSize() / (format.getFrameSize() * format.getFrameRate());

            Console.debug("Duração "+duration);

            //Console.debug("Duration: " + df.format(duration)+" secs");
        } catch (UnsupportedAudioFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    } // end of loadClip()


    public void TocaSom(){
        //clip.play();



        if ((clip != null)&&(clip.isRunning()==false)){

            clip.stop();
            clip.start(); // start playing
            //Console.debug(" DeuPlay");
        }else if(clip.getMicrosecondPosition()>140000){
            //clip.stop();
            //clip.setMicrosecondPosition(100);
            clip.setFramePosition(10);
            //clip.start(); // start playing
            //Console.debug(" replay");
        }

        //Console.debug(" "+clip.getMicrosecondPosition());

    }
    public void TocaSomLoop(){
        //clip.loop();
    }

    public void ParaSom(){
        //clip.stop();
    }


    public void update(LineEvent event) {
        // TODO Auto-generated method stub

    }
}
