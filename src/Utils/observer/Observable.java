package Utils.observer;


import Utils.entity.Event;

public interface Observable<Ev extends Event>{
    void addObserver(Observer<Ev>e);
    void removeObserver(Observer<Ev> e);
    void notifyObservers(Ev  t);
}
