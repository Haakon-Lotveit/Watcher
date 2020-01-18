package no.haakon.demo;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.haakon.annotation.WatchClass;

import java.time.LocalDateTime;

/**
 * Dette er verdens kjedeligste bønne.
 * Se for deg en bruker med en epost, som kan ha abbonert på nyhetsmail eller ikke.
 * Vi skjelver alle i spenning. ^_^ Poenget var bare å ha en enkel og forståelig bønne der et sted.
 * Dette er også slik java ser ut uten lombok. Tenk deg å skrive java, uten å bruke lombok!
 */
@Data
@NoArgsConstructor
@WatchClass
public class ExampleBean {
    private String name;
    private String email;
    private boolean subsbscriber;
    private LocalDateTime created;
    private LocalDateTime updated;
}
