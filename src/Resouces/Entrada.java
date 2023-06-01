package Resouces;

public class Entrada<K,V> implements Entry<K,V>{

	private K key;
	private V value;
	
	public Entrada(K k, V v) {
		key = k;
		value = v;
	}
	
	public void setKey(K k) {
		key = k;
	}
	public K getKey() {
		return key;
	}
	
	public void setValue(V v ) {
		value = v;
	}
	
	public V getValue() {
		return value;
	}
}
