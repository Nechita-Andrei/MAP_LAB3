package Service;

import Repository.RepositoryStudents;
import Utils.entity.ChangeType;
import Utils.entity.StudentChangeEvent;
import Utils.observer.Observable;
import Utils.observer.Observer;
import Validation.DataValidator;
import Validation.ValidationException;
import domain.Student;

import java.util.ArrayList;
import java.util.List;


public class ServiceStudent implements Observable<StudentChangeEvent> {
    private RepositoryStudents repositoryStudents;
    public ServiceStudent(RepositoryStudents repositoryStudents){
        this.repositoryStudents=repositoryStudents;
    }
    public void store(Object object)throws ValidationException{
        if(object instanceof Student){
            Student st=(Student)object;
            DataValidator.getInstance().validareStudent(st);
           repositoryStudents.saveS(st);
        }
    }

    public Student getStudent(Integer id){
        return repositoryStudents.findOne(id);
    }
    public Iterable<Student>getAllStudents(){
        return repositoryStudents.findAll();
    }

    public Student updateStudent(Student vechi,Student nou)throws  ValidationException{
        DataValidator.getInstance().validareStudent(nou);
        return repositoryStudents.update(vechi,nou);
    }

    public Student deleteStudent(Integer id){
        return repositoryStudents.delete(id);
    }

    public Student addStudentGui(Student st) {

        repositoryStudents.saveS(st);
        notifyObservers(new StudentChangeEvent(ChangeType.ADD, null));
        return st;
    }
    public Student deleteStudentGui(Student st){
        Student student=repositoryStudents.delete(st.getId());
        if(student!=null){
            notifyObservers(new StudentChangeEvent(ChangeType.DELETE,student));
        }
        return student;
    }
    public Student updateStudentGui(Student old_st,Student new_st){
        Student m=repositoryStudents.update(old_st,new_st);
        if(m!=null){

            notifyObservers(new StudentChangeEvent(ChangeType.UPDATE,m));
        }
        return m;
    }

    public Iterable<Student>getAll(){
        return repositoryStudents.findAll();
    }

    private List<Observer<StudentChangeEvent>>observers=new ArrayList<>();

    @Override
    public void addObserver(Observer<StudentChangeEvent>e){
        observers.add(e);

    }

    @Override
    public void removeObserver(Observer<StudentChangeEvent>e){

    }

    @Override
    public void notifyObservers(StudentChangeEvent t){
        observers.stream().forEach(x->x.update(t));
    }

}

