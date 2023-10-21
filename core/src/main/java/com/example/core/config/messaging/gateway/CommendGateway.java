package com.example.core.config.messaging.gateway;

import com.example.core.domain.messaging.command.Command;
import org.springframework.integration.annotation.MessagingGateway;

import static com.example.core.domain.messaging.MassagingVO.COMMAND_GATEWAY_CHANNEL;

/**
 * todo
 * return type에 대항 응답 차이
 *
 * void면 api응답을 바로한다.
 * 그런데 제네릭을 사용하면 api응답이 계속 지연된다...??
 */
@MessagingGateway(defaultRequestChannel = COMMAND_GATEWAY_CHANNEL)
public interface CommendGateway {
    void request(Command command);
}
