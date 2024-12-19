import { calendar } from './store.svelte';
import type { EventDTO, EventSchema, Event, Attendee } from './types/calendar';

// Fetch the base API URL from environment variables
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

// Prevent re-triggering during update
let requesting = false

// Fetch the calendar object from the server based on the URL
export async function fetchCalendar(url: string) {
    if (requesting) return;
    requesting = true;

    try {
        const response = await fetch(
            `${API_BASE_URL}/calendar/upload?ical=${encodeURIComponent(url)}`,
            {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
            }
        );
        calendar.data = await response.json();
    } catch (error) {
        console.error('Error fetching calendar:', error);
    } finally {
        requesting = false;
    }
}


// Modify the calendar based on course and activity filters
export async function modifyCalendar(courseFilter: string[], activityFilter: string[]) {
    if (requesting) return;
    requesting = true;

    try {
        const response = await fetch(
            `${API_BASE_URL}/calendar/modify/${calendar.id}?courseFilter=${courseFilter}&activityFilter=${activityFilter}`,
            {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
            }
        );
        calendar.data = await response.json();
    } catch (error) {
        console.error('Error modifying calendar:', error);
    } finally {
        requesting = false;
    }
}

// Download the calendar object as an .ics file
export async function downloadCalendar() {
    if (requesting) return;
    requesting = true;

    try {
        const response = await fetch(`${API_BASE_URL}/calendar/download/${calendar.id}`, {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' },
        });
        if (!response.ok) throw new Error('Download failed');

        const blob = await response.blob();
        const link = Object.assign(document.createElement('a'), {
            href: URL.createObjectURL(blob),
            download: `${calendar.name}.ics`,
        });
        link.click();
        URL.revokeObjectURL(link.href);
    } catch (error) {
        console.error('Download error:', error);
    } finally {
        requesting = false;
    }
}

// Set calendar format by applying a schema
export async function applySchema(schema: EventSchema) {
    if (requesting) return;
    requesting = true;

    try {
        const { summarySchema, descriptionSchema, locationSchema } = schema;
        const response = await fetch(`${API_BASE_URL}/calendar/applySchema/${calendar.id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                summarySchema,
                descriptionSchema,
                locationSchema,
            }),
        });
        calendar.data = await response.json();
    } catch (error) {
        console.error('Error applying schema:', error);
    } finally {
        requesting = false;
    }
}

// Add a new event to the calendar
export async function addEvent(event: EventDTO) {
    if (requesting) return;
    requesting = true;

    // Vi tejpar lite!!
    event.interval.start = event.interval.start + ':00Z';
    event.interval.end = event.interval.end + ':00Z';

    try {
        const response = await fetch(`${API_BASE_URL}/event/add?calendarId=${calendar.id}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(event),
        });
        calendar.data = await response.json();
    } catch (error) {
        console.error('Error adding event:', error);
    } finally {
        requesting = false;
    }
}

// Delete an event from the calendar
export async function deleteEvent(event: Event) {
    if (requesting) return;
    requesting = true;

    try {
        const response = await fetch(`${API_BASE_URL}/event/delete?calendarId=${calendar.id}&eventId=${event.id}`, {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json' },
        });
        calendar.data = await response.json();
    } catch (error) {
        console.error('Error deleting event:', error);
    } finally {
        requesting = false;
    }
}

// Modify an event in the calendar
export async function modifyEvent(id: string, event: EventDTO) {
    if (requesting) return;
    requesting = true;

    // Vi tejpar lite!!
    event.interval.start = event.interval.start + ':00Z';
    event.interval.end = event.interval.end + ':00Z';

    try {
        const response = await fetch(`${API_BASE_URL}/event/modify?calendarId=${calendar.id}&eventId=${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(event),
        });
        calendar.data = await response.json();
    } catch (error) {
        console.error('Error modifying event:', error);
    } finally {
        requesting = false;
    }
}

// Add attendees to all events of a specific course
export async function setCourseAttendees(courseCode: string, attendees: Attendee[]) {
    if (requesting) return;
    requesting = true;

    try {
        const response = await fetch(`${API_BASE_URL}/calendar/setCourseAttendees/${calendar.id}?courseCode=${courseCode}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ attendees }),
        });
        calendar.data = await response.json();
    } catch (error) {
        console.error('Error adding attendees:', error);
    } finally {
        requesting = false;
    }
}



