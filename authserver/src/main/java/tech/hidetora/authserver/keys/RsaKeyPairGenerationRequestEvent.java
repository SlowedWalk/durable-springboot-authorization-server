package tech.hidetora.authserver.keys;

import org.springframework.context.ApplicationEvent;

import java.time.Instant;

/**
 * @author hidetora
 * @version 1.0.0
 * @since 2022/04/18
 */
public class RsaKeyPairGenerationRequestEvent extends ApplicationEvent {
    public RsaKeyPairGenerationRequestEvent(Instant instant) {
        super(instant);
    }

    @Override
    public Instant getSource() {
        return (Instant) super.getSource();
    }
}
