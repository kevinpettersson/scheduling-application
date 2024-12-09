export type Attendee = {
    name: string;
    mail: string;
}

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

export type Event = {
    id: string;
    course: Course;
    activity: string;
    interval: Interval;
    locations: Location[];
    visibility: boolean;
    attendees: Attendee[];
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