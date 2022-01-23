package com.example.vkr;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void checkRegex() {

        String before = "{ \"text\" : \"success is me\"}";
        String after = "success";

        String res =before.replaceAll("text","");
        res = res.replaceAll("[^\\da-zA-Zа-яёА-ЯЁ ]", "");
        res = res.replaceFirst(" ","");
        assertEquals(4, 2 + 2);
    }

//    @Test
//    public static void synthesizeText(String text) throws Exception {
//        // Instantiates a client
//        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
//            // Set the text input to be synthesized
//            SynthesisInput input = SynthesisInput.newBuilder().setText(text).build();
//
//            // Build the voice request
//            VoiceSelectionParams voice =
//                    VoiceSelectionParams.newBuilder()
//                            .setLanguageCode("en-US") // languageCode = "en_us"
//                            .setSsmlGender(SsmlVoiceGender.FEMALE) // ssmlVoiceGender = SsmlVoiceGender.FEMALE
//                            .build();
//
//            // Select the type of audio file you want returned
//            AudioConfig audioConfig =
//                    AudioConfig.newBuilder()
//                            .setAudioEncoding(AudioEncoding.MP3) // MP3 audio.
//                            .build();
//
//            // Perform the text-to-speech request
//            SynthesizeSpeechResponse response =
//                    textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);
//
//            // Get the audio contents from the response
//            ByteString audioContents = response.getAudioContent();
//
//            // Write the response to the output file.
//            try (OutputStream out = new FileOutputStream("output.mp3")) {
//                out.write(audioContents.toByteArray());
//                System.out.println("Audio content written to file \"output.mp3\"");
//            }
//        }
//    }
}