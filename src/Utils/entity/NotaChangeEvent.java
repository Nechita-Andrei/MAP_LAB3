package Utils.entity;


import domain.Nota;

public class NotaChangeEvent implements Event {
    private ChangeType type;
    private Nota newS,oldS;

    public NotaChangeEvent(ChangeType type,Nota data){
        this.type=type;
        this.newS=data;
    }

    public NotaChangeEvent(ChangeType type,Nota data,Nota oldS){
        this.newS=data;
        this.type=type;
        this.oldS=oldS;

    }
    public ChangeType getType(){
        return type;
    }
    public Nota getNewS(){
        return  newS;

    }
    public Nota getOldS(){
        return oldS;
    }

}


