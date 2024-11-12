package org.example;

import com.assemblyai.api.AssemblyAI;
import com.assemblyai.api.resources.transcripts.requests.TranscriptParams;
import javazoom.jl.player.Player;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Audio {

    public static void createfile(String url)  {
        try {
            PrintWriter pw = new PrintWriter(url);
            pw.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void writefile(String url, Object contenido) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(url,true));
            pw.println(contenido);
            pw.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {

            createfile("sound.txt");

            AssemblyAI client = AssemblyAI.builder()
                    .apiKey("eea5b57955be4aefaedf80c454371a5a")
                    .build();

            var fielUrl = "sonido.mp3";

            FileInputStream input = new FileInputStream(fielUrl);

            var uploadedFile = client.files().upload(new File("vocals.mp3"));

            fielUrl = uploadedFile.getUploadUrl();

            var params = TranscriptParams.builder()
                    .audioUrl(fielUrl)
                    .build();

            var transcript = client.transcripts().transcribe(params);

            String cadena = transcript.getText().toString();

            Matcher matcher = Pattern.compile("([^\\\\.]+)").matcher(cadena);
            while (matcher.find()){
                writefile("sound.txt", matcher.group(1));
            }


            BufferedInputStream bufferedInputStream = new BufferedInputStream(input);

            Player player = new Player(bufferedInputStream);

            player.play();
              

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
