package domain;

public class Nota extends Entity<Integer> {
    private Student student;
    private Tema tema;
    private String feedback;
    private Integer dataP;
    private String profesor;
    private  Integer intarziere;
    private Integer notaInitiala;
    private  Integer notaFinala;

    public Nota( Integer id,Student student,Tema tema, String feedback,Integer notaInitiala){
        super.setId(id);
        this.student=student;
        this.tema=tema;
        this.feedback=feedback;
        this.intarziere=0;
        this.notaInitiala=notaInitiala;

    }

    public Integer getDataP(){
        return dataP;
    }

    public void setDataP(Integer dataPN){
        this.dataP=dataPN;
    }

    public Student getStudent(){
        return student;
    }

    public void setStudent(Student student_nou){
        this.student=student_nou;
    }

    public  Tema getTema(){
        return tema;
    }

    public void setTema(Tema tema_noua){
        this.tema=tema_noua;
    }


    public  String getProfesor(){
        return profesor;
    }

    public void setProfesor(String profesor_nou){
        this.profesor=profesor_nou;
    }

    public Integer getIntarziere(){
        return intarziere;
    }

    public void setIntarziere(Integer intarziere_noua){
        this.intarziere=intarziere_noua;
    }

    public String getFeedback(){
        return feedback;
    }

    public void setFeedback(String feedback_nou){
        this.feedback=feedback_nou;
    }

    public Integer getNotaInitiala(){
        return notaInitiala;
    }

    public void setNotaInitiala(Integer notaInitiala_noua){
        this.notaInitiala=notaInitiala_noua;
    }

    public Integer CalculateFinalGrade(){
        if(StructuraAn.getInstance().getSaptamanaCurenta()<tema.getDeadlineWeek())
            return notaInitiala;
        Integer  nota=notaInitiala-StructuraAn.getInstance().getSaptamanaCurenta()+tema.getDeadlineWeek()+intarziere;

        if(nota>notaInitiala){
            return notaInitiala;
        }
        if(nota<notaInitiala-2)
        {
            return 0;
        }
        return nota;
    }

    public Integer getNotaFinala() {
        return notaFinala;
    }

    public void setNotaFinala(Integer notaFinala) {
        this.notaFinala = notaFinala;
    }

    @Override
    public String toString(){
        return "Tema: "+tema.getId()+"\nNota: "+this.notaFinala+
                "\nPredata in saptamana: "+dataP+"\nDeadline: "+tema.getDeadlineWeek()+
                "\nFeedback: "+feedback;
    }

    @Override
    public int hashCode(){
        return feedback.hashCode()^student.hashCode()^tema.hashCode();
    }
}
