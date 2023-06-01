package implementacionDiccionario;

import java.util.Iterator;

import Resouces.*;
import TDADiccionario.Dictionary;
import TDALista.PositionList;
import exceptions.InvalidEntryException;
import exceptions.InvalidKeyException;
import implementacionListaDoblementeEnlazada.ListaDE;

public class DictionaryOpenAddresing<K,V> implements Dictionary<K, V> {
	protected Entry<K,V>[] buckets;
	protected Entry<K,V> disponible;
	protected int size;
	protected int capacidad;

	public DictionaryOpenAddresing() {
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

	@Override
	public Entry<K,V> find(K key)throws InvalidKeyException {
		checkKey(key);
		Entry<K,V> toReturn = null;
		int balde = hashThisKey(key);
		boolean encontre = false;
		boolean encontreNull = false;
		int i = 0;
		while(i<capacidad && !encontre && !encontreNull) {
			if(buckets[balde] == null) {
				encontreNull = true;
			}
			else if(buckets[balde] != disponible && buckets[balde].getKey().equals(key)) {

				toReturn = buckets[balde];
				encontre = true;

			}else {
				balde = (balde+1) % capacidad;
				i++;
			}
		}

		return toReturn;
	}
	@Override
	public Iterable<Entry<K,V>> findAll(K key)throws InvalidKeyException{
		checkKey(key);
		PositionList<Entry<K,V>> toReturn = new ListaDE<Entry<K,V>>();
		int balde = hashThisKey(key);
		for (int i = 0; i < buckets.length; i++) {
			if(buckets[i] != null && buckets[i] != disponible && buckets[i].getKey().equals(key)) {
				toReturn.addLast(buckets[i]);
			}
			balde = (balde+1)%capacidad;
		}

		return toReturn;
	}

	@Override
	public Entry<K,V> insert(K key, V value) throws InvalidKeyException{
		checkKey(key);
		Entry<K,V> toReturn = null;
		int balde = hashThisKey(key);
		boolean encontre = false;

		for (int i = 0; i < buckets.length && !encontre; i++) {
			if(buckets[balde] == null || buckets[balde] == disponible) {
				encontre = true;
				toReturn = new Entrada<K,V>(key,value);
				buckets[balde] = toReturn;
				size++;
			}else {
				balde = (balde+1)%capacidad;
			}
		}
		if(size/capacidad > 0.5) {
			reHash();
		}
		return toReturn;
	}

	@Override
	public Entry<K,V> remove(Entry<K,V> e)throws InvalidEntryException{
		if(e == null || e == disponible) throw new InvalidEntryException("Entrada invalid");
		Entry<K,V> toReturn = null;
		int balde = hashThisKey(e.getKey());
		boolean encontre = false;
		boolean encontreNull = false;
		
		for (int i = 0; i < buckets.length && !encontre && !encontreNull; i++) {
			if(buckets[i]==null) encontreNull = true;
			else if(buckets[i]!=disponible &&buckets[balde].getKey() == e.getKey() ) {
				toReturn = buckets[balde];
				buckets[balde] = disponible;
				encontre = true;
				size--;
			}
			balde = (balde+1)%capacidad;

		}

		if(!encontre) throw new InvalidEntryException("No esta la entrada en el dicionario");
		return toReturn;
	}

	@Override
	public Iterable<Entry<K,V>> entries(){
		PositionList<Entry<K,V>> entries = new ListaDE<Entry<K,V>>();

		for (int i = 0; i < buckets.length; i++) {
			if(buckets[i] != null && buckets[i] != disponible) entries.addLast(buckets[i]);
		}

		return entries;
	}




	protected void reHash() throws InvalidKeyException{
		Iterable<Entry<K,V>> entries = entries();
		capacidad = nextPrime(capacidad*2);
		buckets = new Entry[capacidad];

		for(Entry<K,V> elem:entries) {
			insert(elem.getKey(),elem.getValue());
		}
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
	protected int hashThisKey(K key) {
		return Math.abs(key.hashCode())%capacidad;
	}
	protected void checkKey(K key) throws InvalidKeyException{	
		if(key == null) throw new InvalidKeyException("Key invalida");
	}
}
