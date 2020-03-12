package domain;

public class Student extends Entity<Integer> {
    private String nume;
    private String prenume;
    private Integer grupa;
    private String email;
    private String cadruDidacticIndrumatorLab;

    public Student(Integer Id,String nume,String prenume,Integer grupa,String email,String cdil){

        super.setId(Id);
        this.nume = nume;
        this.prenume=prenume;
        this.grupa=grupa;
        this.email=email;
        this.cadruDidacticIndrumatorLab=cdil;

    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public Integer getGrupa() {
        return grupa;
    }

    public void setGrupa(Integer grupa) {
        this.grupa = grupa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCadruDidacticIndrumatorLab() {
        return cadruDidacticIndrumatorLab;
    }

    public void setCadruDidacticIndrumatorLab(String cadruDidacticIndrumatorLab) {
        this.cadruDidacticIndrumatorLab = cadruDidacticIndrumatorLab;
    }

    @Override
    public String toString() {
        return "id: "+getId()+" "+
                "nume: " + nume + '\'' +
                "prenume: " + prenume + '\'' +
                "grupa: " + grupa ;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Student){
            Student s = (Student) obj;
            return s.getId() == getId();
        }
        return false;
    }

}
