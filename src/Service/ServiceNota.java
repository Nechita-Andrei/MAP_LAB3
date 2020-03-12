package Service;

import Repository.RepositoryNota;
import Utils.entity.ChangeType;
import Utils.entity.NotaChangeEvent;
import Utils.observer.Observable;
import Utils.observer.Observer;
import Validation.DataValidator;
import Validation.ValidationException;
import domain.Nota;

import java.util.ArrayList;
import java.util.List;

public class ServiceNota implements Observable<NotaChangeEvent> {
    private RepositoryNota repositoryNota;
    public ServiceNota(RepositoryNota repositoryNota){
        this.repositoryNota=repositoryNota;
    }

    public Nota store(Object object)throws ValidationException{
        if(object instanceof Nota){
            Nota n=(Nota)object;
            DataValidator.getInstance().validareNota(n);
            for(Nota nt:getAllGrades())
            {
                if(nt.getStudent().equals(n.getStudent())&&nt.getTema().equals(n.getTema()))
                    throw new ValidationException("Studentul a fost deja notat");
            }
            repositoryNota.save(n);
            notifyObservers(new NotaChangeEvent(ChangeType.ADD,null));
            return n;
        }
        return null;

    }

    public Nota getNota(Integer id){
        return repositoryNota.findOne(id);

    }

    public Iterable<Nota>getAllGrades(){
        return repositoryNota.findAll();
    }

    public Nota updateNota(Nota veche,Nota noua)throws ValidationException{
        DataValidator.getInstance().validareNota(noua);
        return repositoryNota.update(veche, noua);
    }

    public  Nota deleteNota(Integer id){
        return repositoryNota.delete(id);
    }

    public Iterable<Nota>getAll(){
        return repositoryNota.findAll();
    }

    private List<Observer<NotaChangeEvent>> observers=new ArrayList<>();

    public int sizeLista(){
        return repositoryNota.size();
    }

    @Override
    public void addObserver(Observer<NotaChangeEvent>e){
        observers.add(e);

    }

    @Override
    public void removeObserver(Observer<NotaChangeEvent>e){

    }

    @Override
    public void notifyObservers(NotaChangeEvent t){
        observers.stream().forEach(x->x.update(t));
    }
}
