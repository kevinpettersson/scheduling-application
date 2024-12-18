<script lang="ts">
    import * as Sheet from "$lib/components/ui/sheet/index.js";
    import { buttonVariants } from "$lib/components/ui/button/index.js";
    import { Input } from "$lib/components/ui/input/index.js";
    import { Label } from "$lib/components/ui/label/index.js";
    import AttendeePopover from "./attendee-popover.svelte";
</script>
    
<Sheet.Root>
  <Sheet.Trigger class={buttonVariants({ variant: "outline" })}>Add Event</Sheet.Trigger>
    <Sheet.Content side="right">
     <Sheet.Header>
      <Sheet.Title>Add Event</Sheet.Title>
      <Sheet.Description>
       Fill in all fields to add a new event.
      </Sheet.Description>
     </Sheet.Header>

     <div class="grid gap-4 py-4">

      <div class="grid grid-cols-4 items-center gap-4">
       <Label for="summary" class="text-right">Summary</Label>
       <Input id="summary" value="Summary!" class="col-span-3" />
      </div>

      <div class="grid grid-cols-4 items-center gap-4">
       <Label for="description" class="text-right">Description</Label>
       <Input id="description" value="description!" class="col-span-3" />
      </div>

      <div class="grid grid-cols-4 items-center gap-4">
        <Label for="description" class="text-right">StartTime</Label>
        <input id="startTime" type="datetime-local" name="startTime" value="2024-06-01T08:30" class="col-span-3" />
      </div>

      <div class="grid grid-cols-4 items-center gap-4">
        <Label for="description" class="text-right">EndTime</Label>
        <input id="endTime" type="datetime-local" name="startTime" value="2024-06-01T08:30" class="col-span-3" />
      </div>

      <div class="grid grid-cols-4 items-center gap-4">
        <Label for="location" class="text-right">Location</Label>
        <Input id="location" value="location!" class="col-span-3" />
      </div>

        <div class="grid grid-cols-4 items-center gap-4">
         <Label for="attendees" class="text-right">Attendees</Label>
         <AttendeePopover/>
        </div>
    </div>
    <Sheet.Footer>
      <Sheet.Close class={buttonVariants({ variant: "outline" })}
       >Save Event</Sheet.Close>
    </Sheet.Footer>
  </Sheet.Content>
</Sheet.Root>