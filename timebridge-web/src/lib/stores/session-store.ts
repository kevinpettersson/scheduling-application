import { writable, type Writable } from 'svelte/store';
import type { Calendar } from '$lib/types/calendar';

// Utility to check if running in the browser
const isBrowser = typeof window !== 'undefined';

export function createSessionStore<T>(key: string, initialValue: T | null = null): Writable<T> {
    if (!isBrowser) {
        // Return a writable with the initial value in SSR
        return writable(initialValue as T);
    }

    // Get the initial value from sessionStorage
    const storedValue = sessionStorage.getItem(key);
    const initial = storedValue ? JSON.parse(storedValue) : initialValue;

    const store = writable<T>(initial);

    // Subscribe to changes and update sessionStorage
    store.subscribe((value) => {
        if (value === null) {
            sessionStorage.removeItem(key);
        } else {
            sessionStorage.setItem(key, JSON.stringify(value));
        }
    });

    return store;
}

export const calendarStore = createSessionStore<Calendar>('calendar');