import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Test {
    @org.junit.Test
    public void test() {
        int i = 0;
        changeInt(i);
        System.out.println(i);
    }

    @org.junit.Test
    public void test2(){
        Value value = new Value();
        changeValue(value);
        System.out.println(value.getV());
    }


    public void changeInt(int i) {
        Collection<Object> set = new HashSet<>();
    }

    public void changeValue(Value value){
        value.setV(2);
    }



    public class Value{
        int v;

        public void setV(int v) {
            this.v = v;
        }

        public int getV() {
            return v;
        }
    }
}
