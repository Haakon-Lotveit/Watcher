package no.haakon.model;

import java.util.function.Consumer;

public interface Listener<T> extends Consumer<ProxyEvent<T>> {
}
