package Utils.observer;

import Utils.entity.Event;

public interface Observer<E extends Event> {
    void update (E e);
}
