<script lang="ts">
	import * as Table from '$lib/components/ui/table/index.js';
	import type { Event } from '$lib/types/calendar.d.ts';

	let { event } = $props<{ event: Event }>();

	const formatOptions = {
		start: {
			weekday: 'short',
			month: 'short',
			day: 'numeric',
			hour: '2-digit',
			minute: '2-digit'
		} as const,
		end: {
			hour: '2-digit',
			minute: '2-digit'
		} as const
	};
</script>

<Table.Cell>
	{new Date(event.interval.start).toLocaleString('en-GB', formatOptions.start)} -
	{new Date(event.interval.end).toLocaleString('en-GB', formatOptions.end)}
</Table.Cell>
