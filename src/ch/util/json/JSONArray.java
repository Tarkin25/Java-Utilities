package ch.util.json;

import java.util.*;

import static ch.util.json.JSONObject.isOfSimpleType;

/**
 * @author Severin Weigold
 */

public class JSONArray implements List<Object> {

    private List<Object> array = new ArrayList<>();

    private Class originalType;

    JSONArray(Object object) {
        if(object != null && object.getClass().isArray()) {
            originalType = object.getClass();

            if(object instanceof byte[]) {
                byte[] bytes = (byte[])object;

                for(Byte b : bytes) {
                    add(b);
                }
            } else if(object instanceof short[]) {
                short[] shorts = (short[])object;

                for(Short s : shorts) {
                    add(s);
                }
            } else if(object instanceof int[]) {
                int[] ints = (int[])object;

                for(Integer i : ints) {
                    add(i);
                }
            } else if(object instanceof long[]) {
                long[] longs = (long[])object;

                for(Long l : longs) {
                    add(l);
                }
            } else if(object instanceof float[]) {
                float[] floats = (float[])object;

                for(Float f : floats) {
                    add(f);
                }
            } else if(object instanceof double[]) {
                double[] doubles = (double[])object;

                for(Double d : doubles) {
                    add(d);
                }
            } else if(object instanceof boolean[]) {
                boolean[] booleans = (boolean[])object;

                for(Boolean b : booleans) {
                    add(b);
                }
            } else if(object instanceof char[]) {
                char[] chars = (char[])object;

                for(Character c : chars) {
                    add(c);
                }
            } else {
                Object[] objects = (Object[])object;

                for(Object o : objects) {
                    add(o);
                }
            }

        } else {
            throw new IllegalArgumentException("Argument is not an array");
        }
    }

    Class getOriginalType() {
        return originalType;
    }

    @Override
    public int size() {
        return array.size();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return array.contains(o);
    }

    @Override
    public Iterator<Object> iterator() {
        return array.iterator();
    }

    @Override
    public Object[] toArray() {
        return array.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return array.toArray(a);
    }

    @Override
    public boolean add(Object o) {
        if(o.getClass().isArray()) {
            return array.add(new JSONArray(o));
        } else if(isOfSimpleType(o)) {
            return array.add(o);
        } else {
            return array.add(new JSONObject(o));
        }
    }

    @Override
    public boolean remove(Object o) {
        return array.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return array.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<?> c) {
        boolean success = true;

        for(Object o : c) {
            if(!add(o)) {
                success = false;
            }
        }

        return success;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return array.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return array.retainAll(c);
    }

    @Override
    public void clear() {
        array.clear();
    }

    @Override
    public boolean addAll(int index, Collection<?> c) {
        for(Object o : c) {
            add(index, o);
            index++;
        }

        return true;
    }

    @Override
    public Object get(int index) {
        return array.get(index);
    }

    @Override
    public Object set(int index, Object o) {
        if(o.getClass().isArray()) {
            Object[] newArray = (Object[]) o;

            return array.set(index, new JSONArray(newArray));
        } else if(isOfSimpleType(o)) {
            return array.set(index, o);
        } else {
            return array.set(index, new JSONObject(o));
        }
    }

    @Override
    public void add(int index, Object o) {
        if(o.getClass().isArray()) {
            Object[] newArray = (Object[]) o;

            array.add(index, new JSONArray(newArray));
        } else if(isOfSimpleType(o)) {
            array.add(index, o);
        } else {
            array.add(index, new JSONObject(o));
        }
    }

    @Override
    public Object remove(int index) {
        return array.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return array.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return array.lastIndexOf(o);
    }

    @Override
    public ListIterator<Object> listIterator() {
        return array.listIterator();
    }

    @Override
    public ListIterator<Object> listIterator(int index) {
        return array.listIterator(index);
    }

    @Override
    public List<Object> subList(int fromIndex, int toIndex) {
        return array.subList(fromIndex, toIndex);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for(Object o : array) {
            sb.append(o).append(",");
        }

        sb.setLength(sb.length()-1);

        sb.append("]");
        return sb.toString();
    }

}
