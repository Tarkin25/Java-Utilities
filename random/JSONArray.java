package random;

import java.util.ArrayList;
import java.util.List;

import static random.JSONObject.isOfSimpleType;

public class JSONArray {

    private List<Object> array = new ArrayList<>();

    public JSONArray(Object[] array) {
        for(Object o : array) {
            if(o != null && o.getClass().isArray()) {
                Object[] newArray = (Object[]) o;

                this.array.add(new JSONArray(newArray));
            } else if(isOfSimpleType(o)) {
                this.array.add(o);
            } else {
                this.array.add(new JSONObject(o));
            }
        }
    }

    public JSONArray(List<Object> array) {
        this(array.toArray());
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
