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
    course: Course;
    activity: string;
    interval: Interval;
    locations: Location[];
};

export type Calendar = {
    id: string | null; // Temporary null value
    name: string;
    events: Event[];
};