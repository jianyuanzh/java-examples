package cc.databus.hashmap;

import java.util.Objects;

public class Keys {


    private final Key[] keys;
    private final int keyCount;

    public Keys(int keyCount) {
        keys = new Key[keyCount];
        this.keyCount = keyCount;
    }

    private void init() {
        for (int i = 0; i < keyCount; i++) {
            keys[i] = new Key(i);
        }
    }

    public Key get(int i) {
        return keys[i];
    }

    public static class Key {
        private final int value;

        public Key(int value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Key key = (Key) o;
            return value == key.value;
        }

        @Override
        public int hashCode() {
            return 1;
        }
    }
}
