export type Interval = {
    start: string;
    end: string;
};

export type Location = {
    building: string;
    room: string;
};

export type Course = {
    name: string;
    code: string;
};

export type Attendee = {
    name: string;
    mail: string;
};

export type Event = {
    decoratorProps: any;
    summary: string;
    activity: string;
    location: string;
    interval: Interval;
    attendees: Attendee[];
    visibility: boolean;
};

export type Calendar = {
    id: string;
    name: string;
    events: Event[];
};