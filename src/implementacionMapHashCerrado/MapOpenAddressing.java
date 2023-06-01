package implementacionMapHashCerrado;

import Resouces.*;
import TDALista.PositionList;
import TDAMapeo.Map;
import exceptions.*;
import implementacionListaDoblementeEnlazada.ListaDE;

public class MapOpenAddressing<K,V> implements Map<K,V>{
	protected int size;
	protected int capacidad;
	protected Entry<K,V> disponible;
	protected Entry<K,V>[] buckets;

	public MapOpenAddressing() {
		size = 0;
		capacidad = 13;
		disponible = new Entrada<K,V>(null,null);
		buckets = new Entry[capacidad];
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	public V get(K key) throws InvalidKeyException{
		checkKey(key);
		V toReturn = null;
		int balde = hashThisKey(key);
		boolean encontre = false;
		boolean encontreNull = false;
		int i = 0;
		while(i<capacidad && !encontre && !encontreNull) {

			if(buckets[balde]==null || buckets[balde] == disponible) {
				encontreNull = true;
			}else if(buckets[balde].getKey().equals(key)) {
				encontre = true;
				toReturn = buckets[balde].getValue();
			}


			balde = (balde+1)%capacidad;
			i++;
		}

		return toReturn;
	}

	public V put(K key, V value) throws InvalidKeyException{
		checkKey(key);
		V toReturn = null;
		int balde = hashThisKey(key);
		int i = 0;
		boolean encontre = false;

		while(i<capacidad && !encontre) {

			if(buckets[balde]==null || buckets[balde] == disponible) {
				buckets[balde] = new Entrada<K,V>(key,value);
				encontre = true;
				size++;
			}else if(buckets[balde].getKey().equals(key)){
				toReturn = buckets[balde].getValue();
				((Entrada<K,V>)buckets[balde]).setValue(value);
				encontre = true;
			}
			balde = (balde+1)%capacidad;
			i++;
		}
		if(size/capacidad >0.5) {
			reHash();
		}

		return toReturn;
	}

	public V remove(K key) throws InvalidKeyException{
		checkKey(key);
		V toReturn = null;
		int balde = hashThisKey(key);
		boolean encontre = false;
		boolean encontreNull = false;
		int i = 0;
		while(i<capacidad && !encontre && !encontreNull) {
			if(buckets[balde] == null ) {
				encontreNull = true;
			}else if(buckets[balde] != disponible && buckets[balde].getKey().equals(key)) {
				encontre = true;
				toReturn = buckets[balde].getValue();
				buckets[balde] = disponible;
				size--;
			}
			balde = (balde+1)%capacidad;
			i++;
		}

		return toReturn;
	}

	public Iterable<Entry<K,V>> entries(){
		PositionList<Entry<K,V>> entries = new ListaDE<Entry<K,V>>();

		for (int i = 0; i < buckets.length; i++) {
			if(buckets[i] != null && buckets[i] != disponible) {
				entries.addLast(buckets[i]);
			}
		}

		return entries;
	}
	public Iterable<K> keys(){
		PositionList<K> keys = new ListaDE<K>();

		for (int i = 0; i < buckets.length; i++) {
			if(buckets[i] != null && buckets[i] != disponible) {
				keys.addLast(buckets[i].getKey());
			}
		}
		return keys;
	}

	public Iterable<V> values(){
		PositionList<V> values = new ListaDE<V>();

		for (int i = 0; i < buckets.length; i++) {
			if(buckets[i] != null && buckets[i] != disponible) {
				values.addLast(buckets[i].getValue());
			}
		}
		return values;
	}

	protected void reHash() {
		Iterable<Entry<K,V>> entries = entries();
		capacidad = nextPrime(capacidad*2);
		buckets = new Entry[capacidad];
		size=0;
		for(Entry<K,V> elem:entries) {
			try {
				put(elem.getKey(),elem.getValue());
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	protected int hashThisKey(K key) {
		return Math.abs(key.hashCode())%capacidad;
	}
	protected void checkKey(K key) throws InvalidKeyException{
		if(key == null)throw new InvalidKeyException("Key invalida");
	}
	protected int nextPrime(int n) {
		int primo = n+1;
		int toReturn = 0;
		boolean encontrePrimo=true,encontre = false;

		while(!encontre) {

			for (int i = 2; i <= primo-1 && encontrePrimo; i++) {
				if(primo % i == 0) {
					encontrePrimo = false;
				}

			}
			if(encontrePrimo == true) {
				encontre = true;
				toReturn = primo;
			}
			encontrePrimo = true;
			primo++;
		}

		return toReturn;
	}
}

