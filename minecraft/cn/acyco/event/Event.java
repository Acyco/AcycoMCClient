package cn.acyco.event;

import java.util.ArrayList;

/**
 * @author Acyco
 * @create 2020-01-14 21:37
 */
public class Event {

    public Event call() {

        final ArrayList<EventData> dataList = EventManager.get(this.getClass());
        if (dataList != null) {
        //    System.out.println(dataList.size());
            for (EventData data : dataList) {
                try {
                    data.target.invoke(data.source, this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return this;
    }
}
