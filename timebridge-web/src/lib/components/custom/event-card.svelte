<script lang="ts">
	import type { Event, Format } from '$lib/types/calendar.d.ts';
	import * as Card from '$lib/components/ui/card/index.js';
	import { MapPin, Text, Users } from 'lucide-svelte';

	// input props
	let { event, format } = $props<{
		event: Event;
		format: Format;
	}>();

	// format the title
	function getTitle() {
		if (format.summary.length === 0) {
			return 'No title';
		}

		let title = '';
		for (const field of format.summary) {
			if (field === 'code') {
				title += event.course.code;
			} else if (field === 'name') {
				title += event.course.name;
			} else if (field === 'activity') {
				title += event.activity;
			}
			if (format.summary.indexOf(field) < format.summary.length - 1) {
				title += ' - ';
			}
		}

		return title;
	}

	// format the description
	function getDesc() {
		if (format.description.length === 0) {
			return '. . .';
		}

		let desc = '';
		for (const field of format.description) {
			if (field === 'code') {
				desc += event.course.code;
			} else if (field === 'name') {
				desc += event.course.name;
			} else if (field === 'activity') {
				desc += event.activity;
			}
			if (format.description.indexOf(field) < format.description.length - 1) {
				desc += ' - ';
			}
		}

		return desc;
	}

	// format the location
	function getLocation() {
		if (event.locations.length === 0 || format.location.length === 0) {
			return '. . .';
		}

		let location = '';
		for (const loc of event.locations) {
			if (format.location.includes('building') && !location.includes(loc.building)) {
				location += loc.building;
				if (format.location.includes('room')) {
					location += ' ❯ ';
				}
			}

			if (format.location.includes('room')) {
				location += loc.room;
			}

			if (loc !== event.locations[event.locations.length - 1] && format.location.includes('room')) {
				location += ' • ';
			}
		}

		return location;
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
