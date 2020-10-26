package com.rahul.project.gateway.factory;


import com.vonage.client.VonageClient;
import com.vonage.client.voice.Call;
import com.vonage.client.voice.CallEvent;
import com.vonage.client.voice.VoiceName;
import com.vonage.client.voice.ncco.Ncco;
import com.vonage.client.voice.ncco.TalkAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class CreateCallFactory {

@Autowired
    Environment environment;



    private VonageClient vonageClient = null;


    private VonageClient createVonageClientService() {
        if(vonageClient == null) {


            vonageClient = VonageClient.builder()
                    .applicationId(environment.getRequiredProperty("VONAGE_APPLICATION_ID"))
                    .privateKeyPath(environment.getRequiredProperty("VONAGE_PRIVATE_KEY"))
                    .build();
        }

        return vonageClient;
    }

    public VonageClient getVonageClient() {
        return createVonageClientService();
    }

    public void createConCall(){

        Ncco ncco = new Ncco(
                TalkAction
                        .builder("This is a text-to-speech test message.")
                        .voiceName(VoiceName.JOEY)
                        .build()
        );

        String TO_NUMBER = "917838884009";
        String FROM_NUMBER = "917838884009";

        CallEvent result = getVonageClient()
                .getVoiceClient()
                .createCall(new Call(TO_NUMBER, FROM_NUMBER, ncco));

        System.out.println(result.getConversationUuid());
    }

}
