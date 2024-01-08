package jms.simple.app;

import jakarta.jms.JMSException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class MqCommunicationController {

    private final MQCommunication mqCommunication;

    @GetMapping("save")
    public void saveStringInMq(@RequestBody String stringToSaveInMq) {
        mqCommunication.sendMessageToMQ(stringToSaveInMq);
    }

    @GetMapping("read")
    public String readStringFromMQ() throws JMSException {
        return mqCommunication.readMessageFromMq();
    }
}