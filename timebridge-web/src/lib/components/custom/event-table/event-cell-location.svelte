<script lang="ts">
	import * as Table from '$lib/components/ui/table/index.js';
	import type { Event, Format } from '$lib/types/calendar.d.ts';

	let { event, format } = $props<{
		event: Event;
		format: Pick<Format, 'location'>;
	}>();
</script>

<Table.Cell>
	{#each event.locations as location}
		{#if format.location.includes('building') && format.location.includes('room')}
			{location.building} - {location.room}
		{:else if format.location.includes('building')}
			{location.building}
		{:else if format.location.includes('room')}
			{location.room}
		{/if}
	{/each}
</Table.Cell>
