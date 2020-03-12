package Repository;

import domain.Entity;

public interface Repository<ID, E extends Entity<ID>> {
    public void save(E elem);
    public E update(E old, E nou);
    public E delete(ID key);
    public E findOne(ID key);
    public Iterable<E>findAll();
    public int size();

}
