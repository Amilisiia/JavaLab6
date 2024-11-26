import java.util.*;
import java.util.List;


// Основний клас Lab6
public class Lab6 {

    // Базовий клас Gemstone
    public static abstract class Gemstone {
        private final String name;
        private final double weight; // у каратах
        private final double price;  // за карат
        private final double transparency; // від 0 до 100 (%)

        public Gemstone(String name, double weight, double price, double transparency) {
            if (weight <= 0 || price <= 0 || transparency < 0 || transparency > 100) {
                throw new IllegalArgumentException("Некоректні параметри для каменя!");
            }
            this.name = name;
            this.weight = weight;
            this.price = price;
            this.transparency = transparency;
        }

        public String getName() {
            return name;
        }

        public double getWeight() {
            return weight;
        }

        public double getPrice() {
            return price;
        }

        public double getTransparency() {
            return transparency;
        }

        public double calculateValue() {
            return weight * price;
        }

        @Override
        public String toString() {
            return String.format("Камінь: %s, Вага: %.2f карат, Ціна: %.2f, Прозорість: %.2f%%",
                    name, weight, price, transparency);
        }
    }

    // Клас-нащадок Diamond
    public static class Diamond extends Gemstone {
        public Diamond(double weight, double price, double transparency) {
            super("Diamond", weight, price, transparency);
        }
    }

    // Клас-нащадок Ruby
    public static class Ruby extends Gemstone {
        public Ruby(double weight, double price, double transparency) {
            super("Ruby", weight, price, transparency);
        }
    }

    // Клас-нащадок Amber
    public static class Amber extends Gemstone {
        public Amber(double weight, double price, double transparency) {
            super("Amber", weight, price, transparency);
        }
    }

    // Узагальнений клас TypedCollection
    public static class TypedCollection<T> implements List<T> {
        private static final int INITIAL_CAPACITY = 15;
        private static final double GROWTH_FACTOR = 1.3;
        private Object[] elements;
        private int size;

        public TypedCollection() {
            elements = new Object[INITIAL_CAPACITY];
            size = 0;
        }

        public TypedCollection(T element) {
            this();
            add(element);
        }

        public TypedCollection(Collection<? extends T> collection) {
            this();
            addAll(collection);
        }

        private void ensureCapacity(int minCapacity) {
            if (minCapacity > elements.length) {
                int newCapacity = (int) (elements.length * GROWTH_FACTOR);
                if (newCapacity < minCapacity) {
                    newCapacity = minCapacity;
                }
                elements = Arrays.copyOf(elements, newCapacity);
            }
        }

        private void checkIndex(int index) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Індекс поза межами колекції");
            }
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public boolean isEmpty() {
            return size == 0;
        }

        @Override
        public boolean contains(Object o) {
            for (int i = 0; i < size; i++) {
                if (elements[i].equals(o)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public Iterator<T> iterator() {
            return new Iterator<T>() {
                private int currentIndex = 0;

                @Override
                public boolean hasNext() {
                    return currentIndex < size;
                }

                @Override
                public T next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return (T) elements[currentIndex++];
                }
            };
        }

        @Override
        public Object[] toArray() {
            return Arrays.copyOf(elements, size);
        }

        @Override
        public <U> U[] toArray(U[] a) {
            if (a.length < size) {
                return (U[]) Arrays.copyOf(elements, size, a.getClass());
            }
            System.arraycopy(elements, 0, a, 0, size);
            if (a.length > size) {
                a[size] = null;
            }
            return a;
        }

        @Override
        public boolean add(T element) {
            ensureCapacity(size + 1);
            elements[size++] = element;
            return true;
        }

        @Override
        public boolean remove(Object o) {
            for (int i = 0; i < size; i++) {
                if (elements[i].equals(o)) {
                    System.arraycopy(elements, i + 1, elements, i, size - i - 1);
                    elements[--size] = null;
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            for (Object item : c) {
                if (!contains(item)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public boolean addAll(Collection<? extends T> c) {
            for (T item : c) {
                add(item);
            }
            return !c.isEmpty();
        }

        @Override
        public boolean addAll(int index, Collection<? extends T> c) {
            throw new UnsupportedOperationException("Операція не підтримується");
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            boolean modified = false;
            for (Object item : c) {
                modified |= remove(item);
            }
            return modified;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException("Операція не підтримується");
        }

        @Override
        public void clear() {
            Arrays.fill(elements, 0, size, null);
            size = 0;
        }

        @Override
        public T get(int index) {
            checkIndex(index);
            return (T) elements[index];
        }

        @Override
        public T set(int index, T element) {
            checkIndex(index);
            T old = (T) elements[index];
            elements[index] = element;
            return old;
        }

        @Override
        public void add(int index, T element) {
            throw new UnsupportedOperationException("Операція не підтримується");
        }

        @Override
        public T remove(int index) {
            checkIndex(index);
            T removed = (T) elements[index];
            System.arraycopy(elements, index + 1, elements, index, size - index - 1);
            elements[--size] = null;
            return removed;
        }

        @Override
        public int indexOf(Object o) {
            for (int i = 0; i < size; i++) {
                if (elements[i].equals(o)) {
                    return i;
                }
            }
            return -1;
        }

        @Override
        public int lastIndexOf(Object o) {
            for (int i = size - 1; i >= 0; i--) {
                if (elements[i].equals(o)) {
                    return i;
                }
            }
            return -1;
        }

        @Override
        public ListIterator<T> listIterator() {
            throw new UnsupportedOperationException("Операція не підтримується");
        }

        @Override
        public ListIterator<T> listIterator(int index) {
            throw new UnsupportedOperationException("Операція не підтримується");
        }

        @Override
        public List<T> subList(int fromIndex, int toIndex) {
            throw new UnsupportedOperationException("Операція не підтримується");
        }
    }

    // Основний метод для виконання завдання
    public static void main(String[] args) {
        try {
            TypedCollection<Gemstone> collection = new TypedCollection<>();
            collection.add(new Diamond(2.5, 5000, 95));
            collection.add(new Ruby(3.0, 3000, 85));
            collection.add(new Amber(5.0, 200, 70));

            System.out.println("Колекція каменів:");
            for (Gemstone gem : collection) {
                System.out.println(gem);
            }

        } catch (Exception e) {
            System.err.println("Помилка: " + e.getMessage());
        }
    }
}
