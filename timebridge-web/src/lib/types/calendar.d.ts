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
    course: Course;
    activity: string;
    interval: Interval;
    locations: Location[];
    attendees: Attendee[];
    visibility: boolean;
};

export type Format = {
    summary: string[];
    description: string[];
    location: string[];
};

export type Calendar = {
    id: string;
    name: string;
    format: Format;
    events: Event[];
};