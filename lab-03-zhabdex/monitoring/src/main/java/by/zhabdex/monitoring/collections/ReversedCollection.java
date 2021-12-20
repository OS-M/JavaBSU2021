package by.zhabdex.monitoring.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ReversedCollection<T> implements ProcessedCollection<T, T> {

    public ReversedCollection() {}

    @Override
    public void renew(Collection<? extends T> elements) {
        var reversed_elems = new ArrayList<>(elements.stream().toList());
        Collections.reverse(reversed_elems);
        collection = reversed_elems;
    }

    @Override
    public Collection<? extends T> currentState() {
        return collection;
    }

    private Collection<? extends T> collection = List.of();
}
