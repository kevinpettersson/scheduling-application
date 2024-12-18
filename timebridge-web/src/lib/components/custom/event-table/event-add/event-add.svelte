<script lang="ts">
	import * as Sheet from '$lib/components/ui/sheet/index.js';
	import { buttonVariants } from '$lib/components/ui/button/index.js';
	import { Input } from '$lib/components/ui/input/index.js';
	import { Label } from '$lib/components/ui/label/index.js';
	import AttendeePopover from './attendee-popover.svelte';
	import type { EventDTO } from '$lib/types/calendar';
	import { addEvent } from '$lib/api.svelte';

	let event: EventDTO = {
		summary: '',
		description: '',
		location: '',
		interval: {
			start: '',
			end: ''
		},
		attendees: []
	};

	let eventState = $state(event);
</script>

<Sheet.Root>
	<Sheet.Trigger class={buttonVariants({ variant: 'outline' })}>Add Event</Sheet.Trigger>
	<Sheet.Content side="right">
		<Sheet.Header>
			<Sheet.Title>Add Event</Sheet.Title>
			<Sheet.Description>Fill in all fields to add a new event.</Sheet.Description>
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
					name="tartTime"
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
				onclick={() => addEvent(eventState)}
				class={`${buttonVariants({ variant: 'default' })} w-full`}
			>
				Add Event
			</button>
		</Sheet.Footer>
	</Sheet.Content>
</Sheet.Root>
