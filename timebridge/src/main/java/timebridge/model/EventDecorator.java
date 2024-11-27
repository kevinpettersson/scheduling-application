package timebridge.model;

public abstract class EventDecorator implements EventInterface {
    protected EventInterface decoratedEvent;

    public EventDecorator(EventInterface decoratedEvent) {
        this.decoratedEvent = decoratedEvent;
    }

    @Override
    public String getActivity() {
        return decoratedEvent.getActivity();
    }

    @Override
    public void setActivity(String activity) {
        decoratedEvent.setActivity(activity);
    }

    @Override
    public Interval getInterval() {
        return decoratedEvent.getInterval();
    }

    @Override
    public void setInterval(Interval interval) {
        decoratedEvent.setInterval(interval);
    }



    
}
