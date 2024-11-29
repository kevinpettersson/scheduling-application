import type { Calendar } from '$lib/types/calendar';

class CalendarStore {
    data = $state<Calendar>(
        {
            id: '',
            name: '',
            format: {
                summary: [],
                description: [],
                location: []
            },
            events: []
        }
    );

    get id() {
        return this.data.id;
    }

    get name() {
        return this.data.name;
    }

    get format() {
        return this.data.format;
    }

    get events() {
        return this.data.events;
    }

    // Get all unique course codes
    courseCodes() {
        const codes = this.data.events.map(event => event.course.code);
        return [...new Set(codes)];
    }

    // Get all unique course names
    courseNames() {
        const names = this.data.events.map(event => event.course.name);
        return [...new Set(names)];
    }

    // Get all unique activities
    courseActivities() {
        const activities = this.data.events.map(event => event.activity);
        return [...new Set(activities)];
    }

    // Get visible events
    visibleEvents() {
        return this.data.events.filter(event => event.visibility);
    }

    // Set format
    setFormat(format: Calendar['format']) {
        this.data.format = format;
    }
}

export const calendar = new CalendarStore();