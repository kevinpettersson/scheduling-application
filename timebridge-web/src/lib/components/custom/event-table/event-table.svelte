<script lang="ts">
	import * as Table from '$lib/components/ui/table/index.js';
	import { calendar } from '$lib/store.svelte';
	import EventCard from '$lib/components/custom/event-table/event-card.svelte';
	import type { Event } from '$lib/types/calendar.d.ts';

	let events = $derived(calendar.events);

	// format the time
	const timeFormat = {
		start: {
			weekday: 'long',
			month: 'short',
			day: 'numeric',
		} as const
	};

	// get day and date of event
	function getDay(event: Event) {
		return new Date(event.interval.start).toLocaleString('en-GB', timeFormat.start);
	}
	
</script>


<div class="grid grid-cols-1 gap-2 w-full p-2 pl-0">
	{#each events.filter((event) => event.visibility) as event}
		<EventCard {event} />
	{/each}
	
</div>

