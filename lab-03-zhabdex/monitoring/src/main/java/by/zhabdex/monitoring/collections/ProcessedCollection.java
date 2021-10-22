package by.zhabdex.monitoring.collections;

import java.util.Collection;

public interface ProcessedCollection<T, E> extends FinalProcessedCollection<T, Collection<? extends E>> {
    default <K> ProcessedCollection<T, ? extends K> compose(ProcessedCollection<? super E, K> rhs) {
        // this: A -> B
        // rhs: B -> D
        // this.compose(rhs): rhs(this(A)): A -> D
        var t = this;
        return new ProcessedCollection<T, K>() {
            @Override
            public void renew(Collection<? extends T> elements) {
                t.renew(elements);
                rhs.renew(t.currentState());
            }

            @Override
            public Collection<? extends K> currentState() {
                return rhs.currentState();
            }
        };
    }

    default <K> FinalProcessedCollection<T, ? extends K> compose(FinalProcessedCollection<? super E, K> rhs) {
        var t = this;
        return new FinalProcessedCollection<T, K>() {
            @Override
            public void renew(Collection<? extends T> elements) {
                t.renew(elements);
                rhs.renew(t.currentState());
            }

            @Override
            public K currentState() {
                return rhs.currentState();
            }
        };
    }
}
