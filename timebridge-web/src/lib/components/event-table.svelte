<script lang="ts">
    import * as Table from '$lib/components/ui/table/index.js';
    import { calendarStore } from '$lib/stores/session-store';
    import { derived } from 'svelte/store';
    import { type Location, type Calendar } from '$lib/types/calendar';
    import Trash from "lucide-svelte/icons/trash-2";
    import { Button } from "$lib/components/ui/button/index.js";
    import { Badge } from "$lib/components/ui/badge/index.js";


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
                <Table.Cell class="font-medium"> <Badge variant="secondary" class="rounded"> {event.course.code}</Badge></Table.Cell>
                <Table.Cell>{event.course.name}</Table.Cell>
                <Table.Cell>{event.activity}</Table.Cell>
                <Table.Cell>{new Date(event.interval.start).toLocaleString()}</Table.Cell>
                <Table.Cell>{new Date(event.interval.end).toLocaleString()}</Table.Cell>
                <Table.Cell>
                    {event.locations.map((location: Location) => `${location.building} - ${location.room}`).join(', ')}
                </Table.Cell>
                <Table.Cell>
                    <Button variant="destructive" size="icon">
                        <Trash/>
                      </Button>
                </Table.Cell>

            </Table.Row>
        {/each}
    </Table.Body>
</Table.Root>
