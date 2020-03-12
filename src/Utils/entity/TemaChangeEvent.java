package Utils.entity;

import domain.Tema;

public class TemaChangeEvent implements Event {
    private ChangeType type;
    private Tema newS,oldS;

    public TemaChangeEvent(ChangeType type,Tema data){
        this.type=type;
        this.newS=data;
    }

    public TemaChangeEvent(ChangeType type,Tema data,Tema oldS){
        this.newS=data;
        this.type=type;
        this.oldS=oldS;

    }
    public ChangeType getType(){
        return type;
    }
    public Tema getNewS(){
        return  newS;

    }
    public Tema getOldS(){
        return oldS;
    }

}
