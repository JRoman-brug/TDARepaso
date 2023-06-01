package TDADiccionario;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import org.junit.Test;

import Resouces.Entry;
import exceptions.*;
import implementacionDiccionario.DictionaryOpenAddresing;

import org.junit.Before;



public class DictionaryTest {

	private Dictionary<Integer, Integer> s; // interface

	private Dictionary<Integer, Integer> getDictionary() {

		return new DictionaryOpenAddresing<Integer, Integer>();

	}

	@Before
	public void setUp() {

		s = getDictionary();

	}
	
	/* TESTEANDO EL METODO SIZE() */
	@Test
	public void size() {
                    Entry<Integer, Integer> en;		
		// Testeando si insert() actualiza el size correctamente.

				assertTrue("Tama�o de Diccionario justo despu�s de ser creada != 0",
						s.size() == 0);
				try {
					s.insert(0, 0);
					assertTrue("Tama�o de Diccionario luego de insertar una vez != 1",
							s.size() == 1);

					s.insert(0, 1);

					assertTrue("Tama�o de Diccionario luego de insertar dos veces != 2",
							s.size() == 2);

					s.insert(1, 2);

					assertTrue("Tama�o de Diccionario luego de insertar tres veces != 3",
							s.size() == 3);

					// Testeando que el m�todo remove() actualice el size, y que find(K key) no lo haga.

					s.findAll(0);
					assertTrue("Tama�o de Diccionario cambi� al invocar get()",
							s.size() == 3);

					en=s.find(0);
					assertTrue("Tama�o de Diccionario cambi� al invocar get()",
							s.size() == 3);


					s.remove(en);

					assertTrue(
							"Tama�o de Diccionario luego de eliminar una entrada es != 2",
							s.size() == 2);

					en=s.find(0);
				
					assertTrue("Tama�o de Diccionario cambi� al invocar get()",
							s.size() == 2);

					s.remove(en);

					assertTrue("Tama�o de Diccionario luego de eliminar es != 1",
							s.size() == 1);

					en=s.find(1);
					
					assertTrue("Tama�o de Diccionario cambi� al invocar get()",
							s.size() == 1);

					s.remove(en);

					assertTrue("Tama�o de Diccionario luego de eliminar es != 0",
							s.size() == 0);

				} catch (InvalidKeyException e) {
					fail("Los m�todos find(), findAll() e insert() no deber�an lanzar la excepci�n InvalidKeyException para una clave v�lida.");
				} catch (InvalidEntryException e) {
					fail("El m�todo remove() no deber�a lanzar la excepci�n InvalidEntryException para una entrada v�lida.");
				}
	}
	
	
	/* TESTEANDO EL METODO isEmpty() */

	@Test
	public void isEmpty() {
		Entry<Integer,Integer> en;
		
	  try {

			assertTrue("EL Diccionario no est� vac�o justo despu�s de ser creado",
					s.isEmpty());

			s.insert(0, 0);

			assertTrue("El Diccionario est� vac�a despu�s de insertar 1 elemento",
					!s.isEmpty());

			s.insert(0, 1);

			assertTrue("El Diccionario est� vac�a despu�s de insertar 2 elementos",
					!s.isEmpty());

			s.insert(1, 0);

			assertTrue("El Diccionario est� vac�a despu�s de insertar 3 elementos",
					!s.isEmpty());

		} catch (InvalidKeyException e) {

			fail("El m�todo insert() no deber�a lanzar InvalidkeyException con una clave v�lida.");
		}
		
		try {

			s.findAll(0);

			assertTrue(
					"El Diccionario est� vac�o despu�s de invocar findAll() teniendo 3 elementos",
					!s.isEmpty());

			en=s.find(1);

			assertTrue(
					"El Diccionario est� vac�o despu�s de invocar find() teniendo 3 elementos",
					!s.isEmpty());

			s.remove(en);

			assertTrue(
					"El Diccionario est� vac�o despu�s de eliminar 1 elemento, quedando 2.",
					!s.isEmpty());

			en=s.find(0);

			assertTrue(
					"El Diccionario est� vac�o despu�s de invocar find() teniendo 2 elementos",
					!s.isEmpty());

			s.remove(en);

			assertTrue(
					"El Diccionario est� vac�o despu�s de eliminar 1 elemento, quedando 1.",
					!s.isEmpty());

			en=s.find(0);

			assertTrue(
					"El Diccionario est� vac�o despu�s de invocar get(i3) teniendo 1 elemento",
					!s.isEmpty());

			s.remove(en);

			assertTrue(
					"EL Diccionario no est� vac�o despu�s de eliminar todos los elementos que ten�a.",
					s.isEmpty());

		} catch (InvalidKeyException e) {

			fail("El m�todo find() o findAll() no deber�a lanzar InvalidKeyException con una clave v�lida.");

		} catch (InvalidEntryException e) {

			fail("El m�todo remove() no deber�a lanzar InvalidEntryException con una entrada v�lida.");

		}


	}
	
	/* TESTEANDO EL METODO find(K key) */
	
	@Test
	public void find() {

		Integer valor, clave;
		Vector<Vector<Integer>> V= new Vector<Vector<Integer>>(2);
		Set<Integer> claves= new HashSet<Integer>();
		Random r= new Random();
		Entry<Integer,Integer> en;
		boolean esta;
		
		//Clave inv�lida
		try {
			 s.find(null);
			 fail("El m�todo find deber�a haber lanzado la excepci�n InvalidKeyException para una clave nula");
		} catch (InvalidKeyException e) {	
		}
		
		// Diccionario vac�o.
		try {

			en = s.find(1);
			assertTrue(
					"El m�todo find() deber�a devolver nulo cuando la clave no est� en el Diccionario.",
					en == null);
		} catch (InvalidKeyException e) {
			fail("El m�todo find() no deber�a lanzar InvalidkeyException con una clave v�lida.");
		}
			
		
		// Insertando 1000 elementos
        V.add(new Vector<Integer>(1000));
        V.add(new Vector<Integer>(1000));
		try {
			for (int i=0; i<1000;i++)
				{clave=r.nextInt(10*(i+1));
				 claves.add(clave);
				 valor=r.nextInt(1000);
				 V.get(0).add(clave);
				 V.get(1).add(valor);
				 s.insert(clave, valor);
				}
            
		   } catch (InvalidKeyException e) {

			fail("El m�todo insert() no deber�a lanzar InvalidkeyException con una clave v�lida.");

		   }

		//Testeando find con 1000 elementos	

		try {
			for (Integer i:claves)
			{ en=s.find(i);
			  while (en!=null)
			  {   esta=buscoEnVectorYEliminar(en.getKey(), en.getValue(),V);
				  assertTrue("El m�todo find() no funciona correctamente", esta);
				  s.remove(en);
				  en=s.find(i);
			  }
			  }
			assertTrue("El m�todo find() no funciona correctamente",((V.get(0).size()==0)&&(V.get(1).size()==0)));
			
		} catch (InvalidKeyException e) {
			fail("El m�todo insert() no deber�a lanzar InvalidkeyException con una clave v�lida.");
		} catch (InvalidEntryException e) {
			fail("El m�todo remove() no deber�a lanzar InvalidEntryException con una entrada v�lida.");
		}

		
	}
	
	private boolean buscoEnVectorYEliminar(int c, int v, Vector<Vector<Integer>> V)
	{ int index=0; boolean encontro=true; boolean es=false;
	  int pos=-1;
	 
	  while ((encontro)&& !es && (index<V.get(0).capacity()))
	  	{  pos=V.get(0).indexOf(c, index);
	  	   if (pos==-1) encontro=false;
	       	else
	       		 {	es= (V.get(1).get(pos)==v);
	       		  	index=pos+1;
	       		 }
	      	      
	  	}  		
	   if (es)
	         {	V.get(0).remove(pos);
		   		V.get(1).remove(pos);}
	   return es;
	}


	/* TESTEANDO EL METODO findAll(K key) */
	
	@Test
	public void findAll() {

		Integer valor, clave;
		Vector<Vector<Integer>> V= new Vector<Vector<Integer>>(2);
		Set<Integer> claves= new HashSet<Integer>();
		Random r= new Random();
		Iterator<Entry<Integer,Integer>> it;
		Entry<Integer,Integer> en;
		boolean esta;
		
		//Clave inv�lida
		try {
			 s.findAll(null);
			 fail("El m�todo findAll() deber�a haber lanzado la excepci�n InvalidKeyException para una clave nula");
		} catch (InvalidKeyException e) {	
		}
		
		// Diccionario vac�o.
		try {

			it = s.findAll(1).iterator();
			assertTrue(
					"El m�todo findAll() deber�a devolver un iterable sin elementos cuando la clave no est� en el Diccionario.",
					!it.hasNext());
		} catch (InvalidKeyException e) {
			fail("El m�todo findAll() no deber�a lanzar InvalidkeyException con una clave v�lida.");
		}
			
			
		// Insertando 1000 elementos
        V.add(new Vector<Integer>(1000));
        V.add(new Vector<Integer>(1000));
		try {
			for (int i=0; i<1000;i++)
				{clave=r.nextInt(10*(i+1));
				 claves.add(clave);
				 valor=r.nextInt(1000);
				 V.get(0).add(clave);
				 V.get(1).add(valor);
				 s.insert(clave, valor);
				}
            
		   } catch (InvalidKeyException e) {

			fail("El m�todo insert() no deber�a lanzar InvalidkeyException con una clave v�lida.");

		   }

		//Testeando find con 1000 elementos	

		try {
			for (Integer i:claves)
			{ it=s.findAll(i).iterator();
			  while (it.hasNext())
			  {   en=it.next();
			      esta=buscoEnVectorYEliminar(en.getKey(), en.getValue(),V);
				  assertTrue("El m�todo findAll() no funciona correctamente", esta);
		      }
			}
			assertTrue("El m�todo findAll() no funciona correctamente",((V.get(0).size()==0)&&(V.get(1).size()==0)));
		} catch (InvalidKeyException e) {
			fail("El m�todo insert() no deber�a lanzar InvalidkeyException con una clave v�lida.");
		}

		
		
	}
	
	/* TESTEANDO LOS M�TODOS INSERT/REMOVE*/
	
	@Test
	public void Insert_Remove() {

		Integer valor, clave;
		Vector<Vector<Integer>> V= new Vector<Vector<Integer>>(2);
		Set<Integer> claves= new HashSet<Integer>();
		Random r= new Random();
		Entry<Integer,Integer> en=null;
		boolean esta;
		
		//Clave inv�lida
		try {
			 s.insert(null,3);
			 fail("El m�todo insert deber�a haber lanzado la excepci�n InvalidKeyException para una clave inv�lida");
	    } catch (InvalidKeyException e) {	
		}
		
		//Entrada inv�lida
		try {
			 s.remove(null);
			 fail("El m�todo remove deber�a haber lanzado la excepci�n InvalidEntryException para una entry inv�lida");
		} catch (InvalidEntryException e) {	
		}
		
		// Insertando 1 elemento.
		try {
			s.insert(0, 1);
			en=s.find(0);
			s.remove(en);

		} catch (InvalidKeyException e) {

			fail("El m�todo insert() o find() no deber�a lanzar InvalidkeyException con una clave v�lida.");

		} catch (InvalidEntryException e) {
			fail("El m�todo remove() no deber�a lanzar InvalidEntryException con una entrada v�lida.");
		}
		
		//Intentando eliminar una entrada que no est� en el diccionario
		try {
			  s.remove(en);	
			  fail("El m�todo remove() deber�a lanzar InvalidEntryException con una entrada que no se encuentra en el diccionario.");

		} catch (InvalidEntryException e) {
			
		}
		
		// Insertando 1000 elementos
        V.add(new Vector<Integer>(1000));
        V.add(new Vector<Integer>(1000));
		try {
			for (int i=0; i<1000;i++)
				{clave=r.nextInt(10*(i+1));
				 claves.add(clave);
				 valor=r.nextInt(1000);
				 V.get(0).add(clave);
				 V.get(1).add(valor);
				 s.insert(clave, valor);
				}
            
		   } catch (InvalidKeyException e) {

			fail("El m�todo insert() no deber�a lanzar InvalidkeyException con una clave v�lida.");

		   }

		//Testeando find con 1000 elementos	

		try {
			for (Integer i:claves)
			{ en=s.find(i);
			  while (en!=null)
			  {   esta=buscoEnVectorYEliminar(en.getKey(), en.getValue(),V);
				  assertTrue("El m�todo insert() no funciona correctamente", esta);
				  s.remove(en);
				  en=s.find(i);
			  }
			  }
			assertTrue("El m�todo insert() no funciona correctamente",((V.get(0).size()==0)&&(V.get(1).size()==0)));
			
		} catch (InvalidKeyException e) {
			fail("El m�todo insert() no deber�a lanzar InvalidkeyException con una clave v�lida.");
		} catch (InvalidEntryException e) {
			fail("El m�todo remove() no deber�a lanzar InvalidEntryException con una entrada v�lida.");
		}
		
     assertTrue("El m�todo remove() no funciona correctamente", s.isEmpty());
     
	}
	

	/* TESTEANDO EL M�TODO ENTRIES*/
	
	@Test
	public void entries() {

		Integer valor, clave;
		Vector<Vector<Integer>> V= new Vector<Vector<Integer>>(2);
		Set<Integer> claves= new HashSet<Integer>();
		Random r= new Random();
		boolean esta;
		
	    //Diccionario vac�o
		assertTrue("Entris no funciona correctamente para un diccionario vac�o", ((s.entries()!=null)&&(!s.entries().iterator().hasNext())));
			
			
		// Insertando 1000 elementos
        V.add(new Vector<Integer>(1000));
        V.add(new Vector<Integer>(1000));
		try {
			for (int i=0; i<1000;i++)
				{clave=r.nextInt(10*(i+1));
				 claves.add(clave);
				 valor=r.nextInt(1000);
				 V.get(0).add(clave);
				 V.get(1).add(valor);
				 s.insert(clave, valor);
				}
            
		   } catch (InvalidKeyException e) {

			fail("El m�todo insert() no deber�a lanzar InvalidkeyException con una clave v�lida.");

		   }

		//Testeando entries con 1000 elementos	

		for (Entry<Integer, Integer> entrada: s.entries())
				{    esta=buscoEnVectorYEliminar(entrada.getKey(), entrada.getValue(),V);
					 assertTrue("El m�todo entries() no funciona correctamente", esta);
		 	     }
			assertTrue("El m�todo entries() no funciona correctamente",((V.get(0).size()==0)&&(V.get(1).size()==0)));
			
	    		
	}
	
	
}