package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class StructuraAn {
    public class Semestru {
        private LocalDate start;
        private LocalDate sfarsit;
        private LocalDate startV;
        private LocalDate sfarsitV;

        public Semestru(String start, String sfarsit) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");

            this.start = LocalDate.parse(start, formatter);
            this.sfarsit = LocalDate.parse(sfarsit, formatter);
        }

        public void addVacanta(String startV, String sfarsitV) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
            this.startV = LocalDate.parse(startV, formatter);
            this.sfarsitV = LocalDate.parse(sfarsitV, formatter);
        }

        public boolean eSfarsit() {
            return false;
        }

        public Integer getWeek() {
            Integer week = 0;
            LocalDate start_sem = start;
            while (start_sem.isBefore(LocalDate.now()) && sfarsit.isAfter(LocalDate.now())) {
                week++;
                start_sem = start_sem.plusDays(7);
            }
            return week;
        }
    }

    private  Semestru semestru1;
    private Semestru semestru2;

    public StructuraAn(){
        semestru1=new Semestru("30.09.2019","19.01.2020");
        semestru2=new Semestru("24.02.2020","7.06.2020");
    }
    public Integer getSemestru()
    {
        if(semestru1.eSfarsit()==false)
            return 1;
        return 2;
    }

    public Integer getSaptamanaCurenta(){
        if(semestru1.eSfarsit()==false)
            return semestru1.getWeek()+1;
        if(semestru2.eSfarsit()==false)
            return semestru2.getWeek()+1;
        return -1;
    }
    private static StructuraAn instance=new StructuraAn();
    public static  StructuraAn getInstance(){
        return instance;
    }

}
