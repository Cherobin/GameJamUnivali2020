package apiPS;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


import java.io.*;
import java.util.ArrayList;

import javax.sound.sampled.*;

/*
 * Created on 02/02/2009
 * Atualizado Para Verção 1.0
 * Desenvolvido Por Dennis Kerr Coelho
 * PalmSoft Tecnologia
 */

public class PS_SoundPlayer_TESTE implements Runnable{

    Thread thread = null;
    boolean running = false;

    private static AudioInputStream stream;
    private static AudioFormat format = null;
    private static SourceDataLine lines[];
    boolean avaliableline[];

    ArrayList audiosbinarios;
    ArrayList playlist;

    public PS_SoundPlayer_TESTE(String fnm) {
        audiosbinarios = new ArrayList();
        playlist = new ArrayList();

        AudioData audio = new AudioData(createInput(fnm));
        audiosbinarios.add(audio);

        lines = new SourceDataLine[10];

        lines[0] = createLine();
        lines[1] = createLine();
        lines[2] = createLine();
        lines[3] = createLine();
        lines[4] = createLine();
        lines[5] = createLine();
        lines[6] = createLine();
        lines[7] = createLine();
        lines[8] = createLine();
        lines[9] = createLine();

        avaliableline  = new boolean[10];
        avaliableline[0] = true;
        avaliableline[1] = true;
        avaliableline[2] = true;
        avaliableline[3] = true;
        avaliableline[4] = true;
        avaliableline[5] = true;
        avaliableline[6] = true;
        avaliableline[7] = true;
        avaliableline[8] = true;
        avaliableline[9] = true;
    }

    public void addaudiofile(String fnm){
        AudioData audio = new AudioData(createInput(fnm));
        audiosbinarios.add(audio);
    }

    private static byte[] createInput(String fnm)
    // Set up the audio input stream from the sound file
    {
      try {
        // link an audio stream to the sampled sound's file
        stream = AudioSystem.getAudioInputStream( new File(fnm) );
        format = stream.getFormat();
        Console.debug("Audio format: " + format);

        // convert ULAW/ALAW formats to PCM format
        if ( (format.getEncoding() == AudioFormat.Encoding.ULAW) ||
             (format.getEncoding() == AudioFormat.Encoding.ALAW) ) {


          AudioFormat newFormat =
             new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                                  format.getSampleRate(),
                                  format.getSampleSizeInBits()*2,
                                  format.getChannels(),
                                  format.getFrameSize()*2,
                                  format.getFrameRate(), true);  // big endian
          // update stream and format details
          stream = AudioSystem.getAudioInputStream(newFormat, stream);
          Console.debug("Converted Audio format: " + newFormat);
          format = newFormat;
        }
      }
      catch (UnsupportedAudioFileException e)
      {  Console.debug( e.getMessage());
         System.exit(0);
      }
      catch (IOException e)
      {  Console.debug( e.getMessage());
         System.exit(0);
      }


      byte[] samples = null;

      try {
        samples = new byte[stream.available()];
        DataInputStream dis = new DataInputStream(stream);

        dis.readFully(samples);
      }
      catch (IOException e)
      { Console.debug( e.getMessage());
        System.exit(0);
      }

      return samples;

    }  // end of createInput()


    public void addtoplaylist(int track){

        /*if(playlist.size()>=4){
            return;
        }*/

        PlayData pldata = new PlayData();
        pldata.audio = ((AudioData)audiosbinarios.get(track)).audio;
        pldata.source = new ByteArrayInputStream(pldata.audio);
        pldata.buf = new byte[lines[0].getBufferSize()];

        for(int i = 0; i < 10; i ++ ){
            if(avaliableline[i]){
                pldata.line = i;
                avaliableline[i] = false;
                playlist.add(pldata);
                Console.debug("Adicionou "+playlist.size()+" line "+i);
                break;
            }
        }



    }

    private static SourceDataLine createLine()
    // set up the SourceDataLine going to the JVM's mixer
    {
      SourceDataLine line  = null;

      try {
        DataLine.Info info =
              new DataLine.Info(SourceDataLine.class, format);
        if (!AudioSystem.isLineSupported(info)) {
          Console.debug("Line does not support: " + format);
          System.exit(0);
        }
        // get a line of the required format
        line = (SourceDataLine) AudioSystem.getLine(info);
        line.open(format);
      }
      catch (Exception e)
      {  Console.debug( e.getMessage());
         //System.exit(0);
      }

      return line;

    }  // end of createOutput()






    public void iniciar(){

         if (thread == null || !running) {

             thread = new Thread(this);
             running = true;
             thread.start();

             Console.debug("starto");
         }
     }


    public void run() {



         /* while(running){

              Console.debug("rodapirosca");

              for(int i = 0; i < playlist.size();i++){
                  PlayData pdata = (PlayData)playlist.get(i);
                  Console.debug("toca");

                  if(pdata.offset == 0){
                      lines[i].start();
                      Console.debug("start");
                  }

                  int avaliable = lines[i].getBufferSize();
                  int ler = (pdata.offset+avaliable)<=pdata.audio.length?avaliable:pdata.audio.length-pdata.offset;

                  Console.debug(" "+ler+" "+avaliable+" "+pdata.audio.length+" "+ pdata.offset);

                  pdata.offset += lines[i].write(pdata.audio, pdata.offset, ler);

                  Console.debug("play");
                  if(pdata.offset>=pdata.audio.length){
                      lines[i].close();
                      playlist.remove(i);
                  }
              }

              try {
                  thread.sleep(10);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }

          }*/

        while(running){


            for(int i = 0; i < playlist.size();i++){
                  PlayData pdata = (PlayData)playlist.get(i);
                  int iline = pdata.line;

                  lines[iline].start();
                  // read and play chunks of the audio

                  if(pdata.offset==0){
                      try {

                          pdata.dataread = pdata.source.read(pdata.buf, 0, pdata.buf.length);

                          //Console.debug("leu "+pdata.dataread);

                      } catch (IOException e) {
                            // TODO Auto-generated catch block
                        e.printStackTrace();
                      }
                      if(pdata.dataread>0){
                          pdata.offset += lines[iline].write(pdata.buf, pdata.offset, pdata.dataread-pdata.offset);

                          //Console.debug("Toca "+i+" "+ playlist.size()+ " "+iline);
                      }
                  }else{
                      //Console.debug("Toca "+i+" "+ playlist.size()+" "+iline);
                      pdata.offset += lines[iline].write(pdata.buf, pdata.offset, pdata.dataread-pdata.offset);
                  }

                  if(pdata.offset>=pdata.dataread){
                      pdata.offset = 0;
                  }

                  //Console.debug("offset "+pdata.offset);

                  if(pdata.dataread<=0){
                      lines[iline].drain();
                      lines[iline].stop();
                      avaliableline[iline] = true;
                      //lines[0].close();

                      //Console.debug("terminou de tocar");

                      playlist.remove(i);
                  }
              }

              try {
                  thread.sleep(1);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
        }
    }

}

class AudioData{
    byte audio[] = null;
    public AudioData(byte _audio[]){
        audio = _audio;
    }
}

class PlayData{
    byte audio[] = null;
    int offset = 0;
    int dataread = 0;
    int line = 0;

    InputStream source = null;
    byte buf[];
}
