<script lang="ts">
	import * as Sheet from '$lib/components/ui/sheet/index.js';
	import { buttonVariants } from '$lib/components/ui/button/index.js';
	import { Input } from '$lib/components/ui/input/index.js';
	import { Label } from '$lib/components/ui/label/index.js';
	import DatePicker from '$lib/components/custom/date-picker.svelte';
	import { DateFormatter, getLocalTimeZone, today } from '@internationalized/date';
    import TimePicker from '$lib/components/custom/event-table/event-add/custom-time-picker.svelte';
	import { _importSchema } from '$lib/schemas';
	import { defaults, superForm } from 'sveltekit-superforms';
	import { zod } from 'sveltekit-superforms/adapters';
	import { fetchCalendar } from '$lib/api.svelte';
	import { goto } from '$app/navigation';

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
	function handleSave(): void {
        const event = {
            courseCode,
            courseName,
            activity,
            date,
            startTime,
            endTime,
            location,
            attendees
        };
        console.log(event);
    }
    
    let value = today(getLocalTimeZone());
</script>

<Sheet.Root>
    <Sheet.Trigger class={buttonVariants({ variant: 'outline' })}>Add Event</Sheet.Trigger>
    <Sheet.Content side="right">
        <Sheet.Header>
            <Sheet.Title>New Event</Sheet.Title>
            <Sheet.Description>
                Add a new event to your calendar. Make sure all fields are filled in!
            </Sheet.Description>
        </Sheet.Header>

        <form method="POST" class="w-full flex flex-col">

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
                <Label for="date" class="text-right">Date</Label>
            <DatePicker></DatePicker>
            <script>
                import { DateValue } from '$lib/components/custom/date-picker.svelte';
                $: date = DateValue;
            </script>
        </div>

            <div class="grid grid-cols-4 items-center gap-4">
                <Label for="startTime" class="text-right">Start time</Label>
                <TimePicker bind:startTime={startTime}/>
            </div>

            <div class="grid grid-cols-4 items-center gap-4">
                <Label for="endTime" class="text-right">End time</Label>
                <TimePicker bind:endTime={endTime}/>
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
        </form>
        <Sheet.Footer>
            <Sheet.Close class={buttonVariants({ variant: 'outline' })} on:click={handleSave} disabled={!isFormValid}>Save Event</Sheet.Close>

        <Sheet.Footer>
        </Sheet.Footer>
        </Sheet.Footer>
	</Sheet.Content>
</Sheet.Root>
