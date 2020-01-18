package no.haakon.watcher;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import no.haakon.model.ProxyEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class Watchmaker<T> {

    public static final MethodPredicate IS_SETTER = (Object proxy, Object originalt, Method method, Method forwarder, Object[] args) -> method.getName().startsWith("set");

    private final Set<Consumer<ProxyEvent<T>>> eventListeners;
    private final MethodPredicate notifyWhenMatching;

    private ProxyFactory factory = new ProxyFactory();

    // Dette er ment som et eksempel på hvorfor Joshua Bloch liker builder-patternet. Her er det bare TO argumenter som
    // kan variere, men merk at for at det skal være så ergonomisk som mulig, ender vi opp med 7(!) varianter av konstruktøren.
    // På den positive siden så kan du gjøre new Watchmaker(konsument).createWatchman(bønne) for å lage en proxy,
    // Og det er ganske pent når man skal grafse rundt med Javas refleksjon. Det ser nesten ut som om det skal være sånn... :
    // Hvis du ikke er vant med varargs har Effective Java en del nyttige ting å si om dem. Hint: Ikke bruk dem på primitive variabler.

    public Watchmaker (MethodPredicate notifyWhenMatching, Collection<Consumer<ProxyEvent<T>>> eventlisteners) {
        this.eventListeners = new HashSet<>(eventlisteners);
        this.notifyWhenMatching = notifyWhenMatching;
    }

    @SafeVarargs
    public Watchmaker(MethodPredicate notifyWhenMatching, Consumer<ProxyEvent<T>>... eventlisteners) {
        this(notifyWhenMatching, Arrays.asList(eventlisteners));
    }

    public Watchmaker(MethodPredicate notifyWhenMatching) {
        this(notifyWhenMatching, Collections.emptySet());
    }

    @SafeVarargs
    public Watchmaker(Consumer<ProxyEvent<T>>... eventlisteners) {
        this(IS_SETTER, eventlisteners);
    }

    public Watchmaker(Collection<Consumer<ProxyEvent<T>>> eventlisteners) {
        this(IS_SETTER, eventlisteners);
    }

    public Watchmaker() {
        this(IS_SETTER, Collections.emptySet());
    }

    public void subscribeToPropertyChangeEvents(Consumer<ProxyEvent<T>> callbackFunction) {
        eventListeners.add(callbackFunction);
    }

    // This is where the magic happens. Litt mindre "vil du bli med meg hjem og se på frimerkesamlingen min?", og litt mer
    // "på en skala fra 1-10, hvor mye lukter dette tørklet av kloroform?".
    @SuppressWarnings("unchecked") // Vi *VET* at dette går greit, fordi proxyen garanterer det i dokken. API-et er dog ikke like medgjørlig.
    public T createWatchman(T objectToWatch) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ProxyFactory pf = new ProxyFactory();
        pf.setSuperclass(objectToWatch.getClass());

        return (T) pf.create(new Class<?>[0], new Object[0], createInvocationHandler(objectToWatch));
    }

    /**
     * Siden proxyen ikke har en referanse til det originale objektet (noe som gir mening), så må invocationhandleren
     * ta seg av det istedenfor. Derfor tar dette kallet imot det originale objektet, slik at vi kan sende inn kallet til
     * det direkte, istedenfor å trikse rundt mer enn nødvendig.
     * Alle forebehold som vanligvis tas mot å kalle et objekt to ganger på en gang (aka. trådsikkerhet) må tas manuelt.
     * Dette er en liten kveldshack for å vise hvordan ting kan gjøres.
     */
    private MethodHandler createInvocationHandler(final T objektSomSkalProxes) {
        return new MethodHandler() {
            @Override
            @SuppressWarnings("unchecked") // Siden object er proxyen, og proxyen garanterer å extende T, og dermed alltid være instanceof T.
            public Object invoke(Object object, Method overridenMethod, Method forwarderMethod, Object[] args) throws Throwable {
                if(notifyWhenMatching.matches(object, objektSomSkalProxes, overridenMethod, forwarderMethod, args)) {
                    ProxyEvent<T> event = new ProxyEvent<>((T)object, objektSomSkalProxes, overridenMethod, forwarderMethod, args);

                    for(Consumer<ProxyEvent<T>> lytter : eventListeners) {
                        lytter.accept(event);
                    }
                }

                return overridenMethod.invoke(objektSomSkalProxes, args);
            }
        };
    }
}

