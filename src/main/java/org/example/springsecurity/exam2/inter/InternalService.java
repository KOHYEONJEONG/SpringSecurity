package org.example.springsecurity.exam2.inter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InternalService {

    public void internal() {
        log.info("CallServiceV0 internal");
    }
}
