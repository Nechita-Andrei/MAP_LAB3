package Utils.entity;

import domain.Student;

public class StudentChangeEvent implements Event {
    private ChangeType type;
    private Student newS,oldS;

    public StudentChangeEvent(ChangeType type,Student data){
        this.type=type;
        this.newS=data;
    }

    public StudentChangeEvent(ChangeType type,Student data,Student oldS){
        this.newS=data;
        this.type=type;
        this.oldS=oldS;

    }
    public ChangeType getType(){
        return type;
    }
    public Student getNewS(){
        return  newS;

    }
    public Student getOldS(){
        return oldS;
    }

}
