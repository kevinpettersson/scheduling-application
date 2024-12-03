<script lang="ts">
	import * as Sheet from '$lib/components/ui/sheet/index.js';
	import { buttonVariants } from '$lib/components/ui/button/index.js';
	import { Input } from '$lib/components/ui/input/index.js';
	import { Label } from '$lib/components/ui/label/index.js';
	import DatePicker from '$lib/components/custom/date-picker.svelte';
	import { DateFormatter } from '@internationalized/date';
    import TimePicker from '$lib/components/custom/custom-time-picker.svelte';

    const df = new DateFormatter("en-US", {
    dateStyle: "long",
  });
    
    let courseCode = '';
    let courseName = '';
    let activity = '';
    let date = '';
    let startTime = '';
    let endTime = '';
    let attendees = '';
    let location = '';

    let isFormValid = false;

    $: isFormValid = courseCode.trim() !== '' && courseName.trim() !== '' && activity.trim() !== '' && date.trim() !== '' && startTime.trim() !== '' && endTime.trim() !== '' && attendees.trim() !== '' && location.trim() !== '';


   // function to handle the save, not implemented yet
	function handleSave(e: CustomEvent<any>): void {
		throw new Error('Function not implemented.');
	}
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
                <Input id="courseCode" bind:value={courseCode} class="col-span-3" required />
            </div>

            <!-- Course Name -->
            <div class="grid grid-cols-4 items-center gap-4">
                <Label for="courseName" class="text-right">Course name</Label>
                <Input id="courseName" bind:value={courseName} class="col-span-3" required />
            </div>

            <!-- Activity -->
            <div class="grid grid-cols-4 items-center gap-4">
                <Label for="activity" class="text-right">Activity</Label>
                <Input id="activity" bind:value={activity} class="col-span-3" required />
            </div>

            <!-- Time  ADD CALENDAR HERE!!!! -->
            <div class="grid grid-cols-4 items-center gap-4">
                <Label for="time" class="text-right">Date</Label>
                <DatePicker/>
            </div>

            <div class="grid grid-cols-4 items-center gap-4">
                <Label for="startTime" class="text-right">Start time</Label>
                <TimePicker/>
            </div>

            <div class="grid grid-cols-4 items-center gap-4">
                <Label for="endTime" class="text-right">End time</Label>
                <TimePicker/>
            </div>

            <div class="grid grid-cols-4 items-center gap-4">       
                <Label for="attendees" class="text-right">Attendees</Label>
                <Input id="attendees" bind:value={attendees} class="col-span-3" required />
            </div>

            <!-- Location -->
            <div class="grid grid-cols-4 items-center gap-4">
                <Label for="location" class="text-right">Location</Label>
                <Input id="location" bind:value={location} class="col-span-3" required />
            </div>

        </div>
        <Sheet.Footer>
            <Sheet.Close class={buttonVariants({ variant: 'outline' })} on:click={handleSave} disabled={!isFormValid}>Save Event</Sheet.Close>
        </Sheet.Footer>
	</Sheet.Content>
</Sheet.Root>
