package no.haakon.demo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * This is probably the most boring bean I could think of.
 * It's realistic in the sense that it knows of creation and update time.
 * But it's just users who may or may not be on a mailing list.
 */
@Data
@NoArgsConstructor
public class ExampleBean {
    private String name;
    private String email;
    private boolean subsbscriber;
    private LocalDateTime created;
    private LocalDateTime updated;
}
