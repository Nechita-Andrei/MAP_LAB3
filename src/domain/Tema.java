package domain;

import Utils.WeekCalculator;

public class Tema extends Entity<Integer>{
    private String descriere;
    private Integer startWeek;
    private Integer deadlineWeek;

    public Tema(Integer ID, String descriere, Integer deadlineWeek){

        super.setId(ID);
        this.descriere=descriere;
        this.deadlineWeek=deadlineWeek;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Integer getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(Integer startWeek) {
        this.startWeek = startWeek;
    }

    public Integer getDeadlineWeek() {
        return deadlineWeek;
    }

    public void setDeadlineWeek(Integer deadlineWeek) {
        WeekCalculator c=new WeekCalculator();

        if(getStartWeek()<=deadlineWeek && getDeadlineWeek()>=c.calculate())
            this.deadlineWeek = deadlineWeek;
    }

    @Override
    public String toString() {
        return "id: "+getId()+" "+
                "descriere: " + descriere + '\'' +
                "startWeek: " + startWeek +'\''+
                "deadlineWeek: " + deadlineWeek;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Tema){
            Tema s = (Tema) obj;
            return s.getId() == getId();
        }
        return false;
    }


}
