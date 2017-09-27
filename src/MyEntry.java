import java.util.Map.Entry;

public class MyEntry<K, V> implements Entry<K, V> {
	private K key;
	private V value;

	public MyEntry(K key, V value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public V setValue(V value) {
		V oldValue = value;
		this.value = value;
		return oldValue;
	}

	@Override
	public int hashCode() {
		return (key.toString() + value.toString()).hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof MyEntry<?, ?>)) {
			return false;
		}
		MyEntry o = (MyEntry) other;
		return key.equals(o.key) && value.equals(o.value);
	}

}
