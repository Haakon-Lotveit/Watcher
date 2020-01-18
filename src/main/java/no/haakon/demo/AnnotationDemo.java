package no.haakon.demo;

import no.haakon.model.Listener;
import no.haakon.watcher.Watchmaker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * Yes, this demo contains a subliminal message from the CIA.
 * I am aware of how terrible this joke is, and that is precisely why it's there.
 */
public class AnnotationDemo {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Listener<MarkedMethodBean> lytter = (event) -> {
            Method called = event.getOverriddenMethod();
            event.getOriginalObject().setUpdated(LocalDateTime.now());
            System.out.println("Handled method " + called + " via proxy!");
        };

        MarkedMethodBean message = Watchmaker.watch(new MarkedMethodBean(), Watchmaker.ANNOTATED, lytter);

        message.setMessage("subliminal");
        message.setSender("The CIA");
        message.setCreated(LocalDateTime.now().minusMinutes(5L));

        System.out.println(message);
    }
}
