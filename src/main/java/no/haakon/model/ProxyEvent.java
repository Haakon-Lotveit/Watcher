package no.haakon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.lang.reflect.Method;

@Data
@Builder
@AllArgsConstructor
/**
 * Merk at denne klassen lar deg tafse på, endre på, og ødelegge for alt som måtte finne på å skje.
 * Men du kan også gjøre ting som å endre på argumentene. (Se for deg at du vil sladde ting som skal i loggen for eksempel)
 */
public class ProxyEvent<T> {
    private T proxy;
    private T originalObject;
    private Method overriddenMethod;
    private Method forwardedMethod;
    private Object[] arguments;

}
