<script lang="ts">
    import * as Table from '$lib/components/ui/table/index.js';
    import { calendarStore } from '$lib/stores/session-store';
    import { derived } from 'svelte/store';
    import { type Location, type Calendar } from '$lib/types/calendar';

    // Derived store to get the events from calendarStore
    const events = derived(calendarStore, ($calendarStore: Calendar) => $calendarStore?.events || []);
</script>

<Table.Root class="w-full">
    <Table.Header>
        <Table.Row>
            <Table.Head>Code</Table.Head>
            <Table.Head>Course</Table.Head>
            <Table.Head>Activity</Table.Head>
            <Table.Head>Starting</Table.Head>
            <Table.Head>Ending</Table.Head>
            <Table.Head>Location</Table.Head>
        </Table.Row>
    </Table.Header>
    <Table.Body>
        {#each $events as event, i (i)}
            <Table.Row>
                <Table.Cell class="font-medium">{event.course.code}</Table.Cell>
                <Table.Cell>{event.course.name}</Table.Cell>
                <Table.Cell>{event.activity}</Table.Cell>
                <Table.Cell>{new Date(event.interval.start).toLocaleString()}</Table.Cell>
                <Table.Cell>{new Date(event.interval.end).toLocaleString()}</Table.Cell>
                <Table.Cell>
                    {event.locations.map((location: Location) => `${location.building} - ${location.room}`).join(', ')}
                </Table.Cell>
            </Table.Row>
        {/each}
    </Table.Body>
</Table.Root>
