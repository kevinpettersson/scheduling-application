<script lang="ts">
	import * as Table from '$lib/components/ui/table/index.js';
	import { calendar } from '$lib/store.svelte';
	import SummaryCell from './event-cell-summary.svelte';
	import DescriptionCell from './event-cell-desc.svelte';
	import TimeCell from './event-cell-time.svelte';
	import LocationCell from './event-cell-location.svelte';
	import ActionsCell from './event-cell-actions.svelte';

	let events = $derived(calendar.events);
	let format = $derived(calendar.format);
</script>

<Table.Root class="w-full">
	<Table.Header>
		<Table.Row>
			<Table.Head>Event Summary</Table.Head>
			<Table.Head>Event Description</Table.Head>
			<Table.Head>Time</Table.Head>
			<Table.Head>Location</Table.Head>
			<Table.Head>Actions</Table.Head>
		</Table.Row>
	</Table.Header>
	<Table.Body>
		{#each events.filter((event) => event.visibility) as event}
			<Table.Row>
				<SummaryCell {event} {format} />
				<DescriptionCell {event} {format} />
				<TimeCell {event} />
				<LocationCell {event} {format} />
				<ActionsCell {event} />
			</Table.Row>
		{/each}
	</Table.Body>
</Table.Root>
