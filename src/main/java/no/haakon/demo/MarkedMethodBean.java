package no.haakon.demo;

import lombok.Data;
import no.haakon.annotation.WatchMethod;

import java.time.LocalDateTime;

@Data
public class MarkedMethodBean {
    private String message;
    private String sender;

    // Strictly speaking you could use any annotation that has runtime retention. But it's much cleaner to use a specific one.
    // Since this setter is special, lombok cannot construct it.
    @WatchMethod
    public void setMessage(String message) {
        this.message = message;
    }

    private LocalDateTime updated;
    private LocalDateTime created;
}
