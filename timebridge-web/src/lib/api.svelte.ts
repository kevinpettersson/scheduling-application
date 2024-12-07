import { calendar } from './store.svelte';

// Prevent re-triggering during update
let requesting = false

// Fetch the calendar object from the server based on the URL
export async function fetchCalendar(url: string) {
    if (requesting) return;
    requesting = true;

    try {
        const response = await fetch(`/api/upload?ical=${encodeURIComponent(url)}`);
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
            `/api/modify?courseFilter=${courseFilter}&activityFilter=${activityFilter}`,
            {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(calendar.data),
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
        const response = await fetch('/api/download', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(calendar.data),
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
