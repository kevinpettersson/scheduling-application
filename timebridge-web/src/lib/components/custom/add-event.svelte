<script lang="ts">
	import * as Sheet from '$lib/components/ui/sheet/index.js';
	import { buttonVariants } from '$lib/components/ui/button/index.js';
	import { Input } from '$lib/components/ui/input/index.js';
	import { Label } from '$lib/components/ui/label/index.js';
    import datePicker from '$lib/components/custom/date-picker.svelte';
	import DatePicker from '$lib/components/custom/date-picker.svelte';

    
</script>

<Sheet.Root>
	<Sheet.Trigger class={buttonVariants({ variant: 'outline' })}>Add Event</Sheet.Trigger>
	<Sheet.Content side="right">
		<Sheet.Header>
			<Sheet.Title>New Event</Sheet.Title>
			<Sheet.Description>
				Add a new event to your calendar. Make sure all fields are filled in
			</Sheet.Description>
		</Sheet.Header>
		<div class="grid gap-4 py-4">
			<!-- Course Code -->
			<div class="grid grid-cols-4 items-center gap-4">
				<Label for="courseCode" class="text-right">Course Code</Label>
				<Input id="courseCode" value="XXX123" class="col-span-3" />
			</div>

			<!-- Course Name -->
			<div class="grid grid-cols-4 items-center gap-4">
				<Label for="courseName" class="text-right">CourseName</Label>
				<Input id="courseName" value="coursename here" class="col-span-3" />
			</div>

			<!-- Activity -->
			<div class="grid grid-cols-4 items-center gap-4">
				<Label for="activity" class="text-right">Activity</Label>
				<Input id="activity" value="activity here" class="col-span-3" />
			</div>

			<!-- Time  ADD CALENDAR HERE!!!! -->
            <div class="grid grid-cols-4 items-center gap-4">
                <Label for="time" class="text-right">Time</Label>
                <DatePicker/> 
            </div>

            <div class="grid grid-cols-4 items-center gap-4">
				<Label for="location" class="text-right">StartTime</Label>
				<Input id="location" value="location here" class="col-span-3" />
			</div>

            <div class="grid grid-cols-4 items-center gap-4">
				<Label for="location" class="text-right">EndTime</Label>
				<Input id="location" value="location here" class="col-span-3" />
			</div>

            <div class="grid grid-cols-4 items-center gap-4">       
                <Label for="attendees" class="text-right">Attendees</Label>
                <Input id="attendees" value="attendees here" class="col-span-3" />
            </div>

			<!-- Location -->
			<div class="grid grid-cols-4 items-center gap-4">
				<Label for="location" class="text-right">Location</Label>
				<Input id="location" value="location here" class="col-span-3" />
			</div>

		</div>
		<Sheet.Footer>
			<Sheet.Close class={buttonVariants({ variant: 'outline' })}>Save changes</Sheet.Close>
		</Sheet.Footer>
	</Sheet.Content>
</Sheet.Root>
