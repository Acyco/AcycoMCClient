package cn.acyco.event;

/**
 * @author Acyco
 * @create 2020-01-14 21:42
 */
public class EventCancelable extends Event{
    private boolean cancelled = false;

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
    
}
