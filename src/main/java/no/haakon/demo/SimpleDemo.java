package no.haakon.demo;

import no.haakon.model.ProxyEvent;
import no.haakon.watcher.Watchmaker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

public class SimpleDemo {
    public static void main(String[] args) throws Exception {
        Consumer<ProxyEvent<ExampleBean>> lytter = (event) -> {
            System.out.printf("Nå endrer bønnen %s seg!%n", event.getOriginalObject());
            event.getOriginalObject().setUpdated(LocalDateTime.now()); // <- Vi kan altså automatisk holde den oppdatert! Neat-o
        };
        // Hvis du bruker en IoC-konteiner som Guice, Spring, Dagger eller lignende, så definerer du dette der,
        // og så vil det jo skje automatisk. Siden dette er bare en liten demonstrasjon av hvordan AOP kan gjøres i Java,
        // så gjør vi ting manuelt her.
        ExampleBean unwatched = new ExampleBean();
        ExampleBean watched = new Watchmaker<>(lytter).createWatchman(new ExampleBean());

        unwatched.setName("Unwatched");
        watched.setName("Watched");
        unwatched.setEmail("unwatched@example.com");
        watched.setEmail("watched@example.com");
        unwatched.setCreated(LocalDateTime.now().minusMinutes(10L));
        watched.setCreated(LocalDateTime.now().minusMinutes(10L));
        unwatched.setSubsbscriber(false);
        watched.setSubsbscriber(true);

        unwatched.setUpdated(LocalDateTime.now());

        System.out.println("Unwatched last updated: " + unwatched.getUpdated().format(DateTimeFormatter.ISO_DATE_TIME));
        System.out.println("Watched last updated: " + watched.getUpdated().format(DateTimeFormatter.ISO_DATE_TIME));
        System.out.println();
        System.out.println("UNWATCHED:");
        System.out.println(unwatched);
        System.out.println();
        System.out.println("WATCHED:");
        System.out.println(watched);
    }
}
