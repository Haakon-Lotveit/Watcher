package no.haakon.watcher;

import java.lang.reflect.Method;

/**
 * Gitt informasjonen du har tilgjengelig når en invocationhandler blir kalt, burde noen varsles?
 * Det vil si: Gitt at denne metoden blir kalt, skal lytterene varsles eller ikke?
 * Standard sjekk er å se om det er en setter-metode. En kan også sjekke om den er annotert, eller andre ting.
 */
public interface MethodPredicate {
    boolean matches(Object proxy, Object originalt, Method method, Method forwarder, Object[] arguments);
}
