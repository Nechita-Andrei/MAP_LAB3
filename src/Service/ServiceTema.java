package Service;


import Repository.RepositoryTema;
import Utils.entity.ChangeType;
import Utils.entity.TemaChangeEvent;
import Utils.observer.Observable;
import Utils.observer.Observer;
import Validation.DataValidator;
import Validation.ValidationException;
import domain.Tema;

import java.util.ArrayList;
import java.util.List;

public class ServiceTema implements Observable<TemaChangeEvent> {
    private RepositoryTema repositoryTema;
    public ServiceTema(RepositoryTema temaRepository)
    {
        this.repositoryTema=temaRepository;
    }

    public void store(Object object)throws ValidationException{
        if(object instanceof Tema){
            Tema t=(Tema)object;
            DataValidator.getInstance().validareTema(t);
            repositoryTema.saveTema(t);
        }
    }

    public Tema getTema(Integer id){
        return repositoryTema.findOne(id);
    }

    public Iterable<Tema> getAllTeme(){
        return repositoryTema.findAll();
    }

    public Tema updateTema(Tema veche,Tema noua)throws ValidationException{
        DataValidator.getInstance().validareTema(noua);
        return repositoryTema.update(veche,noua);
    }

    public Tema deleteTema(Integer id){
        return repositoryTema.delete(id);

    }
    public Tema addTemaGui(Tema st) {

        repositoryTema.save(st);
        notifyObservers(new TemaChangeEvent(ChangeType.ADD, null));
        return st;
    }
    public Tema deleteTemaGui(Tema st){
        Tema student=repositoryTema.delete(st.getId());
        if(student!=null){
            notifyObservers(new TemaChangeEvent(ChangeType.DELETE,student));
        }
        return student;
    }
    public Tema updateTemaGui(Tema old_st,Tema new_st){
        Tema rez=repositoryTema.update(old_st,new_st);
        if(rez!=null){
            rez.setStartWeek(old_st.getStartWeek());
            repositoryTema.saveTema(rez);
            notifyObservers(new TemaChangeEvent(ChangeType.UPDATE,new_st));

        }
        return rez;
    }

    public Iterable<Tema>getAll(){
        return repositoryTema.findAll();
    }

    private List<Observer<TemaChangeEvent>> observers=new ArrayList<>();

    @Override
    public void addObserver(Observer<TemaChangeEvent>e){
        observers.add(e);

    }

    @Override
    public void removeObserver(Observer<TemaChangeEvent>e){

    }

    @Override
    public void notifyObservers(TemaChangeEvent t){
        observers.stream().forEach(x->x.update(t));
    }
}
