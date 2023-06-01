package implementacionMapHashAbierto;

import java.util.Iterator;

import Resouces.Entrada;
import Resouces.Entry;
import TDALista.*;
import TDAMapeo.Map;
import exceptions.InvalidKeyException;
import implementacionListaDoblementeEnlazada.ListaDE;

public class MapSeparateChaining<K,V> implements Map<K,V> {

	private int size;
	private PositionList<Entry<K,V>>[] buckets;
	private int capacidad;

	public MapSeparateChaining() {
		capacidad = 13;
		size = 0;
		buckets = new PositionList[capacidad];

		for(int i=0;i<buckets.length;i++) {
			buckets[i] = new ListaDE<Entry<K,V>>();
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
	public V get(K key) throws InvalidKeyException {
		checkKey(key);
		int hash = hashThisKey(key);

		V toReturn = null;
		
		Iterator<Entry<K,V>> it = buckets[hash].iterator();
		Entry<K,V> aux = null;
		boolean encontre = false;
		while(it.hasNext() && !encontre) {
			aux = it.next();
			if(aux.getKey() == key) {
				encontre = true;
				toReturn = aux.getValue();				
			}
		}

		return toReturn;
	}

	public V put(K key, V value) throws InvalidKeyException {
		checkKey(key);

		V toReturn = null;
		int hash = hashThisKey(key);
		
		PositionList<Entry<K,V>> balde = buckets[hash];
		Iterator<Entry<K,V>> it = balde.iterator();

		Entry<K,V> aux = null;
		boolean encontre = false;
		while(it.hasNext() && !encontre) {
			aux = it.next();
			if(aux.getKey().equals(key)) {
				encontre = true;
				toReturn = aux.getValue();
				((Entrada<K,V>) aux).setValue(value);
			}
		}

		if(!encontre) {
			balde.addLast(new Entrada<K,V>(key,value));
			size++;
		}
		
		if(size > 1.5*capacidad) {
			reHash();
		}
		
		return toReturn;
	}

	@Override
	public V remove(K key) throws InvalidKeyException {
		checkKey(key);
		V toReturn = null;
		int hash = hashThisKey(key);
		
		PositionList<Entry<K,V>> balde = buckets[hash];
		Iterator<Position<Entry<K,V>>> it = balde.positions().iterator();
		
		Position<Entry<K,V>> aux = null;
		boolean encontre = false;
		while(it.hasNext() && !encontre) {
			aux = it.next();
			if(aux.element().getKey().equals(key)) {
				try {
					encontre = true;
					toReturn = aux.element().getValue();
					balde.remove(aux);				
					size--;					
				}catch( InvalidPositionException e) {
					e.printStackTrace();
				}
			}
		}
		
	
			
		
		return toReturn;
	}

	@Override
	public Iterable<K> keys() {
		PositionList<K> keys = new ListaDE<K>();
		for(int i=0;i<buckets.length;i++) {
			for(Entry<K,V> elem:buckets[i]) {
				keys.addLast(elem.getKey());
			}
		}
		return keys;
	}

	@Override
	public Iterable<V> values() {
		PositionList<V> value = new ListaDE<V>();
		for(int i=0;i<buckets.length;i++) {
			for(Entry<K,V> elem:buckets[i]) {
				value.addLast(elem.getValue());
			}
		}
		return value;
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> entries = new ListaDE<Entry<K,V>>();
		for(int i=0;i<buckets.length;i++) {
			for(Entry<K,V> elem:buckets[i]) {
				entries.addLast(elem);
			}
		}
		return entries;
	}

	private int hashThisKey(K key) {
		return Math.abs(key.hashCode())%capacidad;
	}

	private void checkKey(K key)throws InvalidKeyException{
		if(key == null) throw new InvalidKeyException("Key invalida");
	}
	private void reHash() {
		Iterable<Entry<K,V>> entries = entries();
		size = 0;
		capacidad = nextPrime(capacidad);
		buckets = new PositionList[capacidad];
		for(int i=0;i<buckets.length;i++) {
			buckets[i] = new ListaDE<Entry<K,V>>();
		}
		for(Entry<K,V> elem:entries) {
			try {
				put(elem.getKey(),elem.getValue());
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			}
		}
	}
	private int nextPrime(int n) {
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
