package it.unibo.inner.test.impl;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.List;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T>{
    private final List<T> elements;
    private Predicate<T> filter;

    public IterableWithPolicyImpl(final T[] elements, final Predicate<T> filter) {
        this.elements = List.of(elements);
        this.filter = filter;
    }

    public IterableWithPolicyImpl(final T[] elements) {
        this(elements, new Predicate<T>(){
            @Override 
            public boolean test(final Object item) {
                return true;
            }
        });
    }


    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        this.filter = filter;
    }

    private class InnerPolicyIterator implements Iterator<T> {
        private int indexNow = 0;
        private final int size;

        InnerPolicyIterator() {
            this.size=elements.size();
        }

        @Override
        public boolean hasNext() {
            while (indexNow < size) {
                if (filter.test(elements.get(indexNow))) {
                    return true;
                }
                indexNow++;
            }
            return false;
        }

        @Override
        public T next() {
            if (this.hasNext()) {
                return elements.get(indexNow++);
            }
            throw new NoSuchElementException("Array size: " + size + "At index: " + indexNow);
        }
        
    }
    
    @Override
    public Iterator<T> iterator() {
        return new InnerPolicyIterator();
    }

    @Override 
    public String toString() {
        return elements.toString();
    }
}
