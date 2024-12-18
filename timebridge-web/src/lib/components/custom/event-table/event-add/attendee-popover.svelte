<script lang="ts">
	import { buttonVariants } from '$lib/components/ui/button/index.js';
	import { Input } from '$lib/components/ui/input/index.js';
	import { Label } from '$lib/components/ui/label/index.js';
	import * as Popover from '$lib/components/ui/popover/index.js';
	import { type Attendee, type EventDTO } from '$lib/types/calendar';

    let { eventState } = $props<{
		eventState: EventDTO;
	}>();

	let name = $state('');
	let mail = $state('');

	function saveAttendee() {
		let newAttendee = {
			name: name,
			mail: mail
		};

        eventState.attendees.push(newAttendee);

        name = '';
        mail = '';
	}
</script>

{#each eventState.attendees as attendee}
        <Label for="name">{attendee.name}</Label>
        <Label for="email">{attendee.mail}</Label>
{/each}

<Popover.Root>
	<Popover.Trigger class={`${buttonVariants({ variant: 'outline' })} col-span-3`}>Add Attendee</Popover.Trigger>
	<Popover.Content class="w-80">
		<div class="grid gap-4">
			<div class="space-y-2">
				<h4 class="font-medium leading-none">Attendee</h4>
				<p class="text-sm text-muted-foreground">
					Please insert the name and email of the attendee.
				</p>
			</div>
			<div class="grid gap-2">
				<div class="grid grid-cols-3 items-center gap-4">
					<Label for="name">Name</Label>
					<Input id="name" bind:value={name} class="col-span-2 h-8" />
				</div>
				<div class="grid grid-cols-3 items-center gap-4">
					<Label for="email">Email</Label>
					<Input id="email" bind:value={mail} class="col-span-2 h-8" />
				</div>
			</div>
			<button class={buttonVariants({ variant: 'outline' })} onclick={saveAttendee}
				>Submit Atteendee</button
			>
		</div>
	</Popover.Content>
</Popover.Root>
