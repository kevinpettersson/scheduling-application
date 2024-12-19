<script lang="ts">
	import * as Sheet from '$lib/components/ui/sheet/index.js';
	import { buttonVariants } from '$lib/components/ui/button/index.js';
	import { Input } from '$lib/components/ui/input/index.js';
	import { Label } from '$lib/components/ui/label/index.js';
	import AttendeePopover from '$lib/components/custom/event-table/event-add/attendee-popover.svelte';
	import type { EventDTO } from '$lib/types/calendar';
	import { modifyEvent } from '$lib/api.svelte';
	import { onMount } from 'svelte';


    // input props
	let { event } = $props<{
		event: Event;
	}>();


	let eventDTO: EventDTO = {
		summary: '',
		description: '',
		location: '',
		interval: {
			start: '',
			end: ''
		},
		attendees: []
	};

	let eventState = $state(eventDTO);

	onMount(() => {
	eventState.summary = event.summary;
        eventState.description = event.description;
        eventState.location = event.location;

        // Parse the date strings
        let startDate = new Date(event.interval.start);
        let endDate = new Date(event.interval.end);

        // Increment the dates by one day
        startDate.setDate(startDate.getDate() - 1);
        endDate.setDate(endDate.getDate() - 1);

        // Convert the dates back to strings
        eventState.interval.start = startDate.toISOString().split('T')[0];
        eventState.interval.end = endDate.toISOString().split('T')[0];

        eventState.attendees = event.attendees;
	});
</script>

<Sheet.Root>
	<Sheet.Trigger class={buttonVariants({ variant: 'outline' })}>Modify Event</Sheet.Trigger>
	<Sheet.Content side="right">
		<Sheet.Header>
			<Sheet.Title>Modify event</Sheet.Title>
			<Sheet.Description>Fill in all fields to modify event.</Sheet.Description>
		</Sheet.Header>

		<div class="grid gap-4 py-4">
			<div class="grid grid-cols-4 items-center gap-4">
				<Label for="summary" class="text-right">Summary</Label>
				<Input id="summary" bind:value={eventState.summary} class="col-span-3" />
			</div>

			<div class="grid grid-cols-4 items-center gap-4">
				<Label for="description" class="text-right">Description</Label>
				<Input id="description" bind:value={eventState.description} class="col-span-3" />
			</div>

			<div class="grid grid-cols-4 items-center gap-4">
				<Label for="description" class="text-right">StartTime</Label>
				<input
					id="startTime"
					type="datetime-local"
					name="startTime"
					bind:value={eventState.interval.start}
					class="col-span-3"
				/>
			</div>

			<div class="grid grid-cols-4 items-center gap-4">
				<Label for="description" class="text-right">EndTime</Label>
				<input
					id="endTime"
					type="datetime-local"
					name="startTime"
					bind:value={eventState.interval.end}
					class="col-span-3"
				/>
			</div>

			<div class="grid grid-cols-4 items-center gap-4">
				<Label for="location" class="text-right">Location</Label>
				<Input id="location" bind:value={eventState.location} class="col-span-3" />
			</div>

			<div class="grid grid-cols-4 items-center gap-4">
				<Label for="attendees" class="text-right">Attendees</Label>
				<AttendeePopover {eventState} />
			</div>
		</div>
		<Sheet.Footer>
			<Sheet.Close class={`${buttonVariants({ variant: 'outline' })} w-full`}>Close</Sheet.Close>
			<button
				onclick={() => modifyEvent(event.id, eventState)}
				class={`${buttonVariants({ variant: 'default' })} w-full`}
			>
				Save Event
			</button>
		</Sheet.Footer>
	</Sheet.Content>
</Sheet.Root>
