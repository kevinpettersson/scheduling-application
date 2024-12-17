<script lang="ts">
	import type { Event } from '$lib/types/calendar.d.ts';
	import * as Card from '$lib/components/ui/card/index.js';
	import { MapPin, Text, Users } from 'lucide-svelte';

	// input props
	let { event } = $props<{
		event: Event;
	}>();

	// format the title
	function getTitle() {
		return event.summary;
	}

	// format the description
	function getDesc() {
		return event.description;
	}

	// format the location
	function getLocation() {
		return event.location;
	}

	// format the time
	const timeFormat = {
		start: {
			weekday: 'long',
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

	// function to get the time
	function getTime() {
		return `${new Date(event.interval.start).toLocaleString('en-GB', timeFormat.start)} - ${new Date(event.interval.end).toLocaleString('en-GB', timeFormat.end)}`;
	}

	// get attendees
	function getAttendees() {
		if (event.attendees.length === 0) {
			return '0 attendees';
		}

		let attendees = '';
		for (const attendee of event.attendees) {
			attendees += attendee.name;
			attendees += ' - ' + attendee.email;
			if (event.attendees.indexOf(attendee) < event.attendees.length - 1) {
				attendees += ', ';
			}
		}
	}
</script>

<Card.Root>
	<Card.Header>
		<Card.Title>{getTitle()}</Card.Title>
		<Card.Description>{getTime()}</Card.Description>
	</Card.Header>
	<Card.Content class="flex flex-col gap-2">
		<Card.Description class="flex items-center gap-2 text-foreground">
			<Text size="18" class="flex-none" />
			<span>{getDesc()}</span>
		</Card.Description>
		<Card.Description class="flex items-center gap-2 text-foreground ">
			<MapPin size="18" class="flex-none" />
			<span>{getLocation()}</span>
		</Card.Description>
		<Card.Description class="flex items-center gap-2 text-foreground">
			<Users size="18" class="flex-none" />
			<span>{getAttendees()}</span>
		</Card.Description>
	</Card.Content>
</Card.Root>
