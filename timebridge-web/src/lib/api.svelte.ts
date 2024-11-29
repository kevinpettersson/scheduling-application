import { calendar } from './store.svelte';

// Prevent re-triggering during update
let requesting = false

// Fetch the calendar object from the server based on the URL
export function fetchCalendar(url: string) {
    if (requesting) return;
    requesting = true;

    async function fetchData() {
        try {
            const encodedUrl = encodeURIComponent(url);
            const response = await fetch(`http://localhost:8080/upload?ical=${encodedUrl}`);
            const data = await response.json();
            calendar.data = data;
        } catch (error) {
            console.error('Error fetching calendar:', error);
        } finally {
            requesting = false;
        }
    }

    fetchData();
}


// Modify the calendar based on course and activity filters
export function modifyCalendar(courseFilter: string[], activityFilter: string[]) {
    if (requesting) return;
    requesting = true;

    async function fetchData() {
        try {
            const response = await fetch(`http://localhost:8080/modify?courseFilter=${courseFilter}&activityFilter=${activityFilter}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(calendar.data)
            });
            const data = await response.json();
            calendar.data = data;
        } catch (error) {
            console.error('Error modifying calendar:', error);
        } finally {
            requesting = false;
        }
    }

    fetchData();
}