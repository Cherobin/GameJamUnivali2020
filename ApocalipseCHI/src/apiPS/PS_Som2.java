package apiPS;
import java.io.*;
import javax.sound.sampled.*;

/*
 * Created on 02/02/2009
 * Atualizado Para Verção 1.0
 * Desenvolvido Por Dennis Kerr Coelho
 * PalmSoft Tecnologia
 */

public class PS_Som2 {


   private static final int ECHO_NUMBER = 1;   // how many echoes to add
   private static final double DECAY = 0.5;    // the decay for each echo

   private static AudioInputStream stream;
   private static AudioFormat format = null;
   private static SourceDataLine line = null;

   byte[] audiosample;


   public PS_Som2(String fnm){


         createInput(fnm);

         /*if (!isRequiredFormat()) {    // not in SamplesPlayer
           Console.debug("Format unsuitable for echoing");
           System.exit(0);
         }*/

         createOutput();

         int numBytes = (int)(stream.getFrameLength() * format.getFrameSize());
         Console.debug("Size in bytes: " + numBytes);

         audiosample = getSamples(numBytes);

   }

   public void play(){
       Console.debug("tocou");
       playsample(audiosample);
   }

   private static void createInput(String fnm)
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
   }  // end of createInput()


   private static boolean isRequiredFormat()
   // Only 8-bit PCM signed or unsigned audio can be echoed
   {
     if ( ((format.getEncoding() == AudioFormat.Encoding.PCM_UNSIGNED) ||
           (format.getEncoding() == AudioFormat.Encoding.PCM_SIGNED)) &&
          (format.getSampleSizeInBits() == 8))
       return true;
     else
       return false;
   }  // end of isRequiredFormat()


   private static void createOutput()
   // set up the SourceDataLine going to the JVM's mixer
   {
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
        System.exit(0);
     }
   }  // end of createOutput()



   private static byte[] getSamples(int numBytes)
   /* Load all the samples from the AudioInputStream as a single
      byte array. Return a _modified_ byte array, after the
      echo effect has been applied. */
   {
      // read the entire stream into samples[]
      byte[] samples = new byte[numBytes];
      DataInputStream dis = new DataInputStream(stream);
      try {
        dis.readFully(samples);
      }
      catch (IOException e)
      { Console.debug( e.getMessage());
        System.exit(0);
      }

      /* Create a byte array by applying the audio effect.
         This line is the main point of difference from
         SamplesPlayer, which returns samples unchanged. */
      //return echoSamples(samples, numBytes);
      return samples;
   } // end of getSamples()






   private static byte[] echoSamples(byte[] samples, int numBytes)
   /* The echo effect is ECHO_NUMBER (4) copies of the original
      sound added to the end of the original.

      The volume of each one is reduced (decayed)
      by DECAY (0.5) over its predecessor.

      The change to a byte is done by echoSample()
   */
   {  int numTimes = ECHO_NUMBER + 1;
      double currDecay = 1.0;
      short sample, newSample;
      byte[] newSamples = new byte[numBytes*numTimes];

      for (int j=0; j < numTimes; j++) {
        for (int i=0; i < numBytes; i++)
          newSamples[i + (numBytes*j)] = echoSample(samples[i], currDecay);
        currDecay *= DECAY;
      }
      return newSamples;
   }  // end of echoSamples()


   private static byte echoSample(byte sampleByte, double currDecay)
   /* Since the effect is restricted to samples which are PCM
      signed or unsigned, and 8-bit,  we do not have to worry
      about big/little endian or strange byte formats.
      The byte represents the amplitude (loudness) of the sample.

      The byte is converted to a short, divided by the decay, then
      returned as a byte.

      An unsigned byte needs masking as it is converted since Java
      stores shorts in signed form, so we cut away any excessive
      bits before the short is created (which uses 16 bits).

   */
   {
     short sample, newSample;
     if (format.getEncoding() == AudioFormat.Encoding.PCM_UNSIGNED) {
       sample = (short)(sampleByte & 0xff);  // unsigned 8 bit --> short
       newSample = (short)(sample * currDecay);
       return (byte) newSample;
     }
     else if (format.getEncoding() == AudioFormat.Encoding.PCM_SIGNED) {
       sample = (short)sampleByte;  // signed 8 bit --> short
       newSample = (short)(sample * currDecay);
       return (byte) newSample;
     }
     else
       return sampleByte;    // no change
   } // end of echoSample


   private static void playsample(byte[] samples)
   /* The samples[] byte array is connected to a stream, read in chunks,
      and passed to the SourceDataLine. */
   {
     // byte array --> stream
     InputStream source = new ByteArrayInputStream(samples);

     int numRead = 0;
     byte[] buf = new byte[line.getBufferSize()];

     line.start();
     // read and play chunks of the audio
     try {
       while ((numRead = source.read(buf, 0, buf.length)) >= 0) {
         int offset = 0;
         while (offset < numRead)
           offset += line.write(buf, offset, numRead-offset);
       }
     }
     catch (IOException e)
     {  Console.debug( e.getMessage()); }

     // wait until all data is played, then close the line
     line.drain();
     line.stop();
     line.close();
     Console.debug("terminou de tocar");
   }  // end of play()


 } // end of EchoSamplesPlayer class
