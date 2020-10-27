package com.rahul.project.gateway.factory;


import com.vonage.client.VonageClient;
import com.vonage.client.voice.Call;
import com.vonage.client.voice.CallEvent;
import com.vonage.client.voice.VoiceName;
import com.vonage.client.voice.ncco.ConnectAction;
import com.vonage.client.voice.ncco.Ncco;
import com.vonage.client.voice.ncco.PhoneEndpoint;
import com.vonage.client.voice.ncco.TalkAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class CreateCallFactory {

    @Autowired
    private Environment environment;


    private VonageClient vonageClient = null;

    public static void main(String[] args) {
        CreateCallFactory createCallFactory = new CreateCallFactory();
        createCallFactory.createConCall();
    }

    public VonageClient getVonageClient() {
        return createVonageClientService();
    }

    private VonageClient createVonageClientService() {
        if (vonageClient == null) {
            /*vonageClient = VonageClient.builder()
                    .applicationId(environment.getRequiredProperty("vonage.application.id"))
                    .privateKeyPath(environment.getRequiredProperty("vonage.application.key.path"))
                    .build();*/

            vonageClient = VonageClient.builder()
                    .applicationId("1de854a9-974a-48ac-b96c-c7ada006e1c1")
                    .privateKeyPath("C:\\Data\\work\\PetShree\\private.key")
                    .build();
        }

        return vonageClient;
    }

    public void createConCall() {
        String TO_NUMBER = "918447072124";
        String FROM_NUMBER = "917838884009";
        Ncco ncco = new Ncco(
                TalkAction
                        .builder("Welcome to Pet Shree. Please wait while we connect your call.")
                        .voiceName(VoiceName.ADITI)
                        .build(),
                ConnectAction.builder()
                        .endpoint(PhoneEndpoint.builder(TO_NUMBER).build())
                        .from(FROM_NUMBER).timeOut(20)
                        .build()
        );


        CallEvent result = getVonageClient()
                .getVoiceClient()
                .createCall(new Call("917838884009", "917838884009", ncco));

        System.out.println(result.getConversationUuid());
    }

}
