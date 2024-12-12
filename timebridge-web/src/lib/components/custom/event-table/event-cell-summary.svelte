<script lang="ts">
	import * as Table from '$lib/components/ui/table/index.js';
	import { Badge } from '$lib/components/ui/badge/index.js';
	import type { Event, Format } from '$lib/types/calendar.d.ts';

	let { event, format } = $props<{
		event: Event;
		format: Pick<Format, 'summary'>;
	}>();
</script>

<Table.Cell>
	{#each format.summary as field, index}
		{#if field === 'code'}
			<Badge variant="secondary" class="rounded font-medium">
				{event.course.code}
			</Badge>
		{:else if field === 'name'}
			{event.course.name}
		{:else if field === 'activity'}
			{event.activity}
		{:else if field === 'null'}
			{""}
		{/if}

		{#if index < format.summary.length - 1}
			{' - '}
		{/if}
	{/each}
</Table.Cell>
