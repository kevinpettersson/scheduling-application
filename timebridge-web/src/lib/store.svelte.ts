import type { Calendar } from '$lib/types/calendar';

class CalendarStore {
    data = $state<Calendar>(
        {
            id: '',
            name: '',
            events: []
        }
    );

    get id() {
        return this.data.id;
    }

    get name() {
        return this.data.name;
    }

    get events() {
        return this.data.events;
    }

    // Get all unique course codes
    courseCodes() {
        const codes = this.data.events.map(event => event.decorators.COURSE.code);
        return [...new Set(codes)];
    }

    // Get all unique course names
    courseNames() {
        const names = this.data.events.map(event => event.decorators.COURSE.name);
        return [...new Set(names)];
    }

    // Get all unique activities
    courseActivities() {
        const activities = this.data.events.map(event => event.decorators.ACTIVITY);
        return [...new Set(activities)];
    }

    // Get visible events
    visibleEvents() {
        return this.data.events.filter(event => event.visibility);
    }
}

export const calendar = new CalendarStore();