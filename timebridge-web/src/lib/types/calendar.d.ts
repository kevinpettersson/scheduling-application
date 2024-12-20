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
    id: string;
    summary: string;
    activity: string;
    location: string;
    interval: Interval;
    attendees: Attendee[];
    visibility: boolean;
    decorators: any;
};

export type EventDTO = {
    summary: string;
    description: string;
    location: string;
    interval: Interval;
    attendees: Attendee[];
}

type EventSchema = {
    summarySchema: String[];
    descriptionSchema: String[];
    locationSchema: String[];
};

export type Calendar = {
    id: string;
    name: string;
    events: Event[];
};