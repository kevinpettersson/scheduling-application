<script lang="ts">
	import * as Table from '$lib/components/ui/table/index.js';
	import type { Event, Format } from '$lib/types/calendar.d.ts';

	let { event, format } = $props<{
		event: Event;
		format: Pick<Format, 'location'>;
	}>();

	function getLocationString(event: Event, format: Pick<Format, 'location'>): string {
		return event.locations.map((location, index) => {
			let locationString = '';
			if (format.location.includes('building') && format.location.includes('room')) {
				locationString = `${location.building} - ${location.room}`;
			} else if (format.location.includes('building')) {
				locationString = location.building;
			} else if (format.location.includes('room')) {
				locationString = location.room;
			}
			if (index !== event.locations.length - 1 && locationString) {
				locationString += ', ';
			}
			return locationString;
		}).join('');
	}
</script>

<Table.Cell>
	{getLocationString(event, format)}
</Table.Cell>
