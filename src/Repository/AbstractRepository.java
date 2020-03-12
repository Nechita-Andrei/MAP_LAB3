package Repository;

import domain.Entity;

import java.util.HashMap;

public abstract class AbstractRepository<ID,E extends Entity<ID>> implements Repository<ID,E>  {

    private HashMap<ID,E> elems;

    public AbstractRepository(){
        elems = new HashMap<>();
    }

    /**
     * metoda saleaza elementul
     * @param elem
     */
    public void save(E elem){
        elems.put(elem.getId(),elem);
    }

    /**
     * cauta un element dupa id
     * @param id
     * @return elemntul cautat
     */
    public E findOne(ID id){
        return elems.get(id);
    }

    /**
     * sterge un element dupa id
     * @param id
     * @return elementul sters sau null daca nu exista
     */
    public E delete(ID id){
        E elem = findOne(id);
        if(elem!=null){
            elems.remove(id);
        }
        return elem;
    }

    /**
     * modifica un element
     * @param vechi
     * @param nou
     * @return elementul modificat
     */
    public E update(E vechi,E nou){
        E v = findOne(vechi.getId());

    if(v!=null){

            v=delete(vechi.getId());
            elems.put(nou.getId(),nou);
        }
        return nou;
    }

    /**
     *
     * @return o lista cu toate elementele
     */
    public Iterable<E> findAll(){
        return elems.values();
    }

    /**
     *
     * @return numarul de elemente
     */
    public int size(){
        return elems.size();
    }

}
